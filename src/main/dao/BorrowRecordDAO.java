package main.dao;
import main.database.DatabaseConnection;
import main.models.BorrowRecord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowRecordDAO {
    public boolean addBorrowRecord(BorrowRecord record) {
        String sql = """
                INSERT INTO borrow_records 
                (book_id, member_id, borrow_date, due_date, return_status)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, record.getBookId());
            preparedStatement.setInt(2, record.getMemberId());
            preparedStatement.setString(3, record.getBorrowDate());
            preparedStatement.setString(4, record.getDueDate());
            preparedStatement.setString(5, record.getReturnStatus());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error adding borrow record: " + e.getMessage());
            return false;
        }
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        List<BorrowRecord> records = new ArrayList<>();

        String sql = "SELECT * FROM borrow_records";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                BorrowRecord record = new BorrowRecord(
                        resultSet.getInt("record_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getInt("member_id"),
                        resultSet.getString("borrow_date"),
                        resultSet.getString("due_date"),
                        resultSet.getString("return_status")
                );

                records.add(record);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving borrow records: " + e.getMessage());
        }
        return records;
    }

    public BorrowRecord searchBorrowRecordById(int recordId) {
        String sql = "SELECT * FROM borrow_records WHERE record_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, recordId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new BorrowRecord(
                        resultSet.getInt("record_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getInt("member_id"),
                        resultSet.getString("borrow_date"),
                        resultSet.getString("due_date"),
                        resultSet.getString("return_status")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error searching borrow record by ID: " + e.getMessage());
        }

        return null;
    }

    public List<BorrowRecord> searchBorrowRecordsByBookId(int bookId) {
        List<BorrowRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM borrow_records WHERE book_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BorrowRecord record = new BorrowRecord(
                        resultSet.getInt("record_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getInt("member_id"),
                        resultSet.getString("borrow_date"),
                        resultSet.getString("due_date"),
                        resultSet.getString("return_status")
                );

                records.add(record);
            }

        } catch (SQLException e) {
            System.out.println("Error searching borrow records by book ID: " + e.getMessage());
        }

        return records;
    }

    public List<BorrowRecord> searchBorrowRecordsByMemberId(int memberId) {
        List<BorrowRecord> records = new ArrayList<>();

        String sql = "SELECT * FROM borrow_records WHERE member_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, memberId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BorrowRecord record = new BorrowRecord(
                        resultSet.getInt("record_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getInt("member_id"),
                        resultSet.getString("borrow_date"),
                        resultSet.getString("due_date"),
                        resultSet.getString("return_status")
                );

                records.add(record);
            }

        } catch (SQLException e) {
            System.out.println("Error searching borrow records by member ID: " + e.getMessage());
        }

        return records;
    }

    public boolean updateBorrowRecord(BorrowRecord record) {
        String sql = """
                UPDATE borrow_records
                SET book_id = ?, member_id = ?, borrow_date = ?, due_date = ?, return_status = ?
                WHERE record_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, record.getBookId());
            preparedStatement.setInt(2, record.getMemberId());
            preparedStatement.setString(3, record.getBorrowDate());
            preparedStatement.setString(4, record.getDueDate());
            preparedStatement.setString(5, record.getReturnStatus());
            preparedStatement.setInt(6, record.getRecordId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating borrow record: " + e.getMessage());
            return false;
        }
    }

    public boolean updateBorrowStatus(int recordId, String newStatus) {
        String sql = """
                UPDATE borrow_records
                SET return_status = ?
                WHERE record_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, recordId);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Error updating borrow status: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteBorrowRecord(int recordId) {
        String sql = "DELETE FROM borrow_records WHERE record_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, recordId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting borrow record: " + e.getMessage());
            return false;
        }
    }

    public List<BorrowRecord> getOverdueRecords() {
        List<BorrowRecord> records = new ArrayList<>();
        String sql = """
                SELECT * FROM borrow_records
                WHERE due_date < DATE('now')
                AND return_status != 'Returned'
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                BorrowRecord record = new BorrowRecord(
                        resultSet.getInt("record_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getInt("member_id"),
                        resultSet.getString("borrow_date"),
                        resultSet.getString("due_date"),
                        resultSet.getString("return_status")
                );

                records.add(record);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving overdue records: " + e.getMessage());
        }
        return records;
    }

    public List<BorrowRecord> filterBorrowRecordsByDateRange(String startDate, String endDate) {
        List<BorrowRecord> records = new ArrayList<>();

        String sql = """
                SELECT * FROM borrow_records
                WHERE borrow_date BETWEEN ? AND ?
                ORDER BY borrow_date ASC
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BorrowRecord record = new BorrowRecord(
                        resultSet.getInt("record_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getInt("member_id"),
                        resultSet.getString("borrow_date"),
                        resultSet.getString("due_date"),
                        resultSet.getString("return_status")
                );

                records.add(record);
            }

        } catch (SQLException e) {
            System.out.println("Error filtering borrow records by date range: " + e.getMessage());
        }

        return records;
    }

    public List<BorrowRecord> filterBorrowRecordsByStatus(String status) {
    List<BorrowRecord> records = new ArrayList<>();
    String sql = "SELECT * FROM borrow_records WHERE return_status = ? ORDER BY due_date ASC";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        preparedStatement.setString(1, status);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            BorrowRecord record = new BorrowRecord(
                    resultSet.getInt("record_id"),
                    resultSet.getInt("book_id"),
                    resultSet.getInt("member_id"),
                    resultSet.getString("borrow_date"),
                    resultSet.getString("due_date"),
                    resultSet.getString("return_status")
            );

            records.add(record);
        }

    } catch (SQLException e) {
        System.out.println("Error filtering borrow records by status: " + e.getMessage());
    }

    return records;
}

public List<BorrowRecord> filterBorrowRecordsByDateRangeSorted(
        String startDate,
        String endDate,
        String sortOrder
) {
    List<BorrowRecord> records = new ArrayList<>();

    String sql;
    if (sortOrder != null && sortOrder.equalsIgnoreCase("DESC")) {
        sql = """
                SELECT * FROM borrow_records
                WHERE borrow_date BETWEEN ? AND ?
                ORDER BY borrow_date DESC
                """;
    } else {
        sql = """
                SELECT * FROM borrow_records
                WHERE borrow_date BETWEEN ? AND ?
                ORDER BY borrow_date ASC
                """;
    }

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, startDate);
        preparedStatement.setString(2, endDate);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            BorrowRecord record = new BorrowRecord(
                    resultSet.getInt("record_id"),
                    resultSet.getInt("book_id"),
                    resultSet.getInt("member_id"),
                    resultSet.getString("borrow_date"),
                    resultSet.getString("due_date"),
                    resultSet.getString("return_status")
            );

            records.add(record);
        }

    } catch (SQLException e) {
        System.out.println("Error filtering borrow records by date range: " + e.getMessage());
    }
    return records;
}

public int updateOverdueRecordsAutomatically() {
    String sql = """
            UPDATE borrow_records
            SET return_status = 'Overdue'
            WHERE due_date < DATE('now')
            AND return_status = 'Borrowed'
            """;

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        return preparedStatement.executeUpdate();

    } catch (SQLException e) {
        System.out.println("Error updating overdue records automatically: " + e.getMessage());
        return 0;
    }
}
}