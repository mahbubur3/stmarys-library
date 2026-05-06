package main.dao;
import main.database.DatabaseConnection;
import main.models.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (title, author, category, availability_status) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getCategory());
            preparedStatement.setString(4, book.getAvailabilityStatus());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
            return false;
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Book book = new Book(
                    resultSet.getInt("book_id"),
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("category"),
                    resultSet.getString("availability_status")
                );

                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving books: " + e.getMessage());
        }

        return books;
    }

    public Book searchBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                return new Book(
                    resultSet.getInt("book_id"),
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("category"),
                    resultSet.getString("availability_status")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error searching book by ID: " + e.getMessage());
        }

        return null;
    }

    public List<Book> searchBooksByTitleOrAuthor(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book(
                    resultSet.getInt("book_id"),
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("category"),
                    resultSet.getString("availability_status")
                );

                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error searching books: " + e.getMessage());
        }

        return books;
    }

    public boolean updateBook(Book book) {
        String sql = """
                UPDATE books 
                SET title = ?, author = ?, category = ?, availability_status = ?
                WHERE book_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getCategory());
            preparedStatement.setString(4, book.getAvailabilityStatus());
            preparedStatement.setInt(5, book.getBookId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating book: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE book_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
            return false;
        }
    }

    public List<Book> advancedSearchBooks(String title, String author, String category, String sortOrder) {
        List<Book> books = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM books WHERE 1=1");

        if (title != null && !title.trim().isEmpty()) {
            sql.append(" AND title LIKE ?");
        }

        if (author != null && !author.trim().isEmpty()) {
            sql.append(" AND author LIKE ?");
        }

        if (category != null && !category.trim().isEmpty()) {
            sql.append(" AND category LIKE ?");
        }

        if (sortOrder != null && sortOrder.equalsIgnoreCase("DESC")) {
            sql.append(" ORDER BY title DESC");
        } else {
            sql.append(" ORDER BY title ASC");
        }

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

            int parameterIndex = 1;

            if (title != null && !title.trim().isEmpty()) {
                preparedStatement.setString(parameterIndex, "%" + title + "%");
                parameterIndex++;
            }

            if (author != null && !author.trim().isEmpty()) {
                preparedStatement.setString(parameterIndex, "%" + author + "%");
                parameterIndex++;
            }

            if (category != null && !category.trim().isEmpty()) {
                preparedStatement.setString(parameterIndex, "%" + category + "%");
                parameterIndex++;
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("category"),
                        resultSet.getString("availability_status")
                );

                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error performing advanced book search: " + e.getMessage());
        }

        return books;
    }

    public List<Book> filterBooksByCategory(String category, String sortOrder) {
        List<Book> books = new ArrayList<>();

        String sql;

        if (sortOrder != null && sortOrder.equalsIgnoreCase("DESC")) {
            sql = "SELECT * FROM books WHERE category LIKE ? ORDER BY title DESC";
        } else {
            sql = "SELECT * FROM books WHERE category LIKE ? ORDER BY title ASC";
        }

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + category + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("category"),
                        resultSet.getString("availability_status")
                );

                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error filtering books by category: " + e.getMessage());
        }

        return books;
    }

    public List<Book> getBooksSortedByTitle(String sortOrder) {
        List<Book> books = new ArrayList<>();

        String sql;

        if (sortOrder != null && sortOrder.equalsIgnoreCase("DESC")) {
            sql = "SELECT * FROM books ORDER BY title DESC";
        } else {
            sql = "SELECT * FROM books ORDER BY title ASC";
        }

        try (Connection connection = DatabaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("category"),
                        resultSet.getString("availability_status")
                );

                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error sorting books: " + e.getMessage());
        }

        return books;
    }
}