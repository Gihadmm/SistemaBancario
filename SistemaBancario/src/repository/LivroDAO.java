package repository;

import Model.Livro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    // 1) INSERIR um novo livro
    public void inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO Livros (titulo, autor, genero, anoPublicacao, editora, isbn, quantidadeExemplares, disponivel, reservado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    livro.setId(rs.getInt(1));
                }
            }
        }
    }

    // 2) BUSCAR todos os livros
    public List<Livro> buscarTodos() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livros";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                livros.add(mapearLivro(rs));
            }
        }
        return livros;
    }

    // 3) BUSCAR livro por ID
    public Livro buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Livros WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearLivro(rs);
                }
            }
        }
        return null;
    }

    // 🔍 NOVO: Buscar por título (parcial ou completo)
    public List<Livro> buscarPorTitulo(String titulo) throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livros WHERE titulo LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + titulo + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    livros.add(mapearLivro(rs));
                }
            }
        }
        return livros;
    }

    // 🔍 NOVO: Buscar por autor
    public List<Livro> buscarPorAutor(String autor) throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livros WHERE autor LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + autor + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    livros.add(mapearLivro(rs));
                }
            }
        }
        return livros;
    }

    // 🔍 NOVO: Buscar por gênero
    public List<Livro> buscarPorGenero(String genero) throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livros WHERE genero LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + genero + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    livros.add(mapearLivro(rs));
                }
            }
        }
        return livros;
    }

    // 4) ATUALIZAR todos os campos de um livro
    public void atualizar(Livro livro) throws SQLException {
        String sql = "UPDATE Livros SET titulo = ?, autor = ?, genero = ?, anoPublicacao = ?, " +
                "editora = ?, isbn = ?, quantidadeExemplares = ?, disponivel = ?, reservado = ? " +
                "WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setInt(4, livro.getAnoPublicacao());
            stmt.setString(5, livro.getEditora());
            stmt.setString(6, livro.getIsbn());
            stmt.setInt(7, livro.getQuantidadeExemplares());
            stmt.setBoolean(8, livro.isDisponivel());
            stmt.setBoolean(9, livro.isReservado());
            stmt.setInt(10, livro.getId());

            stmt.executeUpdate();
        }
    }

    // 5) ATUALIZAR apenas a disponibilidade
    public void atualizarDisponibilidade(int id, boolean disponivel) throws SQLException {
        String sql = "UPDATE Livros SET disponivel = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, disponivel);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    // 6) DELETAR um livro pelo ID
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Livros WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // ——— Método auxiliar para mapear um ResultSet a um objeto Livro ———
    private Livro mapearLivro(ResultSet rs) throws SQLException {
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
        // Caso tenha as colunas no banco, descomente:
        //livro.setDisponivel(rs.getBoolean("disponivel"));
        //livro.setReservado(rs.getBoolean("reservado"));
        return livro;
    }
}
