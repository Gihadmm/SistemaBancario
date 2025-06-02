package repository;

import Model.Cliente;
import Model.Acesso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas operações de persistência relacionadas aos clientes (acesso = 'CLIENTE').
 * Utiliza a tabela 'Usuarios' do banco de dados.
 */
public class ClienteDAO {

    /**
     * Insere um novo cliente na tabela 'Usuarios'.
     *
     * @param cliente o objeto Cliente a ser inserido
     * @throws SQLException em caso de falha ao interagir com o banco
     */
    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Usuarios (cpf, nome, email, senha, acesso) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getSenha());
            stmt.setString(5, Acesso.CLIENTE.name()); // Define o tipo de acesso como CLIENTE

            stmt.executeUpdate(); // Executa a inserção
        }
    }

    /**
     * Busca um cliente pelo CPF (somente se for CLIENTE).
     *
     * @param cpf o CPF do cliente
     * @return Cliente correspondente ou null se não encontrado
     * @throws SQLException em caso de falha no banco
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
     * Lista todos os clientes cadastrados no sistema.
     *
     * @return Lista de todos os objetos Cliente
     * @throws SQLException em caso de erro na consulta
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
     * Atualiza os dados (nome, email, senha) de um cliente.
     *
     * @param cliente objeto Cliente com dados atualizados
     * @throws SQLException em caso de falha ao atualizar
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
     * Remove um cliente da base de dados.
     *
     * @param cpf CPF do cliente a ser removido
     * @throws SQLException em caso de erro na exclusão
     */
    public void deletar(String cpf) throws SQLException {
        String sql = "DELETE FROM Usuarios WHERE cpf = ? AND acesso = 'CLIENTE'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }

    /**
     * Método auxiliar para mapear um ResultSet para um objeto Cliente.
     *
     * @param rs resultado da consulta
     * @return objeto Cliente
     * @throws SQLException em caso de erro de leitura
     */
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        String cpf = rs.getString("cpf");
        String nome = rs.getString("nome");
        String email = rs.getString("email");
        String senha = rs.getString("senha");

        return new Cliente(cpf, nome, email, senha);
    }

    /**
     * Método alternativo (alias) para buscar um cliente pelo CPF.
     *
     * @param cpf CPF do cliente
     * @return Cliente correspondente
     * @throws SQLException em caso de erro
     */
    public Cliente buscarPorId(String cpf) throws SQLException {
        return buscarPorCpf(cpf); // Reaproveita a lógica do método buscarPorCpf
    }
}
