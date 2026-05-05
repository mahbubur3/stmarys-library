package main.database;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseOptimizer {
    public static void createIndexes() {
        String[] indexQueries = {
                "CREATE INDEX IF NOT EXISTS idx_books_title ON books(title);",
                "CREATE INDEX IF NOT EXISTS idx_books_author ON books(author);",
                "CREATE INDEX IF NOT EXISTS idx_books_category ON books(category);",
                "CREATE INDEX IF NOT EXISTS idx_members_name ON members(member_name);",
                "CREATE INDEX IF NOT EXISTS idx_members_email ON members(email);",
                "CREATE INDEX IF NOT EXISTS idx_borrow_book_id ON borrow_records(book_id);",
                "CREATE INDEX IF NOT EXISTS idx_borrow_member_id ON borrow_records(member_id);",
                "CREATE INDEX IF NOT EXISTS idx_borrow_date ON borrow_records(borrow_date);",
                "CREATE INDEX IF NOT EXISTS idx_borrow_due_date ON borrow_records(due_date);",
                "CREATE INDEX IF NOT EXISTS idx_borrow_status ON borrow_records(return_status);"
        };

        try (Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement()) {
            for (String query : indexQueries) {
                statement.execute(query);
            }
            
            System.out.println("Database indexes created successfully.");

        } catch (SQLException e) {
            System.out.println("Failed to create database indexes.");
            System.out.println("Error: " + e.getMessage());
        }
    }
}