import java.util.List;

public class TransactionLog {
    private List<String> currentLogs;

    // no arg constrouctor
    public TransactionLog() {}

    // getter
    public List<String> getLogEntries() {
        return currentLogs;
    }

    public void logTransaction(String transaction) {}

    public void exitUpdate() {}
}
