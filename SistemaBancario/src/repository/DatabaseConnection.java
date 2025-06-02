package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por fornecer conexões com o banco de dados SQL Server.
 * Utiliza parâmetros de conexão definidos estaticamente e é usada em todas as DAOs do sistema.
 */
public class DatabaseConnection {

    // Endereço DNS público da instância EC2 (AWS) onde o banco está hospedado
    private static final String HOST = "ec2-3-81-172-170.compute-1.amazonaws.com";

    // Porta padrão do SQL Server
    private static final int PORT = 1433;

    // Nome do banco de dados
    private static final String DATABASE = "Biblioteca";

    // Credenciais de acesso ao banco
    private static final String USER = "sa";
    private static final String PASS = "pao123";

    // String de conexão JDBC para SQL Server
    // "encrypt=false" e "trustServerCertificate=true" usados para evitar problemas de SSL
    private static final String URL = String.format(
            "jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false;trustServerCertificate=true;",
            HOST, PORT, DATABASE
    );

    // Construtor privado para impedir instanciação da classe
    private DatabaseConnection() { }

    /**
     * Fornece uma nova conexão com o banco de dados.
     * @return objeto Connection pronto para uso
     * @throws SQLException em caso de erro na conexão
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
