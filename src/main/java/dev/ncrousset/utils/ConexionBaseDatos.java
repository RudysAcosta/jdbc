package dev.ncrousset.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {

    private static Connection connection;
    private static String url = "jdbc:mysql://127.0.0.1:3306/java_curso";
    private static String username =  "root";
    private static String password = "secret";

    private ConexionBaseDatos() {}

    public static Connection getInstance() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, username, password);
        }

        return connection;
    }
}
