package main.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DATABASE_URL = "jdbc:sqlite:database/library.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void testConnection() {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Database connected");
            }
        } catch (SQLException e) {
            System.out.println("Falied database connected");
            System.out.println("Error: " + e.getMessage());
        }
    }
}