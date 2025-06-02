package repository;

import Model.Exemplar;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO responsável pelo gerenciamento dos Exemplares de livros no banco de dados.
 * Permite inserir, buscar, atualizar e excluir registros de exemplares.
 */
public class ExemplarDAO {

    /**
     * Insere um novo exemplar no banco e define seu ID gerado automaticamente.
     *
     * @param ex Exemplar a ser inserido
     * @throws SQLException em caso de erro de acesso ao banco
     */
    public void inserir(Exemplar ex) throws SQLException {
        String sql = "INSERT INTO Exemplares (livro_id, numero_copia, disponivel) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ex.getLivroId());
            stmt.setInt(2, ex.getNumeroCopia());
            stmt.setBoolean(3, ex.isDisponivel());
            stmt.executeUpdate();

            // Obtém e define o ID gerado
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    ex.setId(keys.getInt(1));
                }
            }
        }
    }

    /**
     * Busca um exemplar pelo seu ID.
     *
     * @param id ID do exemplar
     * @return o Exemplar encontrado ou null
     */
    public Exemplar buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Exemplares WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearExemplar(rs);
                }
            }
        }
        return null;
    }

    /**
     * Busca todos os exemplares de um livro específico.
     *
     * @param livroId ID do livro
     * @return lista de exemplares do livro
     */
    public List<Exemplar> buscarPorLivro(int livroId) throws SQLException {
        List<Exemplar> lista = new ArrayList<>();
        String sql = "SELECT * FROM Exemplares WHERE livro_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, livroId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearExemplar(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Retorna todos os exemplares disponíveis no sistema.
     *
     * @return lista de exemplares com disponivel = true
     */
    public List<Exemplar> buscarTodosDisponiveis() throws SQLException {
        List<Exemplar> lista = new ArrayList<>();
        String sql = "SELECT * FROM Exemplares WHERE disponivel = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearExemplar(rs));
            }
        }
        return lista;
    }

    /**
     * Atualiza os dados de um exemplar no banco.
     *
     * @param ex Exemplar com informações atualizadas
     */
    public void atualizar(Exemplar ex) throws SQLException {
        String sql = "UPDATE Exemplares SET livro_id = ?, numero_copia = ?, disponivel = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ex.getLivroId());
            stmt.setInt(2, ex.getNumeroCopia());
            stmt.setBoolean(3, ex.isDisponivel());
            stmt.setInt(4, ex.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Remove um exemplar do banco pelo seu ID.
     *
     * @param id ID do exemplar a ser removido
     */
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Exemplares WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Mapeia uma linha do ResultSet para um objeto Exemplar.
     *
     * @param rs ResultSet com os dados
     * @return objeto Exemplar construído
     */
    private Exemplar mapearExemplar(ResultSet rs) throws SQLException {
        return new Exemplar(
                rs.getInt("id"),
                rs.getInt("numero_copia"),
                rs.getInt("livro_id"),
                rs.getBoolean("disponivel")
        );
    }
}
