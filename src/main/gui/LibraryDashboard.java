package main.gui;
import main.dao.BookDAO;
import main.dao.MemberDAO;
import main.dao.BorrowRecordDAO;
import main.models.Book;
import main.models.Member;
import main.models.BorrowRecord;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class LibraryDashboard extends JFrame {
    private static final Color APP_BACKGROUND = new Color(248, 248, 248);
    private static final Color PANEL_BACKGROUND = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(33, 33, 33);
    private static final Color BORDER_COLOR = new Color(218, 218, 218);
    private static final Font APP_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font SECTION_FONT = new Font("Segoe UI", Font.BOLD, 15);
    private final BookDAO bookDAO;
    private final MemberDAO memberDAO;
    private final BorrowRecordDAO borrowRecordDAO;
    private JTable booksTable;
    private JTable membersTable;
    private JTable borrowRecordsTable;
    private DefaultTableModel booksTableModel;
    private DefaultTableModel membersTableModel;
    private DefaultTableModel borrowRecordsTableModel;
    private JLabel statusLabel;

    public LibraryDashboard() {
        configureAppLook();
        bookDAO = new BookDAO();
        memberDAO = new MemberDAO();
        borrowRecordDAO = new BorrowRecordDAO();

        setTitle("St Mary's Library System");
        setSize(1280, 720);
        setMinimumSize(new Dimension(920, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(APP_BACKGROUND);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(APP_FONT);
        tabbedPane.setBorder(new EmptyBorder(0, 16, 0, 16));
        tabbedPane.setBackground(APP_BACKGROUND);
        tabbedPane.addTab("Books", createBooksPanel());
        tabbedPane.addTab("Members", createMembersPanel());
        tabbedPane.addTab("Borrowing Records", createBorrowRecordsPanel());
        statusLabel = new JLabel("Ready");
        statusLabel.setFont(APP_FONT);
        statusLabel.setForeground(TEXT_COLOR);
        statusLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(9, 18, 9, 18)
        ));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(PANEL_BACKGROUND);
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        loadBooksData();
        loadMembersData();
        loadBorrowRecordsData();

    }

    private void configureAppLook() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Using default look and feel: " + e.getMessage());
        }

        UIManager.put("Label.font", APP_FONT);
        UIManager.put("Button.font", APP_FONT);
        UIManager.put("TextField.font", APP_FONT);
        UIManager.put("ComboBox.font", APP_FONT);
        UIManager.put("Table.font", APP_FONT);
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 13));
        UIManager.put("OptionPane.messageFont", APP_FONT);
        UIManager.put("OptionPane.buttonFont", APP_FONT);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PANEL_BACKGROUND);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
            new EmptyBorder(18, 22, 16, 22)
        ));

        JLabel titleLabel = new JLabel("Hi! Welcome to St Mary's Library");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        JLabel subtitleLabel = new JLabel("Manage books, members and borrowing records");
        subtitleLabel.setFont(APP_FONT);
        subtitleLabel.setForeground(TEXT_COLOR);
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(subtitleLabel);
        headerPanel.add(textPanel, BorderLayout.WEST);
        return headerPanel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setBackground(APP_BACKGROUND);
        panel.setBorder(new EmptyBorder(16, 0, 18, 0));
        return panel;
    }

    private JPanel createControlPanel(String title, String description) {
        JPanel wrapper = new JPanel(new BorderLayout(0, 12));
        wrapper.setBackground(PANEL_BACKGROUND);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            new EmptyBorder(14, 16, 14, 16)
        ));

        JPanel headingPanel = new JPanel();
        headingPanel.setOpaque(false);
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(SECTION_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        headingPanel.add(titleLabel);

        if (description != null && !description.isEmpty()) {
            JLabel descriptionLabel = new JLabel(description);
            descriptionLabel.setFont(APP_FONT);
            descriptionLabel.setForeground(TEXT_COLOR);
            headingPanel.add(Box.createVerticalStrut(3));
            headingPanel.add(descriptionLabel);
        }

        wrapper.add(headingPanel, BorderLayout.NORTH);
        return wrapper;
    }

    private JPanel createFieldGrid() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        return panel;
    }

    private void addField(JPanel panel, int column, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(APP_FONT);
        label.setForeground(TEXT_COLOR);
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = column;
        labelConstraints.gridy = 0;
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.insets = new Insets(0, column == 0 ? 0 : 12, 5, 0);
        panel.add(label, labelConstraints);

        if (!(field instanceof JPanel)) {
            styleInput(field);
        }
        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = column;
        fieldConstraints.gridy = 1;
        fieldConstraints.weightx = 1;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.insets = new Insets(0, column == 0 ? 0 : 12, 0, 0);
        panel.add(field, fieldConstraints);
    }

    private JPanel createInlineActions(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panel.setOpaque(false);
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(APP_BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        return buttonPanel;
    }

    private DefaultTableModel createTableModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private JTable createTable(DefaultTableModel tableModel) {
        JTable table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(BORDER_COLOR);
        table.setSelectionForeground(TEXT_COLOR);
        table.setGridColor(BORDER_COLOR);
        table.setFont(APP_FONT);
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(APP_BACKGROUND);
        header.setForeground(TEXT_COLOR);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        return table;
    }

    private void setColumnWidths(JTable table, int... widths) {
        for (int column = 0; column < widths.length && column < table.getColumnModel().getColumnCount(); column++) {
            table.getColumnModel().getColumn(column).setPreferredWidth(widths[column]);
        }
    }

    private JScrollPane createTableScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scrollPane.getViewport().setBackground(PANEL_BACKGROUND);
        return scrollPane;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        styleButton(button, PANEL_BACKGROUND, TEXT_COLOR, BORDER_COLOR);
        return button;
    }

    private JButton createPrimaryButton(String text) {
        return createButton(text);
    }

    private JButton createDangerButton(String text) {
        return createButton(text);
    }

    private void styleButton(JButton button, Color background, Color foreground, Color border) {
        button.setFont(APP_FONT);
        button.setFocusPainted(false);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(border),
            new EmptyBorder(7, 13, 7, 13)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    private void styleInput(JComponent component) {
        component.setFont(APP_FONT);
        component.setBackground(Color.WHITE);
        component.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            new EmptyBorder(6, 8, 6, 8)
        ));

        if (component instanceof JTextField) {
        ((JTextField) component).setColumns(12);
        }
    }

    private JPanel createDialogFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(10, 8, 6, 8));
        formPanel.setBackground(PANEL_BACKGROUND);
        return formPanel;
    }

    private void addDialogRow(JPanel panel, int row, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(APP_FONT);
        label.setForeground(TEXT_COLOR);
        styleInput(field);
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.insets = new Insets(0, 0, 10, 14);
        panel.add(label, labelConstraints);
        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = row;
        fieldConstraints.weightx = 1;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.insets = new Insets(0, 0, 10, 0);
        panel.add(field, fieldConstraints);
    }

    private JPanel createBooksPanel() {
        JPanel panel = createContentPanel();
        booksTableModel = createTableModel(new String[]{"Book ID", "Title", "Author", "Category", "Status"});
        booksTable = createTable(booksTableModel);
        setColumnWidths(booksTable, 80, 300, 220, 170, 120);
        JScrollPane scrollPane = createTableScrollPane(booksTable);
        JPanel searchPanel = createControlPanel("Find Books", "Search by title, author or category.");
        JPanel fieldGrid = createFieldGrid();
        JTextField titleSearchField = new JTextField();
        JTextField authorSearchField = new JTextField();
        JTextField categorySearchField = new JTextField();

        String[] sortOptions = {"ASC", "DESC"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
        JButton searchButton = createPrimaryButton("Search");
        JButton clearButton = createButton("Clear");
        addField(fieldGrid, 0, "Title", titleSearchField);
        addField(fieldGrid, 1, "Author", authorSearchField);
        addField(fieldGrid, 2, "Category", categorySearchField);
        addField(fieldGrid, 3, "Title sort", sortComboBox);
        addField(fieldGrid, 4, "Actions", createInlineActions(searchButton, clearButton));
        searchPanel.add(fieldGrid, BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String title = titleSearchField.getText().trim();
            String author = authorSearchField.getText().trim();
            String category = categorySearchField.getText().trim();
            String sortOrder = sortComboBox.getSelectedItem().toString();
            loadAdvancedBookSearchData(title, author, category, sortOrder);
        });

        clearButton.addActionListener(e -> {
            titleSearchField.setText("");
            authorSearchField.setText("");
            categorySearchField.setText("");
            sortComboBox.setSelectedItem("ASC");
            loadBooksData();
        });

        JPanel buttonPanel = createButtonPanel();
        JButton addButton = createPrimaryButton("Add Book");
        JButton updateButton = createButton("Update");
        JButton deleteButton = createDangerButton("Delete");
        JButton refreshButton = createButton("Refresh");
        addButton.addActionListener(e -> showAddBookDialog());
        updateButton.addActionListener(e -> showUpdateBookDialog());
        deleteButton.addActionListener(e -> deleteSelectedBook());
        refreshButton.addActionListener(e -> loadBooksData());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;   
    }

    
    private JPanel createMembersPanel() {
        JPanel panel = createContentPanel();
        membersTableModel = createTableModel(new String[]{"Member ID", "Name", "Email", "Membership Type"});
        membersTable = createTable(membersTableModel);
        setColumnWidths(membersTable, 90, 220, 300, 160);
        JScrollPane scrollPane = createTableScrollPane(membersTable);
        JPanel buttonPanel = createButtonPanel();
        JButton addButton = createPrimaryButton("Add Member");
        JButton updateButton = createButton("Update");
        JButton deleteButton = createDangerButton("Delete");
        JButton refreshButton = createButton("Refresh");

        addButton.addActionListener(e -> showAddMemberDialog());
        updateButton.addActionListener(e -> showUpdateMemberDialog());
        deleteButton.addActionListener(e -> deleteSelectedMember());
        refreshButton.addActionListener(e -> loadMembersData());
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBorrowRecordsPanel() {
        JPanel panel = createContentPanel();
        borrowRecordsTableModel = createTableModel(new String[]{"Record ID", "Book ID", "Member ID", "Borrow Date", "Due Date", "Status"});
        borrowRecordsTable = createTable(borrowRecordsTableModel);
        setColumnWidths(borrowRecordsTable, 90, 80, 90, 150, 150, 120);
        JScrollPane scrollPane = createTableScrollPane(borrowRecordsTable);
        JPanel filterPanel = createControlPanel("Filter Borrowing", "Use dates in YYYY-MM-DD format when filtering by range.");
        JPanel fieldGrid = createFieldGrid();
        String[] statusOptions = {"All", "Borrowed", "Returned", "Overdue"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();
        String[] sortOptions = {"ASC", "DESC"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
        JButton filterButton = createPrimaryButton("Filter");
        JButton clearButton = createButton("Clear");
        startDateField.setToolTipText("Use YYYY-MM-DD format");
        endDateField.setToolTipText("Use YYYY-MM-DD format");
        addField(fieldGrid, 0, "Status", statusComboBox);
        addField(fieldGrid, 1, "Start date", startDateField);
        addField(fieldGrid, 2, "End date", endDateField);
        addField(fieldGrid, 3, "Date sort", sortComboBox);
        addField(fieldGrid, 4, "Actions", createInlineActions(filterButton, clearButton));
        filterPanel.add(fieldGrid, BorderLayout.CENTER);

        filterButton.addActionListener(e -> {
            String status = statusComboBox.getSelectedItem().toString();
            String startDate = startDateField.getText().trim();
            String endDate = endDateField.getText().trim();
            String sortOrder = sortComboBox.getSelectedItem().toString();
            loadFilteredBorrowRecordsData(status, startDate, endDate, sortOrder);
        });

        clearButton.addActionListener(e -> {
            statusComboBox.setSelectedItem("All");
            startDateField.setText("");
            endDateField.setText("");
            sortComboBox.setSelectedItem("ASC");
            loadBorrowRecordsData();
        });

        JPanel buttonPanel = createButtonPanel();
        JButton addButton = createPrimaryButton("Add Borrow Record");
        JButton updateButton = createButton("Update");
        JButton updateStatusButton = createButton("Change Status");
        JButton deleteButton = createDangerButton("Delete");
        JButton refreshButton = createButton("Refresh");
        JButton overdueButton = createButton("Mark Overdue");
        addButton.addActionListener(e -> showAddBorrowRecordDialog());
        updateButton.addActionListener(e -> showUpdateBorrowRecordDialog());
        updateStatusButton.addActionListener(e -> showUpdateBorrowStatusDialog());
        deleteButton.addActionListener(e -> deleteSelectedBorrowRecord());
        refreshButton.addActionListener(e -> loadBorrowRecordsData());
        overdueButton.addActionListener(e -> {
            int updatedCount = borrowRecordDAO.updateOverdueRecordsAutomatically();
            JOptionPane.showMessageDialog(
                this,
                updatedCount + " record(s) marked as overdue.",
                "Overdue Update",
                JOptionPane.INFORMATION_MESSAGE
            );
            loadBorrowRecordsData();
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(updateStatusButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(overdueButton);
        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadBooksData() {
        statusLabel.setText("Loading books...");
        SwingWorker<List<Book>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Book> doInBackground() {
                return bookDAO.getAllBooks();
            }

            @Override
            protected void done() {
                try {
                    List<Book> books = get();
                    booksTableModel.setRowCount(0);
                    for (Book book : books) {
                        booksTableModel.addRow(new Object[]{
                            book.getBookId(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getCategory(),
                            book.getAvailabilityStatus()
                        });
                    }
                    statusLabel.setText("Books loaded successfully. Total: " + books.size());
                } catch (Exception e) {
                    statusLabel.setText("Failed to load books.");
                    JOptionPane.showMessageDialog(
                        LibraryDashboard.this,
                        "Error loading books: " + e.getMessage(),
                        "Loading Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
    }

    private void loadMembersData() {
        statusLabel.setText("Loading members...");
        SwingWorker<List<Member>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Member> doInBackground() {
                return memberDAO.getAllMembers();
            }

            @Override
            protected void done() {
                try {
                    List<Member> members = get();
                    membersTableModel.setRowCount(0);
                    for (Member member : members) {
                        membersTableModel.addRow(new Object[]{
                            member.getMemberId(),
                            member.getMemberName(),
                            member.getEmail(),
                            member.getMembershipType()
                        });
                    }

                    statusLabel.setText("Members loaded successfully. Total: " + members.size());

                } catch (Exception e) {
                    statusLabel.setText("Failed to load members.");
                    JOptionPane.showMessageDialog(
                            LibraryDashboard.this,
                            "Error loading members: " + e.getMessage(),
                            "Loading Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
    }

    private void loadBorrowRecordsData() {
        statusLabel.setText("Loading borrowing records...");
        SwingWorker<List<BorrowRecord>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<BorrowRecord> doInBackground() {
                return borrowRecordDAO.getAllBorrowRecords();
            }

            @Override
            protected void done() {
                try {
                    List<BorrowRecord> records = get();
                    borrowRecordsTableModel.setRowCount(0);
                    for (BorrowRecord record : records) {
                        borrowRecordsTableModel.addRow(new Object[]{
                            record.getRecordId(),
                            record.getBookId(),
                            record.getMemberId(),
                            record.getBorrowDate(),
                            record.getDueDate(),
                            record.getReturnStatus()
                        });
                    }

                    statusLabel.setText("Borrowing records loaded. Total: " + records.size());

                } catch (Exception e) {
                    statusLabel.setText("Failed to load borrowing records.");
                    JOptionPane.showMessageDialog(
                            LibraryDashboard.this,
                            "Error loading borrowing records: " + e.getMessage(),
                            "Loading Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
    }

    

    private void showAddBookDialog() {
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField categoryField = new JTextField();

        String[] statuses = {"Available", "Borrowed"};
        JComboBox<String> statusComboBox = new JComboBox<>(statuses);

        JPanel formPanel = createDialogFormPanel();
        addDialogRow(formPanel, 0, "Title", titleField);
        addDialogRow(formPanel, 1, "Author", authorField);
        addDialogRow(formPanel, 2, "Category", categoryField);
        addDialogRow(formPanel, 3, "Status", statusComboBox);
        int result = JOptionPane.showConfirmDialog(
                this,
                formPanel,
                "Add New Book",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String category = categoryField.getText().trim();
            String status = statusComboBox.getSelectedItem().toString();
            if (title.isEmpty() || author.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Title, author, and category cannot be empty.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Book book = new Book(title, author, category, status);
            if (bookDAO.addBook(book)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Book added",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                loadBooksData();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Book was not added.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void showUpdateBookDialog() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a book to update.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = booksTable.convertRowIndexToModel(selectedRow);
        int bookId = Integer.parseInt(booksTableModel.getValueAt(modelRow, 0).toString());
        String currentTitle = booksTableModel.getValueAt(modelRow, 1).toString();
        String currentAuthor = booksTableModel.getValueAt(modelRow, 2).toString();
        String currentCategory = booksTableModel.getValueAt(modelRow, 3).toString();
        String currentStatus = booksTableModel.getValueAt(modelRow, 4).toString();
        JTextField titleField = new JTextField(currentTitle);
        JTextField authorField = new JTextField(currentAuthor);
        JTextField categoryField = new JTextField(currentCategory);
        String[] statuses = {"Available", "Borrowed"};
        JComboBox<String> statusComboBox = new JComboBox<>(statuses);
        statusComboBox.setSelectedItem(currentStatus);

        JPanel formPanel = createDialogFormPanel();
        addDialogRow(formPanel, 0, "Title", titleField);
        addDialogRow(formPanel, 1, "Author", authorField);
        addDialogRow(formPanel, 2, "Category", categoryField);
        addDialogRow(formPanel, 3, "Status", statusComboBox);
        int result = JOptionPane.showConfirmDialog(
            this,
            formPanel,
            "Update Book",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String category = categoryField.getText().trim();
            String status = statusComboBox.getSelectedItem().toString();
            if (title.isEmpty() || author.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Title, author, and category not be empty.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Book updatedBook = new Book(bookId, title, author, category, status);
            if (bookDAO.updateBook(updatedBook)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Book updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                loadBooksData();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Book was not updated.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void deleteSelectedBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a book to deletee",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = booksTable.convertRowIndexToModel(selectedRow);
        int bookId = Integer.parseInt(booksTableModel.getValueAt(modelRow, 0).toString());
        String title = booksTableModel.getValueAt(modelRow, 1).toString();
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this book?\n\nBook ID: " + bookId + "\nTitle: " + title,
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (bookDAO.deleteBook(bookId)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Book deleted successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                loadBooksData();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Book was not deleted.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void showAddMemberDialog() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        String[] membershipTypes = {"Student", "Staff"};
        JComboBox<String> membershipTypeComboBox = new JComboBox<>(membershipTypes);
        JPanel formPanel = createDialogFormPanel();
        addDialogRow(formPanel, 0, "Member name", nameField);
        addDialogRow(formPanel, 1, "Email", emailField);
        addDialogRow(formPanel, 2, "Membership type", membershipTypeComboBox);
        int result = JOptionPane.showConfirmDialog(
            this,
            formPanel,
            "Add New Member",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String membershipType = membershipTypeComboBox.getSelectedItem().toString();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Member name cannot be empty.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                JOptionPane.showMessageDialog(
                    this,
                    "Invalid email format.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Member member = new Member(name, email, membershipType);
            if (memberDAO.addMember(member)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Member added",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                loadMembersData();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Member was not added.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void showUpdateMemberDialog() {
        int selectedRow = membersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a member to update.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = membersTable.convertRowIndexToModel(selectedRow);
        int memberId = Integer.parseInt(membersTableModel.getValueAt(modelRow, 0).toString());
        String currentName = membersTableModel.getValueAt(modelRow, 1).toString();
        String currentEmail = membersTableModel.getValueAt(modelRow, 2).toString();
        String currentMembershipType = membersTableModel.getValueAt(modelRow, 3).toString();
        JTextField nameField = new JTextField(currentName);
        JTextField emailField = new JTextField(currentEmail);
        String[] membershipTypes = {"Student", "Staff"};
        JComboBox<String> membershipTypeComboBox = new JComboBox<>(membershipTypes);
        membershipTypeComboBox.setSelectedItem(currentMembershipType);
        JPanel formPanel = createDialogFormPanel();
        addDialogRow(formPanel, 0, "Member name", nameField);
        addDialogRow(formPanel, 1, "Email", emailField);
        addDialogRow(formPanel, 2, "Membership type", membershipTypeComboBox);
        int result = JOptionPane.showConfirmDialog(
            this,
            formPanel,
            "Update Member",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String membershipType = membershipTypeComboBox.getSelectedItem().toString();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Member name not keep empty.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email format.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            Member updatedMember = new Member(memberId, name, email, membershipType);
            if (memberDAO.updateMember(updatedMember)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Member updated",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                loadMembersData();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Member was not updated.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void deleteSelectedMember() {
        int selectedRow = membersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a member to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = membersTable.convertRowIndexToModel(selectedRow);
        int memberId = Integer.parseInt(membersTableModel.getValueAt(modelRow, 0).toString());
        String memberName = membersTableModel.getValueAt(modelRow, 1).toString();
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this member?\n\nMember ID: "
                        + memberId + "\nName: " + memberName,
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (memberDAO.deleteMember(memberId)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Member deleted successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                loadMembersData();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Member was not deleted.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void showAddBorrowRecordDialog() {
        JTextField bookIdField = new JTextField();
        JTextField memberIdField = new JTextField();
        JTextField borrowDateField = new JTextField();
        JTextField dueDateField = new JTextField();
        String[] statuses = {"Borrowed", "Returned", "Overdue"};
        JComboBox<String> statusComboBox = new JComboBox<>(statuses);
        JPanel formPanel = createDialogFormPanel();
        addDialogRow(formPanel, 0, "Book ID", bookIdField);
        addDialogRow(formPanel, 1, "Member ID", memberIdField);
        addDialogRow(formPanel, 2, "Borrow date (YYYY-MM-DD)", borrowDateField);
        addDialogRow(formPanel, 3, "Due date (YYYY-MM-DD)", dueDateField);
        addDialogRow(formPanel, 4, "Status", statusComboBox);

        int result = JOptionPane.showConfirmDialog(
                this,
                formPanel,
                "Add Borrow Record",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String bookIdText = bookIdField.getText().trim();
            String memberIdText = memberIdField.getText().trim();
            String borrowDate = borrowDateField.getText().trim();
            String dueDate = dueDateField.getText().trim();
            String status = statusComboBox.getSelectedItem().toString();

            if (!bookIdText.matches("\\d+") || !memberIdText.matches("\\d+")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Book ID and Member ID must be numeric.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (!borrowDate.matches("\\d{4}-\\d{2}-\\d{2}") || !dueDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Dates must use YYYY-MM-DD format.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            BorrowRecord record = new BorrowRecord(Integer.parseInt(bookIdText),
                    Integer.parseInt(memberIdText),
                    borrowDate,
                    dueDate,
                    status
            );

            if (borrowRecordDAO.addBorrowRecord(record)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Borrow record added",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                loadBorrowRecordsData();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Borrow record was not added. Check that Book id and Member ID exist.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void showUpdateBorrowRecordDialog() {
        int selectedRow = borrowRecordsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a borrow record to update.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = borrowRecordsTable.convertRowIndexToModel(selectedRow);
        int recordId = Integer.parseInt(borrowRecordsTableModel.getValueAt(modelRow, 0).toString());
        String currentBookId = borrowRecordsTableModel.getValueAt(modelRow, 1).toString();
        String currentMemberId = borrowRecordsTableModel.getValueAt(modelRow, 2).toString();
        String currentBorrowDate = borrowRecordsTableModel.getValueAt(modelRow, 3).toString();
        String currentDueDate = borrowRecordsTableModel.getValueAt(modelRow, 4).toString();
        String currentStatus = borrowRecordsTableModel.getValueAt(modelRow, 5).toString();
        JTextField bookIdField = new JTextField(currentBookId);
        JTextField memberIdField = new JTextField(currentMemberId);
        JTextField borrowDateField = new JTextField(currentBorrowDate);
        JTextField dueDateField = new JTextField(currentDueDate);
        String[] statuses = {"Borrowed", "Returned", "Overdue"};
        JComboBox<String> statusComboBox = new JComboBox<>(statuses);
        statusComboBox.setSelectedItem(currentStatus);
        JPanel formPanel = createDialogFormPanel();
        addDialogRow(formPanel, 0, "Book ID", bookIdField);
        addDialogRow(formPanel, 1, "Member ID", memberIdField);
        addDialogRow(formPanel, 2, "Borrow date (YYYY-MM-DD)", borrowDateField);
        addDialogRow(formPanel, 3, "Due date (YYYY-MM-DD)", dueDateField);
        addDialogRow(formPanel, 4, "Status", statusComboBox);

        int result = JOptionPane.showConfirmDialog(
            this,
            formPanel,
            "Update Borrow Record",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String bookIdText = bookIdField.getText().trim();
            String memberIdText = memberIdField.getText().trim();
            String borrowDate = borrowDateField.getText().trim();
            String dueDate = dueDateField.getText().trim();
            String status = statusComboBox.getSelectedItem().toString();

            if (!bookIdText.matches("\\d+") || !memberIdText.matches("\\d+")) {
                JOptionPane.showMessageDialog(
                    this,
                    "Book ID and Member ID must be numeric.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (!borrowDate.matches("\\d{4}-\\d{2}-\\d{2}") || !dueDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(
                    this,
                    "Dates must use YYYY-MM-DD format.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            BorrowRecord updatedRecord = new BorrowRecord(
                recordId,
                Integer.parseInt(bookIdText),
                Integer.parseInt(memberIdText),
                borrowDate,
                dueDate,
                status
            );

            if (borrowRecordDAO.updateBorrowRecord(updatedRecord)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Borrow record updated.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                loadBorrowRecordsData();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Borrow record was not updated.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void showUpdateBorrowStatusDialog() {
        int selectedRow = borrowRecordsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a borrow record to update status.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = borrowRecordsTable.convertRowIndexToModel(selectedRow);
        int recordId = Integer.parseInt(borrowRecordsTableModel.getValueAt(modelRow, 0).toString());
        String currentStatus = borrowRecordsTableModel.getValueAt(modelRow, 5).toString();
        String[] statuses = {"Borrowed", "Returned", "Overdue"};
        JComboBox<String> statusComboBox = new JComboBox<>(statuses);
        statusComboBox.setSelectedItem(currentStatus);
        JPanel formPanel = createDialogFormPanel();
        addDialogRow(formPanel, 0, "Status", statusComboBox);

        int result = JOptionPane.showConfirmDialog(
            this,
            formPanel,
            "Update Borrow Status",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String newStatus = statusComboBox.getSelectedItem().toString();
            if (borrowRecordDAO.updateBorrowStatus(recordId, newStatus)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Borrow status updated",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                loadBorrowRecordsData();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Borrow status was not updated.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void deleteSelectedBorrowRecord() {
        int selectedRow = borrowRecordsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a borrow record to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow = borrowRecordsTable.convertRowIndexToModel(selectedRow);
        int recordId = Integer.parseInt(borrowRecordsTableModel.getValueAt(modelRow, 0).toString());
        String bookId = borrowRecordsTableModel.getValueAt(modelRow, 1).toString();
        String memberId = borrowRecordsTableModel.getValueAt(modelRow, 2).toString();
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this borrow record?\n\nRecord ID: "
                        + recordId + "\nBook ID: " + bookId + "\nMember ID: " + memberId,
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (borrowRecordDAO.deleteBorrowRecord(recordId)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Borrow record deleted",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                loadBorrowRecordsData();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Borrow record was not deleted.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void loadAdvancedBookSearchData(String title, String author, String category, String sortOrder) {
        statusLabel.setText("Searching books...");
        SwingWorker<List<Book>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Book> doInBackground() {
                return bookDAO.advancedSearchBooks(title, author, category, sortOrder);
            }

            @Override
            protected void done() {
                try {
                    List<Book> books = get();
                    booksTableModel.setRowCount(0);
                    for (Book book : books) {
                        booksTableModel.addRow(new Object[]{
                            book.getBookId(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getCategory(),
                            book.getAvailabilityStatus()
                        });
                    }
                    statusLabel.setText("Book search completed. Results: " + books.size());
                    if (books.isEmpty()) {
                        JOptionPane.showMessageDialog(
                            LibraryDashboard.this,
                            "No books found for the selected search criteria.",
                            "Search Result",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                } catch (Exception e) {
                    statusLabel.setText("Book search failed.");
                    JOptionPane.showMessageDialog(
                        LibraryDashboard.this,
                        "Error searching books: " + e.getMessage(),
                        "Search Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };
        worker.execute();
    }

    private void loadFilteredBorrowRecordsData(String status, String startDate, String endDate, String sortOrder) {
        boolean hasDateRange = !startDate.isEmpty() && !endDate.isEmpty();
        if (hasDateRange) {
            if (!startDate.matches("\\d{4}-\\d{2}-\\d{2}") || !endDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(
                    this,
                    "Dates must use YYYY-MM-DD format.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        }
        statusLabel.setText("Filtering borrowing records...");
        SwingWorker<List<BorrowRecord>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<BorrowRecord> doInBackground() {
                List<BorrowRecord> records;
                boolean hasStatus = !status.equalsIgnoreCase("All");
                if (hasDateRange) {
                    records = borrowRecordDAO.filterBorrowRecordsByDateRangeSorted(startDate, endDate, sortOrder);
                    if (hasStatus) {
                        records.removeIf(record -> !record.getReturnStatus().equalsIgnoreCase(status));
                    }

                } else if (hasStatus) {
                    records = borrowRecordDAO.filterBorrowRecordsByStatus(status);
                } else {
                    records = borrowRecordDAO.getAllBorrowRecords();
                }

                return records;
            }

            @Override
            protected void done() {
                try {
                    List<BorrowRecord> records = get();
                    borrowRecordsTableModel.setRowCount(0);
                    for (BorrowRecord record : records) {
                        borrowRecordsTableModel.addRow(new Object[]{
                            record.getRecordId(),
                            record.getBookId(),
                            record.getMemberId(),
                            record.getBorrowDate(),
                            record.getDueDate(),
                            record.getReturnStatus()
                        });
                    }
                    statusLabel.setText("Borrowing record filter completed. Results: " + records.size());
                    if (records.isEmpty()) {
                        JOptionPane.showMessageDialog(
                            LibraryDashboard.this,
                            "No borrowing records found for the selected filter criteria.",
                            "Filter Result",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                } catch (Exception e) {
                    statusLabel.setText("Borrowing record filter failed.");
                    JOptionPane.showMessageDialog(
                        LibraryDashboard.this,
                        "Error filtering borrowing records: " + e.getMessage(),
                        "Filter Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
    }
}
