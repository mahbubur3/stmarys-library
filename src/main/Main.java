package main;
import main.database.DatabaseConnection;
import main.gui.LibraryDashboard;
import main.database.DatabaseOptimizer;
import javax.swing.SwingUtilities;
public class Main {
    
    public static void main(String[] args) {
        System.out.println("sarting the system...");
        DatabaseConnection.testConnection();
        DatabaseOptimizer.createIndexes();

        SwingUtilities.invokeLater(() -> {
            LibraryDashboard dashboard = new LibraryDashboard();
            dashboard.setVisible(true);
        });
    }
}