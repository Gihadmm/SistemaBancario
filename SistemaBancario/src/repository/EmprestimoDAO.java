package repository;

import Model.Emprestimo;
import Model.Livro;
import Model.StatusEmprestimo;
import Model.Cliente;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class EmprestimoDAO {
    public void inserir(Emprestimo e)throws SQLException{
        String sql="INSERT INTO Emprestimos (cpf_cliente,id_livro,dataEmprestimo,dataDevolucaoPrevista,status,valorMulta,multaPaga) "
                + "VALUES (?,?,?,?,?,?,?)";
        try(Connection c=DatabaseConnection.getConnection();
            PreparedStatement p=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            p.setString(1,e.getCliente().getCpf());
            p.setInt(2,e.getLivro().getId());
            p.setDate(3,new java.sql.Date(e.getDataEmprestimo().getTime()));
            p.setDate(4,new java.sql.Date(e.getDataDevolucaoPrevista().getTime()));
            p.setString(5,e.getStatus().name());
            p.setDouble(6,e.getValorMulta());
            p.setBoolean(7,e.isMultaPaga());
            p.executeUpdate();
            try(ResultSet rs=p.getGeneratedKeys()){
                if(rs.next()) e.setId(rs.getInt(1));
            }
        }
    }

    public Emprestimo buscarPorId(int id)throws SQLException{
        String sql="SELECT * FROM Emprestimos WHERE id=?";
        try(Connection c=DatabaseConnection.getConnection();
            PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,id);
            try(ResultSet rs=p.executeQuery()){
                if(rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public void atualizarDevolucao(Emprestimo e) throws SQLException {
        String sql = """
            UPDATE Emprestimos 
               SET dataDevolucaoReal = ?, status = ?, valorMulta = ?, multaPaga = ?
             WHERE id = ?
        """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setDate(1, new java.sql.Date(e.getDataDevolucaoReal().getTime()));
            p.setString(2, e.getStatus().name());
            p.setDouble(3, e.getValorMulta());
            p.setBoolean(4, e.isMultaPaga());
            p.setInt(5, e.getId());
            p.executeUpdate();
        }
    }

    /** Atualiza apenas a data prevista (renovação). */
    public void atualizarRenovacao(Emprestimo e) throws SQLException {
        String sql = "UPDATE Emprestimos SET dataDevolucaoPrevista = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setDate(1, new java.sql.Date(e.getDataDevolucaoPrevista().getTime()));
            p.setInt(2, e.getId());
            p.executeUpdate();
        }
    }

    /** Lista todos os empréstimos de um cliente. */
    public List<Emprestimo> listarPorCliente(String cpf) throws SQLException {
        String sql = "SELECT * FROM Emprestimos WHERE cpf_cliente = ?";
        List<Emprestimo> lista = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, cpf);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    /** Lista todos os empréstimos de um livro. */
    public List<Emprestimo> listarPorLivro(int livroId) throws SQLException {
        String sql = "SELECT * FROM Emprestimos WHERE id_livro = ?";
        List<Emprestimo> lista = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, livroId);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    //verifica se o livro está em um emprestimo ativo
    public boolean livroPossuiEmprestimoAtivo(int idLivro) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Emprestimos WHERE id_livro = ? AND status = 'Ativo'";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, idLivro);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private Emprestimo mapear(ResultSet rs)throws SQLException{
        int id=rs.getInt("id");
        Date demp=rs.getDate("dataEmprestimo");
        Date dprev=rs.getDate("dataDevolucaoPrevista");
        Date dreal=rs.getDate("dataDevolucaoReal");
        StatusEmprestimo st=StatusEmprestimo.valueOf(rs.getString("status"));
        double multa=rs.getDouble("valorMulta");
        boolean paga=rs.getBoolean("multaPaga");
        int lid=rs.getInt("id_livro");
        String cpf=rs.getString("cpf_cliente");

        Livro l=new LivroDAO().buscarPorId(lid);
        Cliente c=new ClienteDAO().buscarPorCpf(cpf);

        Emprestimo e=new Emprestimo(id,demp,dprev,l,c);
        e.setDataDevolucaoReal(dreal);
        e.setStatus(st);
        e.setValorMulta(multa);
        e.setMultaPaga(paga);
        return e;
    }
}
