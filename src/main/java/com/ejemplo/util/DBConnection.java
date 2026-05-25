package com.ejemplo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {


    public static Connection getConnection() throws SQLException {
        // Obtenemos las variables que Railway inyecta automáticamente, o usamos las de Docker si no existen
        String host = System.getenv("MYSQLHOST") != null ? System.getenv("MYSQLHOST") : "mysql";
        String port = System.getenv("MYSQLPORT") != null ? System.getenv("MYSQLPORT") : "3306";
        String database = System.getenv("MYSQLDATABASE") != null ? System.getenv("MYSQLDATABASE") : "21grounds_db";
        String user = System.getenv("MYSQLUSER") != null ? System.getenv("MYSQLUSER") : "root";
        String password = System.getenv("MYSQLPASSWORD") != null ? System.getenv("MYSQLPASSWORD") : "root";
        
        // Formamos la URL de conexión de forma dinámica
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el driver de la base de datos", e);
        }
    }
}
