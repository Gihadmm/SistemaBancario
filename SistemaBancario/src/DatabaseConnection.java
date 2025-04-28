import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Método para obter a conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        // Defina a URL de conexão, usuário e senha
        String url = "jdbc:sqlserver://localhost:1433;databaseName=seuBancoDeDados"; // Substitua com suas informações
        String user = "seuUsuario";  // Substitua com seu nome de usuário
        String password = "suaSenha"; // Substitua com sua senha

        // Retorna a conexão com o banco de dados
        return DriverManager.getConnection(url, user, password);
    }
}
