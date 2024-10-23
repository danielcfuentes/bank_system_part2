import java.io.*;
import java.util.*;

public class TransactionLog {
    private static final String LOG_FILE = "transaction_log.txt";
    private List<String> currentLogs;

    public TransactionLog() {
        this.currentLogs = new ArrayList<>();
        loadExistingLogs();
    }

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

    public List<String> getLogEntries() {
        return new ArrayList<>(currentLogs);
    }

    public void logTransaction(String transaction) {
        String logEntry = String.format(transaction);
        currentLogs.add(logEntry);
        
        // Immediately write to file to prevent data loss
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logEntry + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to transaction log: " + e.getMessage());
        }
    }

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