package repository;
import Model.Usuario;
import Model.Cliente;
import Model.Administrador;
import Model.Acesso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Usuario> buscarPorNome(String nome) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios WHERE nome LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearUsuario(rs));
                }
            }
        }
        return lista;
    }


    public List<Usuario> buscarPorEmail(String email) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios WHERE email LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + email + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String acesso = rs.getString("acesso");
                    Usuario u;
                    if (Acesso.ADMINISTRADOR.name().equals(acesso)) {
                        u = new Administrador(
                                rs.getString("cpf"),
                                rs.getString("nome"),
                                rs.getString("email"),
                                rs.getString("senha")
                        );
                    } else {
                        u = new Cliente(
                                rs.getString("cpf"),
                                rs.getString("nome"),
                                rs.getString("email"),
                                rs.getString("senha")
                        );
                    }
                    lista.add(u);
                }
            }
        }
        return lista;
    }



    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
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

    public void atualizarCliente(Cliente c) throws SQLException {
        String sql = "UPDATE Usuarios SET nome=?, email=?, senha=? WHERE cpf=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getEmail());
            stmt.setString(3, c.getSenha());
            stmt.setString(4, c.getCpf());
            stmt.executeUpdate();
        }
    }

    public void atualizarAdministrador(Administrador a) throws SQLException {
        String sql = "UPDATE Usuarios SET nome=?, email=?, senha=? WHERE cpf=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getEmail());
            stmt.setString(3, a.getSenha());
            stmt.setString(4, a.getCpf());
            stmt.executeUpdate();
        }
    }


    public List<Usuario> buscarPorCpfParcial(String cpf) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios WHERE cpf LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + cpf + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario u = (rs.getString("acesso").equals("ADMINISTRADOR")) ?
                            new Administrador(rs.getString("cpf"), rs.getString("nome"), rs.getString("email"), rs.getString("senha")) :
                            new Cliente(rs.getString("cpf"), rs.getString("nome"), rs.getString("email"), rs.getString("senha"));
                    lista.add(u);
                }
            }
        }
        return lista;
    }

    public void atualizar(Usuario u) throws SQLException {
        String sql = "UPDATE Usuarios SET nome = ?, email = ?, senha = ? WHERE cpf = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.setString(4, u.getCpf());

            stmt.executeUpdate();
        }
    }


}
