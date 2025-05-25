package repository;

import Model.Reserva;
import Model.Cliente;
import Model.Livro;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ReservaDAO {

    /**
     * Insere uma nova reserva, incluindo a data prevista de disponibilidade
     */
    public void inserir(Reserva r) throws SQLException {
        String sql = "INSERT INTO Reservas (cpf_cliente, id_livro, data_reserva, data_disponibilidade_prevista) VALUES (?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            p.setString(1, r.getCliente().getCpf());
            p.setInt(2, r.getLivro().getId());
            p.setDate(3, new java.sql.Date(r.getDataReserva().getTime()));
            p.setDate(4, new java.sql.Date(r.getDataDisponibilidadePrevista().getTime()));
            p.executeUpdate();

            try (ResultSet rs = p.getGeneratedKeys()) {
                if (rs.next()) {
                    r.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Lista todas as reservas de um cliente, incluindo a data prevista
     */
    public List<Reserva> listarPorCliente(String cpf) throws SQLException {
        String sql = "SELECT * FROM Reservas WHERE cpf_cliente = ?";
        List<Reserva> lista = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, cpf);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Livro livro = new LivroDAO().buscarPorId(rs.getInt("id_livro"));
                    Cliente cliente = new ClienteDAO().buscarPorCpf(cpf);
                    Date dataRes = rs.getDate("data_reserva");
                    Date dataDisp = rs.getDate("data_disponibilidade_prevista");

                    Reserva r = new Reserva(cliente, livro, dataRes, dataDisp);
                    r.setId(rs.getInt("id"));
                    lista.add(r);
                }
            }
        }
        return lista;
    }

    public boolean temReservasAtivas(int livroId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Reservas WHERE id_livro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, livroId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

}
