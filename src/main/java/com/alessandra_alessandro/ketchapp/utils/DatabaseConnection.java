package com.alessandra_alessandro.ketchapp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String HOST = "aws-0-eu-central-1.pooler.supabase.com";
    private static final String PORT = "6543";
    private static final String DATABASE = "postgres";
    private static final String USER = "postgres.rxdfayaqsbtfmhfmaids";
    private static final String PASSWORD = "Gqi5HDeui3!";
    private static final String SCHEMA = "public";
    private static final String POOL_MODE = "transaction";

    private static final String URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE +
            "?user=" + USER + "&password=" + PASSWORD + "&currentSchema=" + SCHEMA + "&pool_mode=" + POOL_MODE;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

