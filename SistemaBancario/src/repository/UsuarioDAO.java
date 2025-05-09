package repository;

import Model.Usuario;
import Model.Administrador;
import Model.Cliente;
import Model.Acesso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DAO para operações CRUD e contagem sobre a tabela Usuarios.
 */
public class UsuarioDAO {

    /**
     * Conta quantos usuários existem na tabela.
     * @return número total de usuários
     * @throws SQLException em caso de erro de acesso ao banco
     */
    public int countUsuarios() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Usuarios";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }

    /**
     * Insere um novo usuário (Administrador ou Cliente) na tabela.
     * @param u objeto Usuario a ser inserido
     * @throws SQLException em caso de erro de acesso ao banco
     */
    public void inserirUsuario(Usuario u) throws SQLException {
        String sql = "INSERT INTO Usuarios (cpf, nome, email, senha, acesso) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getCpf());
            stmt.setString(2, u.getNome());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getSenha());
            stmt.setString(5, u.getAcesso().name());

            stmt.executeUpdate();
        }
    }


    /**
     * Atualiza a senha de um usuário dado o e-mail.
     * @return true se atualizou; false se e-mail não existe.
     */
    public boolean atualizarSenha(String email, String novaSenha) throws SQLException {
        String sql = "UPDATE Usuarios SET senha = ? WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
        }
    }




    /**
     * Busca um usuário pelo e-mail e senha.
     * Retorna uma instância de Administrador ou Cliente, conforme o campo acesso.
     * @param email  e-mail informado
     * @param senha  senha informada
     * @return Usuario (Administrador ou Cliente) ou null se não encontrado
     * @throws SQLException em caso de erro de acesso ao banco
     */
    public Usuario buscarPorEmailSenha(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM Usuarios WHERE email = ? AND senha = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String cpf       = rs.getString("cpf");
                    String nome      = rs.getString("nome");
                    Acesso acesso    = Acesso.valueOf(rs.getString("acesso"));

                    if (acesso == Acesso.ADMINISTRADOR) {
                        return new Administrador(cpf, nome, email, senha);
                    } else {
                        return new Cliente(cpf, nome, email, senha);
                    }
                }
            }
        }
        return null;
    }
}
