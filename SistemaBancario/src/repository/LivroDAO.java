package repository;

import Model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public void inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO Livros (titulo, autor, genero, anoPublicacao, editora, isbn, quantidadeExemplares, disponivel, reservado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setInt(4, livro.getAnoPublicacao());
            stmt.setString(5, livro.getEditora());
            stmt.setString(6, livro.getIsbn());
            stmt.setInt(7, livro.getQuantidadeExemplares());
            stmt.setBoolean(8, livro.isDisponivel());
            stmt.setBoolean(9, livro.isReservado());

            stmt.executeUpdate();

            // Recuperar ID gerado automaticamente, se for auto_increment
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                livro.setId(rs.getInt(1));
            }
        }
    }

    public Livro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Livros WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Livro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("genero"),
                        rs.getInt("anoPublicacao"),
                        rs.getString("editora"),
                        rs.getString("isbn"),
                        rs.getInt("quantidadeExemplares")
                );
            }
            return null;
        }
    }

    public List<Livro> buscarTodos() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livros";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("genero"),
                        rs.getInt("anoPublicacao"),
                        rs.getString("editora"),
                        rs.getString("isbn"),
                        rs.getInt("quantidadeExemplares")
                );
                livro.setDisponivel(rs.getBoolean("disponivel"));
                livro.setReservado(rs.getBoolean("reservado"));
                livros.add(livro);
            }
        }
        return livros;
    }

    public void atualizarDisponibilidade(int id, boolean disponivel) throws SQLException {
        String sql = "UPDATE Livros SET disponivel = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, disponivel);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Livros WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}