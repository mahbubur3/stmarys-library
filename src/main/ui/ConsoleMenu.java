// package main.ui;
// import main.dao.BookDAO;
// import main.dao.MemberDAO;
// import main.dao.BorrowRecordDAO;
// import main.models.Book;
// import main.models.Member;
// import main.models.BorrowRecord;
// import main.utils.Validator;
// import java.util.List;
// import java.util.Scanner;

// public class ConsoleMenu {
//     private final Scanner scanner;
//     private final BookDAO bookDAO;
//     private final MemberDAO memberDAO;
//     private final BorrowRecordDAO borrowRecordDAO;

//     public ConsoleMenu() {
//         scanner = new Scanner(System.in);
//         bookDAO = new BookDAO();
//         memberDAO = new MemberDAO();
//         borrowRecordDAO = new BorrowRecordDAO();
//     }

//     public void start() {
//         boolean running = true;

//         while (running) {
//             System.out.println(" ST MARY'S DIGITAL LIBRARY SYSTEM");
//             System.out.println("-------------");
//             System.out.println("1. Manage Books");
//             System.out.println("2. Manage Members");
//             System.out.println("3. Manage Borrowing Records");
//             System.out.println("4. Search Records");
//             System.out.println("5. View Overdue Records");
//             System.out.println("0. Exit System");
//             System.out.print("Choose an option: ");

//             String choice = scanner.nextLine();
//             switch (choice) {
//                 case "1" -> manageBooks();
//                 case "2" -> manageMembers();
//                 case "3" -> manageBorrowRecords();
//                 case "4" -> searchRecords();
//                 case "5" -> viewOverdueRecords();
//                 case "0" -> {
//                     System.out.println("Exiting system...");
//                     running = false;
//                 }
//                 default -> System.out.println("Invalid option. Please try again.");
//             }
//         }
//     }

//     private void manageBooks() {
//         boolean back = false;
//         while (!back) {
//             System.out.println("\n--- Manage Books ---");
//             System.out.println("1. Add Book");
//             System.out.println("2. View All Books");
//             System.out.println("3. Update Book");
//             System.out.println("4. Delete Book");
//             System.out.println("0. Back to Main Menu");
//             System.out.print("Choose an option: ");

//             String choice = scanner.nextLine();
//             switch (choice) {
//                 case "1" -> addBook();
//                 case "2" -> viewAllBooks();
//                 case "3" -> updateBook();
//                 case "4" -> deleteBook();
//                 case "0" -> back = true;
//                 default -> System.out.println("Invalid option. Please try again.");
//             }
//         }
//     }

//     private void addBook() {
//         System.out.println("\n--- Add Book ---");
//         System.out.print("Enter title: ");
//         String title = scanner.nextLine();
//         System.out.print("Enter author: ");
//         String author = scanner.nextLine();
//         System.out.print("Enter category: ");
//         String category = scanner.nextLine();
//         System.out.print("Enter availability status (Available/Borrowed): ");
//         String status = scanner.nextLine();

//         if (!Validator.isNotEmpty(title) || !Validator.isNotEmpty(author) || !Validator.isNotEmpty(category)) {
//             System.out.println("Title, author and category cannot be empty.");
//             return;
//         }

//         if (!Validator.isValidBookStatus(status)) {
//             System.out.println("Invalid status. Use Available or Borrowed.");
//             return;
//         }

//         Book book = new Book(title, author, category, status);

//         if (bookDAO.addBook(book)) {
//             System.out.println("Book added");
//         } else {
//             System.out.println("Book was not added.");
//         }
//     }

//     private void viewAllBooks() {
//         System.out.println("\n--- All Books ---");

//         List<Book> books = bookDAO.getAllBooks();

//         if (books.isEmpty()) {
//             System.out.println("No books found.");
//             return;
//         }

//         for (Book book : books) {
//             System.out.println(book);
//             System.out.println("-----=========------");
//         }
//     }

//     private void updateBook() {
//         System.out.println("\n--- Update Book ---");

//         System.out.print("Enter book ID to update: ");
//         String idInput = scanner.nextLine();

//         if (!Validator.isNumeric(idInput)) {
//             System.out.println("Book ID must be numeric.");
//             return;
//         }

//         int bookId = Integer.parseInt(idInput);
//         Book existingBook = bookDAO.searchBookById(bookId);
//         if (existingBook == null) {
//             System.out.println("Book not found.");
//             return;
//         }

//         System.out.println("Current book details:");
//         System.out.println(existingBook);
//         System.out.print("Enter new title: ");
//         String title = scanner.nextLine();
//         System.out.print("Enter new author: ");
//         String author = scanner.nextLine();
//         System.out.print("Enter new category: ");
//         String category = scanner.nextLine();
//         System.out.print("Enter new availability status (Available/Borrowed): ");
//         String status = scanner.nextLine();
//         if (!Validator.isNotEmpty(title) || !Validator.isNotEmpty(author) || !Validator.isNotEmpty(category)) {
//             System.out.println("Title, author, and category cannot be empty.");
//             return;
//         }

//         if (!Validator.isValidBookStatus(status)) {
//             System.out.println("Invalid status. Use Available or Borrowed.");
//             return;
//         }

//         Book updatedBook = new Book(bookId, title, author, category, status);
//         if (bookDAO.updateBook(updatedBook)) {
//             System.out.println("Book updated successfully.");
//         } else {
//             System.out.println("Book was not updated.");
//         }
//     }

//     private void deleteBook() {
//         System.out.println("\n--- Delete Book ---");
//         System.out.print("Enter book ID to delete: ");
//         String idInput = scanner.nextLine();

//         if (!Validator.isNumeric(idInput)) {
//             System.out.println("Book ID must be numeric.");
//             return;
//         }

//         int bookId = Integer.parseInt(idInput);
//         Book book = bookDAO.searchBookById(bookId);
//         if (book == null) {
//             System.out.println("Book not found.");
//             return;
//         }

//         System.out.println("Book found:");
//         System.out.println(book);
//         System.out.print("Are you sure you want to delete this book? (yes/no): ");
//         String confirm = scanner.nextLine();

//         if (confirm.equalsIgnoreCase("yes")) {
//             if (bookDAO.deleteBook(bookId)) {
//                 System.out.println("Book deleted");
//             } else {
//                 System.out.println("Book was not deleted.");
//             }
//         } else {
//             System.out.println("Delete cancelled.");
//         }
//     }

//     private void manageMembers() {
//         boolean back = false;

//         while (!back) {
//             System.out.println("\n--- Manage Members ---");
//             System.out.println("1. Add Member");
//             System.out.println("2. View All Members");
//             System.out.println("3. Update Member");
//             System.out.println("4. Delete Member");
//             System.out.println("0. Back to Main Menu");
//             System.out.print("Choose an option: ");
//             String choice = scanner.nextLine();

//             switch (choice) {
//                 case "1" -> addMember();
//                 case "2" -> viewAllMembers();
//                 case "3" -> updateMember();
//                 case "4" -> deleteMember();
//                 case "0" -> back = true;
//                 default -> System.out.println("Invalid option. Please try again.");
//             }
//         }
//     }

//     private void addMember() {
//         System.out.println("\n--- Add Member ---");
//         System.out.print("Enter member name: ");
//         String name = scanner.nextLine();
//         System.out.print("Enter email: ");
//         String email = scanner.nextLine();
//         System.out.print("Enter membership type (Student/Staff): ");
//         String membershipType = scanner.nextLine();

//         if (!Validator.isNotEmpty(name)) {
//             System.out.println("Member name cannot be empty.");
//             return;
//         }

//         if (!Validator.isValidEmail(email)) {
//             System.out.println("Invalid email format.");
//             return;
//         }

//         if (!Validator.isValidMembershipType(membershipType)) {
//             System.out.println("Invalid membership type. Use Student or Staff.");
//             return;
//         }

//         Member member = new Member(name, email, membershipType);
//         if (memberDAO.addMember(member)) {
//             System.out.println("Member added");
//         } else {
//             System.out.println("Member was not added.");
//         }
//     }

//     private void viewAllMembers() {
//         System.out.println("\n--- All Members ---");
//         List<Member> members = memberDAO.getAllMembers();

//         if (members.isEmpty()) {
//             System.out.println("No members found.");
//             return;
//         }
//         for (Member member : members) {
//             System.out.println(member);
//             System.out.println("--------------------");
//         }
//     }

//     private void updateMember() {
//         System.out.println("\n--- Update Member ---");
//         System.out.print("Enter member ID to update: ");
//         String idInput = scanner.nextLine();

//         if (!Validator.isNumeric(idInput)) {
//             System.out.println("Member ID must be numeric.");
//             return;
//         }

//         int memberId = Integer.parseInt(idInput);
//         Member existingMember = memberDAO.searchMemberById(memberId);
//         if (existingMember == null) {
//             System.out.println("Member not found.");
//             return;
//         }

//         System.out.println("Current member details:");
//         System.out.println(existingMember);
//         System.out.print("Enter new member name: ");
//         String name = scanner.nextLine();
//         System.out.print("Enter new email: ");
//         String email = scanner.nextLine();
//         System.out.print("Enter new membership type (Student/Staff): ");
//         String membershipType = scanner.nextLine();

//         if (!Validator.isNotEmpty(name)) {
//             System.out.println("Member name cannot be empty.");
//             return;
//         }
//         if (!Validator.isValidEmail(email)) {
//             System.out.println("Invalid email format.");
//             return;
//         }
//         if (!Validator.isValidMembershipType(membershipType)) {
//             System.out.println("Invalid membership type. Use Student or Staff.");
//             return;
//         }

//         Member updatedMember = new Member(memberId, name, email, membershipType);
//         if (memberDAO.updateMember(updatedMember)) {
//             System.out.println("Member updated successfully.");
//         } else {
//             System.out.println("Member was not updated.");
//         }
//     }

//     private void deleteMember() {
//         System.out.println("\n--- Delete Member ---");
//         System.out.print("Enter member ID to delete: ");
//         String idInput = scanner.nextLine();
//         if (!Validator.isNumeric(idInput)) {
//             System.out.println("Member ID must be numeric.");
//             return;
//         }

//         int memberId = Integer.parseInt(idInput);
//         Member member = memberDAO.searchMemberById(memberId);
//         if (member == null) {
//             System.out.println("Member not found.");
//             return;
//         }

//         System.out.println("Member found:");
//         System.out.println(member);
//         System.out.print("Are you sure you want to delete this member? (yes/no): ");
//         String confirm = scanner.nextLine();

//         if (confirm.equalsIgnoreCase("yes")) {
//             if (memberDAO.deleteMember(memberId)) {
//                 System.out.println("Member deleted successfully.");
//             } else {
//                 System.out.println("Member was not deleted.");
//             }
//         } else {
//             System.out.println("Delete cancelled.");
//         }
//     }

//     private void manageBorrowRecords() {
//         boolean back = false;
//         while (!back) {
//             System.out.println("\n--- Manage Borrowing Records ---");
//             System.out.println("1. Add Borrowing Record");
//             System.out.println("2. View All Borrowing Records");
//             System.out.println("3. Update Borrowing Record");
//             System.out.println("4. Update Borrowing Status");
//             System.out.println("5. Delete Borrowing Record");
//             System.out.println("0. Back to Main Menu");
//             System.out.print("Choose an option: ");

//             String choice = scanner.nextLine();
//             switch (choice) {
//                 case "1" -> addBorrowRecord();
//                 case "2" -> viewAllBorrowRecords();
//                 case "3" -> updateBorrowRecord();
//                 case "4" -> updateBorrowStatus();
//                 case "5" -> deleteBorrowRecord();
//                 case "0" -> back = true;
//                 default -> System.out.println("Invalid option. Please try again.");
//             }
//         }
//     }

//     private void addBorrowRecord() {
//         System.out.println("\n--- Add Borrowing Record ---");
//         System.out.print("Enter book ID: ");
//         String bookIdInput = scanner.nextLine();
//         System.out.print("Enter member ID: ");
//         String memberIdInput = scanner.nextLine();
//         System.out.print("Enter borrow date (YYYY-MM-DD): ");
//         String borrowDate = scanner.nextLine();
//         System.out.print("Enter due date (YYYY-MM-DD): ");
//         String dueDate = scanner.nextLine();
//         System.out.print("Enter return status (Borrowed/Returned/Overdue): ");
//         String status = scanner.nextLine();

//         if (!Validator.isNumeric(bookIdInput) || !Validator.isNumeric(memberIdInput)) {
//             System.out.println("Book ID and Member ID must be numeric.");
//             return;
//         }

//         if (!Validator.isValidDate(borrowDate) || !Validator.isValidDate(dueDate)) {
//             System.out.println("Invalid date format. Use YYYY-MM-DD.");
//             return;
//         }

//         if (!Validator.isDueDateAfterBorrowDate(borrowDate, dueDate)) {
//             System.out.println("Due date must be after borrow date.");
//             return;
//         }

//         if (!Validator.isValidBorrowStatus(status)) {
//             System.out.println("Invalid status. Use Borrowed, Returned, or Overdue.");
//             return;
//         }

//         int bookId = Integer.parseInt(bookIdInput);
//         int memberId = Integer.parseInt(memberIdInput);

//         BorrowRecord record = new BorrowRecord(bookId, memberId, borrowDate, dueDate, status);

//         if (borrowRecordDAO.addBorrowRecord(record)) {
//             System.out.println("Borrow record added successfully.");
//         } else {
//             System.out.println("Borrow record was not added.");
//         }
//     }

//     private void viewAllBorrowRecords() {
//         System.out.println("\n--- All Borrowing Records ---");

//         List<BorrowRecord> records = borrowRecordDAO.getAllBorrowRecords();

//         if (records.isEmpty()) {
//             System.out.println("No borrowing records found.");
//             return;
//         }

//         for (BorrowRecord record : records) {
//             System.out.println(record);
//             System.out.println("---------=============--------");
//         }
//     }

//     private void updateBorrowRecord() {
//         System.out.println("\n--- Update Borrowing Record ---");
//         System.out.print("Enter record ID to update: ");
//         String recordIdInput = scanner.nextLine();
//         if (!Validator.isNumeric(recordIdInput)) {
//             System.out.println("Record ID must be numeric.");
//             return;
//         }

//         int recordId = Integer.parseInt(recordIdInput);
//         BorrowRecord existingRecord = borrowRecordDAO.searchBorrowRecordById(recordId);

//         if (existingRecord == null) {
//             System.out.println("Borrowing record not found.");
//             return;
//         }

//         System.out.println("Current borrowing record:");
//         System.out.println(existingRecord);
//         System.out.print("Enter new book ID: ");
//         String bookIdInput = scanner.nextLine();
//         System.out.print("Enter new member ID: ");
//         String memberIdInput = scanner.nextLine();
//         System.out.print("Enter new borrow date (YYYY-MM-DD): ");
//         String borrowDate = scanner.nextLine();
//         System.out.print("Enter new due date (YYYY-MM-DD): ");
//         String dueDate = scanner.nextLine();
//         System.out.print("Enter new status (Borrowed/Returned/Overdue): ");
//         String status = scanner.nextLine();

//         if (!Validator.isNumeric(bookIdInput) || !Validator.isNumeric(memberIdInput)) {
//             System.out.println("Book ID and Member ID must be numeric.");
//             return;
//         }

//         if (!Validator.isValidDate(borrowDate) || !Validator.isValidDate(dueDate)) {
//             System.out.println("Invalid date format. Use YYYY-MM-DD.");
//             return;
//         }

//         if (!Validator.isDueDateAfterBorrowDate(borrowDate, dueDate)) {
//             System.out.println("Due date must be after borrow date.");
//             return;
//         }

//         if (!Validator.isValidBorrowStatus(status)) {
//             System.out.println("Invalid status. Use Borrowed, Returned, or Overdue.");
//             return;
//         }

//         BorrowRecord updatedRecord = new BorrowRecord(
//             recordId,
//             Integer.parseInt(bookIdInput),
//             Integer.parseInt(memberIdInput),
//             borrowDate,
//             dueDate,
//             status
//         );

//         if (borrowRecordDAO.updateBorrowRecord(updatedRecord)) {
//             System.out.println("Borrow record updated successfully.");
//         } else {
//             System.out.println("Borrow record was not updated.");
//         }
//     }

//     private void updateBorrowStatus() {
//         System.out.println("\n--- Update Borrowing Status ---");
//         System.out.print("Enter record ID: ");
//         String recordIdInput = scanner.nextLine();

//         if (!Validator.isNumeric(recordIdInput)) {
//             System.out.println("Record ID must be numeric.");
//             return;
//         }
//         System.out.print("Enter new status (Borrowed/Returned/Overdue): ");
//         String status = scanner.nextLine();

//         if (!Validator.isValidBorrowStatus(status)) {
//             System.out.println("Invalid status. Use Borrowed, Returned, or Overdue.");
//             return;
//         }

//         int recordId = Integer.parseInt(recordIdInput);
//         if (borrowRecordDAO.updateBorrowStatus(recordId, status)) {
//             System.out.println("Borrow status updated successfully.");
//         } else {
//             System.out.println("Borrow status was not updated.");
//         }
//     }

//     private void deleteBorrowRecord() {
//         System.out.println("\n--- Delete Borrowing Record ---");
//         System.out.print("Enter record ID to delete: ");
//         String recordIdInput = scanner.nextLine();
//         if (!Validator.isNumeric(recordIdInput)) {
//             System.out.println("Record ID must be numeric.");
//             return;
//         }

//         int recordId = Integer.parseInt(recordIdInput);
//         BorrowRecord record = borrowRecordDAO.searchBorrowRecordById(recordId);

//         if (record == null) {
//             System.out.println("Borrowing record not found.");
//             return;
//         }
//         System.out.println("Borrowing record found:");
//         System.out.println(record);
//         System.out.print("Are you sure you want to delete this record? (yes/no): ");
//         String confirm = scanner.nextLine();

//         if (confirm.equalsIgnoreCase("yes")) {
//             if (borrowRecordDAO.deleteBorrowRecord(recordId)) {
//                 System.out.println("Borrowing record deleted successfully.");
//             } else {
//                 System.out.println("Borrowing record was not deleted.");
//             }
//         } else {
//             System.out.println("Delete cancelled.");
//         }
//     }

//     private void searchRecords() {
//         boolean back = false;
//         while (!back) {
//             System.out.println("\n--- Search Records ---");
//             System.out.println("1. Search Book by ID");
//             System.out.println("2. Search Book by Title or Author");
//             System.out.println("3. Search Member by ID");
//             System.out.println("4. Search Member by Name");
//             System.out.println("5. Search Borrowing Records by Book ID");
//             System.out.println("6. Search Borrowing Records by Member ID");
//             System.out.println("7. Filter Borrowing Records by Date Range");
//             System.out.println("8. Advanced Book Search");
//             System.out.println("9. Filter Books by Category");
//             System.out.println("10. Sort Books by Title");
//             System.out.println("11. Filter Borrowing Records by Status");
//             System.out.println("12. Advanced Date Range Filter with Sorting");
//             System.out.println("0. Back to Main Menu");
//             System.out.print("Choose an option: ");
//             String choice = scanner.nextLine();
//             switch (choice) {
//                 case "1" -> searchBookById();
//                 case "2" -> searchBookByTitleOrAuthor();
//                 case "3" -> searchMemberById();
//                 case "4" -> searchMemberByName();
//                 case "5" -> searchBorrowRecordsByBookId();
//                 case "6" -> searchBorrowRecordsByMemberId();
//                 case "7" -> filterBorrowRecordsByDateRange();
//                 case "8" -> advancedBookSearch();
//                 case "9" -> filterBooksByCategory();
//                 case "10" -> sortBooksByTitle();
//                 case "11" -> filterBorrowRecordsByStatus();
//                 case "12" -> advancedDateRangeFilterWithSorting();
//                 case "0" -> back = true;
//                 default -> System.out.println("Invalid option. Please try again.");
//             }
//         }
//     }

//     private void searchBookById() {
//         System.out.print("Enter book ID: ");
//         String idInput = scanner.nextLine();
//         if (!Validator.isNumeric(idInput)) {
//             System.out.println("Book ID must be numeric.");
//             return;
//         }

//         Book book = bookDAO.searchBookById(Integer.parseInt(idInput));
//         if (book == null) {
//             System.out.println("Book not found.");
//         } 
        
        
//         else {
//             System.out.println(book);
//         }
//     }

//     private void searchBookByTitleOrAuthor() {
//         System.out.print("Enter title or author keyword: ");
//         String keyword = scanner.nextLine();

//         List<Book> books = bookDAO.searchBooksByTitleOrAuthor(keyword);

//         if (books.isEmpty()) {
//             System.out.println("No books found.");
//             return;
//         }

//         for (Book book : books) {
//             System.out.println(book);
//             System.out.println("--------------------");
//         }
//     }
//     private void searchMemberById() {
//         System.out.print("Enter member ID: ");
//         String idInput = scanner.nextLine();
//         if (!Validator.isNumeric(idInput)) {
//             System.out.println("Member ID must be numeric.");
//             return;
//         }

//         Member member = memberDAO.searchMemberById(Integer.parseInt(idInput));
//         if (member == null) {
//             System.out.println("Member not found.");
//         } else {
//             System.out.println(member);
//         }
//     }

//     private void searchMemberByName() {
//         System.out.print("Enter member name keyword: ");
//         String keyword = scanner.nextLine();
//         List<Member> members = memberDAO.searchMembersByName(keyword);
//         if (members.isEmpty()) {
//             System.out.println("No members found.");
//             return;
//         }
//         for (Member member : members) {
//             System.out.println(member);
//             System.out.println("--------------------");
//         }
//     }

//     private void searchBorrowRecordsByBookId() {
//         System.out.print("Enter book ID: ");
//         String idInput = scanner.nextLine();
//         if (!Validator.isNumeric(idInput)) {
//             System.out.println("Book ID must be numeric.");
//             return;
//         }

//         List<BorrowRecord> records = borrowRecordDAO.searchBorrowRecordsByBookId(Integer.parseInt(idInput));

//         if (records.isEmpty()) {
//             System.out.println("No borrowing records found.");
//             return;
//         }

//         for (BorrowRecord record : records) {
//             System.out.println(record);
//             System.out.println("--------------------");
//         }
//     }

//     private void searchBorrowRecordsByMemberId() {
//         System.out.print("Enter member ID: ");
//         String idInput = scanner.nextLine();
//         if (!Validator.isNumeric(idInput)) {
//             System.out.println("Member ID must be numeric.");
//             return;
//         }

//         List<BorrowRecord> records = borrowRecordDAO.searchBorrowRecordsByMemberId(Integer.parseInt(idInput));
//         if (records.isEmpty()) {
//             System.out.println("No borrowing records found.");
//             return;
//         }

//         for (BorrowRecord record : records) {
//             System.out.println(record);
//             System.out.println("--------------------");
//         }
//     }

//     private void filterBorrowRecordsByDateRange() {
//         System.out.print("Enter start date (YYYY-MM-DD): ");
//         String startDate = scanner.nextLine();
//         System.out.print("Enter end date (YYYY-MM-DD): ");
//         String endDate = scanner.nextLine();
//         if (!Validator.isValidDate(startDate) || !Validator.isValidDate(endDate)) {
//             System.out.println("Invalid date format. Use YYYY-MM-DD.");
//             return;
//         }

//         List<BorrowRecord> records = borrowRecordDAO.filterBorrowRecordsByDateRange(startDate, endDate);
//         if (records.isEmpty()) {
//             System.out.println("No borrowing records found in this date range.");
//             return;
//         }

//         for (BorrowRecord record : records) {
//             System.out.println(record);
//             System.out.println("--------------------");
//         }
//     }

//     private void viewOverdueRecords() {
//         System.out.println("\n--- Overdue Records ---");
//         int updatedCount = borrowRecordDAO.updateOverdueRecordsAutomatically();
//         if (updatedCount > 0) {
//             System.out.println(updatedCount + " borrowing record(s) automatically marked as Overdue.");
//         }
//         List<BorrowRecord> overdueRecords = borrowRecordDAO.getOverdueRecords();
//         if (overdueRecords.isEmpty()) {
//             System.out.println("No overdue records found.");
//             return;
//         }

//         for (BorrowRecord record : overdueRecords) {
//             System.out.println(record);
//             System.out.println("--------------------");
//         }
//     }


//     private void advancedBookSearch() {
//         System.out.println("\n--- Advanced Book Search ---");
//         System.out.print("Enter title keyword, or leave blank: ");
//         String title = scanner.nextLine();
//         System.out.print("Enter author keyword, or leave blank: ");
//         String author = scanner.nextLine();
//         System.out.print("Enter category keyword, or leave blank: ");
//         String category = scanner.nextLine();
//         System.out.print("Sort by title ASC or DESC: ");
//         String sortOrder = scanner.nextLine();

//         if (!sortOrder.equalsIgnoreCase("ASC") && !sortOrder.equalsIgnoreCase("DESC")) {
//             System.out.println("Invalid sort order. Defaulting to ASC.");
//             sortOrder = "ASC";
//         }

//         List<Book> books = bookDAO.advancedSearchBooks(title, author, category, sortOrder);
//         if (books.isEmpty()) {
//             System.out.println("No books found.");
//             return;
//         }

//         for (Book book : books) {
//             System.out.println(book);
//             System.out.println("--------------------");
//         }
//     }

//     private void filterBooksByCategory() {
//         System.out.println("\n--- Filter Books by Category ---");
//         System.out.print("Enter category: ");
//         String category = scanner.nextLine();
//         if (!Validator.isNotEmpty(category)) {
//             System.out.println("Category cannot be empty.");
//             return;
//         }

//         System.out.print("Sort by title ASC or DESC: ");
//         String sortOrder = scanner.nextLine();
//         if (!sortOrder.equalsIgnoreCase("ASC") && !sortOrder.equalsIgnoreCase("DESC")) {
//             System.out.println("Invalid sort order. Defaulting to ASC.");
//             sortOrder = "ASC";
//         }

//         List<Book> books = bookDAO.filterBooksByCategory(category, sortOrder);
//         if (books.isEmpty()) {
//             System.out.println("No books found in this category.");
//             return;
//         }

//         for (Book book : books) {
//             System.out.println(book);
//             System.out.println("--------------------");
//         }
//     }

//     private void sortBooksByTitle() {
//         System.out.println("\n--- Sort Books by Title ---");
//         System.out.print("Sort order ASC or DESC: ");
//         String sortOrder = scanner.nextLine();
//         if (!sortOrder.equalsIgnoreCase("ASC") && !sortOrder.equalsIgnoreCase("DESC")) {
//             System.out.println("Invalid sort order. Defaulting to ASC.");
//             sortOrder = "ASC";
//         }

//         List<Book> books = bookDAO.getBooksSortedByTitle(sortOrder);
//         if (books.isEmpty()) {
//             System.out.println("No books found.");
//             return;
//         }

//         for (Book book : books) {
//             System.out.println(book);
//             System.out.println("--------------------");
//         }
//     }

//     private void filterBorrowRecordsByStatus() {
//         System.out.println("\n--- Filter Borrowing Records by Status ---");
//         System.out.print("Enter status (Borrowed/Returned/Overdue): ");
//         String status = scanner.nextLine();

//         if (!Validator.isValidBorrowStatus(status)) {
//             System.out.println("Invalid status. Use Borrowed, Returned, or Overdue.");
//             return;
//         }

//         List<BorrowRecord> records = borrowRecordDAO.filterBorrowRecordsByStatus(status);
//         if (records.isEmpty()) {
//             System.out.println("No borrowing records found with this status.");
//             return;
//         }
//         for (BorrowRecord record : records) {
//             System.out.println(record);
//             System.out.println("--------------------");
//         }
//     }

//     private void advancedDateRangeFilterWithSorting() {
//         System.out.println("\n--- Advanced Date Range Filter with Sorting ---");
//         System.out.print("Enter start date (YYYY-MM-DD): ");
//         String startDate = scanner.nextLine();
//         System.out.print("Enter end date (YYYY-MM-DD): ");
//         String endDate = scanner.nextLine();

//         if (!Validator.isValidDate(startDate) || !Validator.isValidDate(endDate)) {
//             System.out.println("Invalid date format. Use YYYY-MM-DD.");
//             return;
//         }

//         System.out.print("Sort order ASC or DESC: ");
//         String sortOrder = scanner.nextLine();
//         if (!sortOrder.equalsIgnoreCase("ASC") && !sortOrder.equalsIgnoreCase("DESC")) {
//             System.out.println("Invalid sort order. Defaulting to ASC.");
//             sortOrder = "ASC";
//         }
//         List<BorrowRecord> records = borrowRecordDAO.filterBorrowRecordsByDateRangeSorted(startDate, endDate, sortOrder);
//         if (records.isEmpty()) {
//             System.out.println("No borrowing records found in this date range.");
//             return;
//         }
//         for (BorrowRecord record : records) {
//             System.out.println(record);
//             System.out.println("-----------========-------");
//         }
//     }
// }