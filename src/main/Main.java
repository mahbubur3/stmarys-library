// package main;

// import main.database.DatabaseConnection;
// import main.dao.BookDAO;
// import main.models.Book;

// import java.util.List;

// public class Main {

//     public static void main(String[] args) {
//         System.out.println("Starting St Mary's Digital Library System...");

//         DatabaseConnection.testConnection();

//         BookDAO bookDAO = new BookDAO();

//         System.out.println("\n--- Display All Books ---");
//         List<Book> books = bookDAO.getAllBooks();

//         for (Book book : books) {
//             System.out.println(book);
//             System.out.println("--------------------");
//         }

//         System.out.println("\n--- Add New Book Test ---");
//         Book newBook = new Book("Clean Code", "Robert Martin", "Programming", "Available");

//         boolean added = bookDAO.addBook(newBook);

//         if (added) {
//             System.out.println("Book added successfully.");
//         } else {
//             System.out.println("Book was not added.");
//         }

//         System.out.println("\n--- Display Books After Add ---");
//         books = bookDAO.getAllBooks();

//         for (Book book : books) {
//             System.out.println(book);
//             System.out.println("--------------------");
//         }

//         System.out.println("\n--- Search Book By ID Test ---");
//         Book foundBook = bookDAO.searchBookById(1);

//         if (foundBook != null) {
//             System.out.println(foundBook);
//         } else {
//             System.out.println("Book not found.");
//         }

//         System.out.println("\n--- Search Book By Title Or Author Test ---");
//         List<Book> searchResults = bookDAO.searchBooksByTitleOrAuthor("Java");

//         for (Book book : searchResults) {
//             System.out.println(book);
//             System.out.println("--------------------");
//         }

//         System.out.println("\n--- Update Book Test ---");
//         Book updatedBook = new Book(1, "Advanced Java Programming", "John Smith", "Programming", "Available");

//         boolean updated = bookDAO.updateBook(updatedBook);

//         if (updated) {
//             System.out.println("Book updated successfully.");
//         } else {
//             System.out.println("Book was not updated.");
//         }

//         System.out.println("\n--- Delete Book Test ---");
//         boolean deleted = bookDAO.deleteBook(4);

//         if (deleted) {
//             System.out.println("Book deleted successfully.");
//         } else {
//             System.out.println("Book was not deleted. Check if the book ID exists.");
//         }
//     }
// }



// package main;

// import main.database.DatabaseConnection;
// import main.dao.MemberDAO;
// import main.models.Member;

// import java.util.List;

// public class Main {

//     public static void main(String[] args) {
//         System.out.println("Starting St Mary's Digital Library System...");

//         DatabaseConnection.testConnection();

//         MemberDAO memberDAO = new MemberDAO();

//         System.out.println("\n--- Display All Members ---");
//         List<Member> members = memberDAO.getAllMembers();

//         for (Member member : members) {
//             System.out.println(member);
//             System.out.println("--------------------");
//         }

//         System.out.println("\n--- Add New Member Test ---");
//         Member newMember = new Member(
//                 "David Wilson",
//                 "david.wilson@stmarys.ac.uk",
//                 "Student"
//         );

//         boolean added = memberDAO.addMember(newMember);

//         if (added) {
//             System.out.println("Member added successfully.");
//         } else {
//             System.out.println("Member was not added.");
//         }

//         System.out.println("\n--- Display Members After Add ---");
//         members = memberDAO.getAllMembers();

//         for (Member member : members) {
//             System.out.println(member);
//             System.out.println("--------------------");
//         }

//         System.out.println("\n--- Search Member By ID Test ---");
//         Member foundMember = memberDAO.searchMemberById(1);

//         if (foundMember != null) {
//             System.out.println(foundMember);
//         } else {
//             System.out.println("Member not found.");
//         }

//         System.out.println("\n--- Search Member By Name Test ---");
//         List<Member> searchResults = memberDAO.searchMembersByName("Alice");

//         for (Member member : searchResults) {
//             System.out.println(member);
//             System.out.println("--------------------");
//         }

//         System.out.println("\n--- Update Member Test ---");
//         Member updatedMember = new Member(
//                 1,
//                 "Alice Johnson",
//                 "alice.johnson@stmarys.ac.uk",
//                 "Postgraduate Student"
//         );

//         boolean updated = memberDAO.updateMember(updatedMember);

//         if (updated) {
//             System.out.println("Member updated successfully.");
//         } else {
//             System.out.println("Member was not updated.");
//         }

//         System.out.println("\n--- Delete Member Test ---");
//         boolean deleted = memberDAO.deleteMember(4);

//         if (deleted) {
//             System.out.println("Member deleted successfully.");
//         } else {
//             System.out.println("Member was not deleted. Check if the member ID exists.");
//         }
//     }
// }



// package main;

// import main.database.DatabaseConnection;
// import main.dao.BorrowRecordDAO;
// import main.models.BorrowRecord;

// import java.util.List;

// public class Main {

//     public static void main(String[] args) {
//         System.out.println("Starting St Mary's Digital Library System...");

//         DatabaseConnection.testConnection();

//         BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();

//         System.out.println("\n--- Display All Borrow Records ---");
//         List<BorrowRecord> records = borrowRecordDAO.getAllBorrowRecords();

//         for (BorrowRecord record : records) {
//             System.out.println(record);
//             System.out.println("--------------------");
//         }

//         System.out.println("\n--- Add New Borrow Record Test ---");
//         BorrowRecord newRecord = new BorrowRecord(
//                 1,
//                 1,
//                 "2026-04-20",
//                 "2026-05-04",
//                 "Borrowed"
//         );

//         boolean added = borrowRecordDAO.addBorrowRecord(newRecord);

//         if (added) {
//             System.out.println("Borrow record added successfully.");
//         } else {
//             System.out.println("Borrow record was not added.");
//         }

//         System.out.println("\n--- Display Borrow Records After Add ---");
//         records = borrowRecordDAO.getAllBorrowRecords();

//         for (BorrowRecord record : records) {
//             System.out.println(record);
//             System.out.println("--------------------");
//         }

//         System.out.println("\n--- Search Borrow Record By ID Test ---");
//         BorrowRecord foundRecord = borrowRecordDAO.searchBorrowRecordById(1);

//         if (foundRecord != null) {
//             System.out.println(foundRecord);
//         } else {
//             System.out.println("Borrow record not found.");
//         }

//         System.out.println("\n--- Search Borrow Records By Book ID Test ---");
//         List<BorrowRecord> bookRecords = borrowRecordDAO.searchBorrowRecordsByBookId(1);

//         for (BorrowRecord record : bookRecords) {
//             System.out.println(record);
//             System.out.println("--------------------");
//         }

//         System.out.println("\n--- Search Borrow Records By Member ID Test ---");
//         List<BorrowRecord> memberRecords = borrowRecordDAO.searchBorrowRecordsByMemberId(1);

//         for (BorrowRecord record : memberRecords) {
//             System.out.println(record);
//             System.out.println("--------------------");
//         }

//         System.out.println("\n--- Update Borrow Status Test ---");
//         boolean statusUpdated = borrowRecordDAO.updateBorrowStatus(1, "Returned");

//         if (statusUpdated) {
//             System.out.println("Borrow status updated successfully.");
//         } else {
//             System.out.println("Borrow status was not updated.");
//         }

//         System.out.println("\n--- Update Full Borrow Record Test ---");
//         BorrowRecord updatedRecord = new BorrowRecord(
//                 1,
//                 2,
//                 1,
//                 "2025-03-01",
//                 "2025-03-20",
//                 "Borrowed"
//         );

//         boolean updated = borrowRecordDAO.updateBorrowRecord(updatedRecord);

//         if (updated) {
//             System.out.println("Borrow record updated successfully.");
//         } else {
//             System.out.println("Borrow record was not updated.");
//         }

//         System.out.println("\n--- Overdue Records Test ---");
//         List<BorrowRecord> overdueRecords = borrowRecordDAO.getOverdueRecords();

//         if (overdueRecords.isEmpty()) {
//             System.out.println("No overdue records found.");
//         } else {
//             for (BorrowRecord record : overdueRecords) {
//                 System.out.println(record);
//                 System.out.println("--------------------");
//             }
//         }

//         System.out.println("\n--- Date Range Filter Test ---");
//         List<BorrowRecord> dateRangeRecords =
//                 borrowRecordDAO.filterBorrowRecordsByDateRange("2025-03-01", "2025-03-31");

//         for (BorrowRecord record : dateRangeRecords) {
//             System.out.println(record);
//             System.out.println("--------------------");
//         }

//         System.out.println("\n--- Delete Borrow Record Test ---");
//         boolean deleted = borrowRecordDAO.deleteBorrowRecord(4);

//         if (deleted) {
//             System.out.println("Borrow record deleted successfully.");
//         } else {
//             System.out.println("Borrow record was not deleted. Check if the record ID exists.");
//         }
//     }
// }


