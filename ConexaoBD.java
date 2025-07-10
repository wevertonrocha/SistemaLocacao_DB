// ConexaoBD.java
package sistemalocacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String URL = "jdbc:mysql://localhost:3306/sistemalocacao_db";
    private static final String USER = "root"; //usuario BD
    private static final String PASSWORD = ""; //Senha BD

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
