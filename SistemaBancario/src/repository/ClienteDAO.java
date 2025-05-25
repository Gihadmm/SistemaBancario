package repository;

import Model.Cliente;
import Model.Acesso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    /**
     * Insere um novo cliente na tabela Usuarios.
     *
     * @param cliente o objeto Cliente a inserir
     * @throws SQLException em caso de erro de banco
     */
    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Usuarios (cpf, nome, email, senha, acesso) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getSenha());
            stmt.setString(5, Acesso.CLIENTE.name());

            stmt.executeUpdate();
        }
    }

    /**
     * Busca um cliente pelo CPF (só retorna se for acesso CLIENTE).
     *
     * @param cpf o CPF do cliente
     * @return o Cliente ou null se não achar
     * @throws SQLException em caso de erro de banco
     */
    public Cliente buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM Usuarios WHERE cpf = ? AND acesso = 'CLIENTE'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }
        return null;
    }

    /**
     * Lista todos os clientes (registros com acesso = 'CLIENTE').
     *
     * @return lista de todos os Clientes
     * @throws SQLException em caso de erro de banco
     */
    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios WHERE acesso = 'CLIENTE'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        }
        return lista;
    }

    /**
     * Atualiza nome, email e senha de um cliente existente.
     *
     * @param cliente o Cliente com dados atualizados
     * @throws SQLException em caso de erro de banco
     */
    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE Usuarios SET nome = ?, email = ?, senha = ? " +
                "WHERE cpf = ? AND acesso = 'CLIENTE'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getSenha());
            stmt.setString(4, cliente.getCpf());

            stmt.executeUpdate();
        }
    }

    /**
     * Remove um cliente (registros com acesso = 'CLIENTE').
     *
     * @param cpf CPF do cliente a remover
     * @throws SQLException em caso de erro de banco
     */
    public void deletar(String cpf) throws SQLException {
        String sql = "DELETE FROM Usuarios WHERE cpf = ? AND acesso = 'CLIENTE'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }

    // ——— Método auxiliar para mapear um ResultSet em Cliente ———
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        String cpf = rs.getString("cpf");
        String nome = rs.getString("nome");
        String email = rs.getString("email");
        String senha = rs.getString("senha");

        return new Cliente(cpf, nome, email, senha);
    }

    public Cliente buscarPorId(String cpf) throws SQLException {
        // reutiliza a busca por CPF
        return buscarPorCpf(cpf);
    }

}
