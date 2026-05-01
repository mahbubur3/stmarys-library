package main.models;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private String availabilityStatus;

    public Book() {
    }

    public Book(int bookId, String title, String author, String category, String availabilityStatus) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.availabilityStatus = availabilityStatus;
    }

    public Book(String title, String author, String category, String availabilityStatus) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.availabilityStatus = availabilityStatus;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    @Override
    public String toString() {
        return "Book ID: " + bookId +
                "\nTitle: " + title +
                "\nAuthor: " + author +
                "\nCategory: " + category +
                "\nStatus: " + availabilityStatus;
    }
}