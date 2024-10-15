import java.util.List;

public class TransactionLog {
    private List<String> currLogs;

    // no arg constrouctor
    public TransactionLog() {}

    // getter
    public List<String> getLogEntries() {
        return currLogs;
    }

    public void logTransaction(String transaction) {}

    public void exitUpdate() {}
}
