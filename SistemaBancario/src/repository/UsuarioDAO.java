package repository;

import Model.*;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario buscarPorEmailSenha(String email, String senha) {
        String sql = "SELECT * FROM Usuarios WHERE email = ? AND senha = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String cpf       = rs.getString("cpf");
                String nome      = rs.getString("nome");
                String acessoStr = rs.getString("acesso");

                Acesso acesso = Acesso.valueOf(acessoStr.toUpperCase());
                if (acesso == Acesso.ADMINISTRADOR) {
                    return new Administrador(cpf, nome, email, senha);
                } else {
                    return new Cliente(cpf, nome, email, senha);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro no DAO: " + e.getMessage());
        }

        return null;
    }
}