package main.utils;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isNumeric(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        if (!isNotEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidDate(String date) {
        if (!isNotEmpty(date)) {
            return false;
        }

        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isDueDateAfterBorrowDate(String borrowDate, String dueDate) {
        if (!isValidDate(borrowDate) || !isValidDate(dueDate)) {
            return false;
        }

        LocalDate borrow = LocalDate.parse(borrowDate);
        LocalDate due = LocalDate.parse(dueDate);
        return due.isAfter(borrow);
    }

    public static boolean isValidBookStatus(String status) {
        return status.equalsIgnoreCase("Available") || status.equalsIgnoreCase("Borrowed");
    }

    public static boolean isValidBorrowStatus(String status) {
        return status.equalsIgnoreCase("Borrowed") || status.equalsIgnoreCase("Returned") || status.equalsIgnoreCase("Overdue");
    }
    public static boolean isValidMembershipType(String membershipType) {
        return membershipType.equalsIgnoreCase("Student") || membershipType.equalsIgnoreCase("Staff");
    }
}