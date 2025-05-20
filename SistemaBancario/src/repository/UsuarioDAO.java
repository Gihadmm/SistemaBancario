package repository;
import Model.Usuario;
import Model.Cliente;
import Model.Administrador;
import Model.Acesso;
import java.sql.*;

public class UsuarioDAO {
    public Usuario buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM Usuarios WHERE cpf = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;
                String acesso = rs.getString("acesso");
                if (Acesso.ADMINISTRADOR.name().equals(acesso)) {
                    return new Administrador(
                            rs.getString("cpf"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                } else {
                    return new Cliente(
                            rs.getString("cpf"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                }
            }
        }
    }

    public void inserirCliente(Cliente c) throws SQLException {
        String sql = "INSERT INTO Usuarios(cpf,nome,email,senha,acesso) VALUES(?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getCpf());
            stmt.setString(2, c.getNome());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, c.getSenha());
            stmt.setString(5, Acesso.CLIENTE.name());
            stmt.executeUpdate();
        }
    }

    public Usuario buscarPorEmailSenha(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM Usuarios WHERE email = ? AND senha = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;

                String acesso = rs.getString("acesso");
                if (Acesso.ADMINISTRADOR.name().equals(acesso)) {
                    return new Administrador(
                            rs.getString("cpf"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                } else {
                    return new Cliente(
                            rs.getString("cpf"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                }
            }
        }
    }

    /**
     * Insere um administrador no sistema.
     */
    public void inserirAdministrador(Administrador a) throws SQLException {
        String sql = "INSERT INTO Usuarios(cpf,nome,email,senha,acesso) VALUES(?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, a.getCpf());
            stmt.setString(2, a.getNome());
            stmt.setString(3, a.getEmail());
            stmt.setString(4, a.getSenha());
            stmt.setString(5, Acesso.ADMINISTRADOR.name());
            stmt.executeUpdate();
        }
    }
}
