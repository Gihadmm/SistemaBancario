package repository;

import Model.Emprestimo;
import Model.Livro;
import Model.StatusEmprestimo;
import Model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe responsável pelas operações de persistência relacionadas à tabela Emprestimos.
 * Permite inserir, atualizar e consultar empréstimos no banco de dados.
 */
public class EmprestimoDAO {

    /**
     * Insere um novo empréstimo no banco de dados.
     * Após a inserção, o ID gerado é atribuído ao objeto.
     */
    public void inserir(Emprestimo e) throws SQLException {
        String sql = """
            INSERT INTO Emprestimos 
            (cpf_cliente, id_livro, dataEmprestimo, dataDevolucaoPrevista, status, valorMulta, multaPaga)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            p.setString(1, e.getCliente().getCpf());
            p.setInt(2, e.getLivro().getId());
            p.setDate(3, new java.sql.Date(e.getDataEmprestimo().getTime()));
            p.setDate(4, new java.sql.Date(e.getDataDevolucaoPrevista().getTime()));
            p.setString(5, e.getStatus().name());
            p.setDouble(6, e.getValorMulta());
            p.setBoolean(7, e.isMultaPaga());

            p.executeUpdate();

            try (ResultSet rs = p.getGeneratedKeys()) {
                if (rs.next()) e.setId(rs.getInt(1));
            }
        }
    }

    /** Busca um empréstimo pelo ID. */
    public Emprestimo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Emprestimos WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, id);
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    /** Atualiza os dados relacionados à devolução de um empréstimo. */
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

    /** Atualiza a data de devolução prevista (renovação). */
    public void atualizarRenovacao(Emprestimo e) throws SQLException {
        String sql = "UPDATE Emprestimos SET dataDevolucaoPrevista = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setDate(1, new java.sql.Date(e.getDataDevolucaoPrevista().getTime()));
            p.setInt(2, e.getId());

            p.executeUpdate();
        }
    }

    /** Lista todos os empréstimos de um cliente com base no CPF. */
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

    /** Lista todos os empréstimos de um livro pelo ID. */
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

    /** Verifica se um livro possui algum empréstimo com status ATIVO. */
    public boolean livroPossuiEmprestimoAtivo(int idLivro) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Emprestimos WHERE id_livro = ? AND status = 'ATIVO'";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, idLivro);
            try (ResultSet rs = p.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    /** Lista todos os empréstimos registrados no sistema. */
    public List<Emprestimo> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Emprestimos";
        List<Emprestimo> lista = new ArrayList<>();

        try (Connection c = DatabaseConnection.getConnection();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    /** Constrói um objeto Emprestimo a partir de um ResultSet. */
    private Emprestimo mapear(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Date dataEmprestimo = rs.getDate("dataEmprestimo");
        Date dataPrevista = rs.getDate("dataDevolucaoPrevista");
        Date dataReal = rs.getDate("dataDevolucaoReal");
        StatusEmprestimo status = StatusEmprestimo.valueOf(rs.getString("status"));
        double multa = rs.getDouble("valorMulta");
        boolean paga = rs.getBoolean("multaPaga");
        int idLivro = rs.getInt("id_livro");
        String cpfCliente = rs.getString("cpf_cliente");

        Livro livro = new LivroDAO().buscarPorId(idLivro);
        Cliente cliente = new ClienteDAO().buscarPorCpf(cpfCliente);

        Emprestimo e = new Emprestimo(id, dataEmprestimo, dataPrevista, livro, cliente);
        e.setDataDevolucaoReal(dataReal);
        e.setStatus(status);
        e.setValorMulta(multa);
        e.setMultaPaga(paga);

        return e;
    }
}
