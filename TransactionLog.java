import java.io.*;
import java.util.*;
/**
 * Handles logging of all banking transactions.
 * Maintains record of activities in the system.
 * @author Daniel Fuentes, Rogelio Lozano
 * @version 1.0
 */
public class TransactionLog {
    /**File that stores all activites of the whole system */
    private static final String LOG_FILE = "transaction_log.txt";
    /** List storing all transaction log entries  */
    private List<String> currentLogs;

    /**
     * Creates a new transaction logger.
     * Initializes the log
     */
    public TransactionLog() {
        this.currentLogs = new ArrayList<>();
        loadExistingLogs();
    }

    /**
     * Loads existing transaction logs from file.
     * Creates new log file if it doesn't exist.
     */
    private void loadExistingLogs() {
        try {
            File file = new File(LOG_FILE);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    currentLogs.add(line);
                }
                reader.close();
            } else {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error loading transaction log: " + e.getMessage());
        }
    }

    /**
     * Gets all current log entries. 
     * @return list of all transaction logs
     */
    public List<String> getLogEntries() {
        return new ArrayList<>(currentLogs);
    }

    /**
     * Logs a new transaction and writes it to file.
     * Immediately keeps the transaction.
     * @param transaction the transaction details to log
     */
    public void logTransaction(String transaction) {
        String logEntry = String.format(transaction);
        currentLogs.add(logEntry);
        
        //immediately write to file
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logEntry + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to transaction log: " + e.getMessage());
        }
    }

    /**
     * Updates the log file with all current transactions before system exit.
     * Writes all logged transactions.
     */
    public void exitUpdate() {
        try (FileWriter writer = new FileWriter(LOG_FILE)) {
            for (String log : currentLogs) {
                writer.write(log + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error updating transaction log on exit: " + e.getMessage());
        }
    }
}