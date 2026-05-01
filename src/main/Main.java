package main;

import main.database.DatabaseConnection;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting St Mary's Digital Library System...");
        DatabaseConnection.testConnection();
    }
}