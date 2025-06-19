package com.example.gallery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String DB_URL =
            "jdbc:postgresql://localhost:5432/gallerydb";
    private static final String DB_USER = "gallery_user";
    private static final String DB_PASSWORD = "gallery_pass";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
