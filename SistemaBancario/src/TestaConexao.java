import repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaConexao {

    public static void main(String[] args) {
        System.out.println("→ Iniciando teste de conexão JDBC...");

        // 1) Tenta abrir a conexão
        try (Connection conn = DatabaseConnection.getConnection()) {

            // 2) Verifica se a conexão está aberta
            if (conn != null && !conn.isClosed()) {
                System.out.println("✔️  Conexão aberta com sucesso!");

                // 3) (Opcional) Executa um SELECT simples
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT DB_NAME() AS NomeDoBanco")) {

                    if (rs.next()) {
                        String nomeBanco = rs.getString("NomeDoBanco");
                        System.out.println("🏷️  Banco conectado: " + nomeBanco);
                    }
                }
            } else {
                System.err.println("❌  A conexão retornou nula ou já estava fechada.");
            }

        } catch (SQLException e) {
            System.err.println("❌  Erro ao conectar:");
            e.printStackTrace();
        }
    }
}