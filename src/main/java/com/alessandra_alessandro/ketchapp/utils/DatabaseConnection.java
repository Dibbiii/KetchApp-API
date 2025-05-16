package com.alessandra_alessandro.ketchapp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String HOST = System.getenv("DB_HOST");
    private static final String PORT = System.getenv("DB_PORT");
    private static final String DATABASE = System.getenv("DB_NAME");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");
    private static final String SCHEMA = "public";
    private static final String POOL_MODE = "transaction";

    private static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE +
            "?user=" + USER + "&password=" + PASSWORD + "&currentSchema=" + SCHEMA + "&pool_mode=" + POOL_MODE;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}