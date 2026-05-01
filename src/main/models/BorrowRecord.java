package main.models;

public class BorrowRecord {
    private int recordId;
    private int bookId;
    private int memberId;
    private String borrowDate;
    private String dueDate;
    private String returnStatus;

    public BorrowRecord() {
    }

    public BorrowRecord(int recordId, int bookId, int memberId, String borrowDate, String dueDate, String returnStatus) {
        this.recordId = recordId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnStatus = returnStatus;
    }

    public BorrowRecord(int bookId, int memberId, String borrowDate, String dueDate, String returnStatus) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnStatus = returnStatus;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    @Override
    public String toString() {
        return "Record ID: " + recordId +
                "\nBook ID: " + bookId +
                "\nMember ID: " + memberId +
                "\nBorrow Date: " + borrowDate +
                "\nDue Date: " + dueDate +
                "\nStatus: " + returnStatus;
    }
}