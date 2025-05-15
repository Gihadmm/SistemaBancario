package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // enderço DNS do AWS
    private static final String HOST = "ec2-54-210-31-92.compute-1.amazonaws.com";
    private static final int    PORT = 1433;

    // Nome do BD
    private static final String DATABASE = "Biblioteca";

    // Service.LoginService do BD
    private static final String USER = "sa";
    private static final String PASS = "pao123";

    // 4) Montando a URL JDBC
    private static final String URL = String.format(
            "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false;trustServerCertificate=true;",
            HOST, PORT, DATABASE
    );

    // construtor privado para evitar instância
    private DatabaseConnection() { }
 
    // método que será usado pelas suas DAOs
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
