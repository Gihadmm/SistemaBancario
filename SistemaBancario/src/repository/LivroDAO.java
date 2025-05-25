package repository;

import Model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    /**
     * Insere um novo livro e atribui o ID gerado ao objeto.
     */
    public void inserir(Livro livro) throws SQLException {
        String sql = """
            INSERT INTO Livros
              (titulo, autor, genero, anoPublicacao, editora, isbn,
               quantidadeExemplares, disponivel)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
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

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    livro.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Retorna todos os livros cadastrados.
     */
    public List<Livro> buscarTodos() throws SQLException {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Livros";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearLivro(rs));
            }
        }
        return lista;
    }

    /**
     * Busca um livro pelo ID.
     */
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

    /**
     * Busca livros cujo título contenha o parâmetro.
     */
    public List<Livro> buscarPorTitulo(String titulo) throws SQLException {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Livros WHERE titulo LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + titulo + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearLivro(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Busca livros cujo autor contenha o parâmetro.
     */
    public List<Livro> buscarPorAutor(String autor) throws SQLException {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Livros WHERE autor LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + autor + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearLivro(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Busca livros cujo gênero contenha o parâmetro.
     */
    public List<Livro> buscarPorGenero(String genero) throws SQLException {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Livros WHERE genero LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + genero + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearLivro(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Atualiza todos os campos de um livro.
     */
    public void atualizar(Livro livro) throws SQLException {
        String sql = """
            UPDATE Livros
               SET titulo = ?, autor = ?, genero = ?, anoPublicacao = ?,
                   editora = ?, isbn = ?, quantidadeExemplares = ?, disponivel = ?
             WHERE id = ?
        """;
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
            stmt.setInt(9, livro.getId());

            stmt.executeUpdate();
        }
    }

    /**
     * Atualiza apenas a disponibilidade de um livro.
     */
    public void atualizarDisponibilidade(int id, boolean disponivel) throws SQLException {
        String sql = "UPDATE Livros SET disponivel = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, disponivel);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Remove um livro pelo ID.
     */
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Livros WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Mapeia uma linha de ResultSet para um objeto Livro.
     */
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
        // Disponibilidade
        livro.setDisponivel(rs.getBoolean("disponivel"));
        return livro;
    }
}
