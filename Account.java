public abstract class Account {
    protected double balance;
    protected String accountNumber;
    private static TransactionLog transactionLog;
    
    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Account() {}

    public static void setTransactionLog(TransactionLog log) {
        transactionLog = log;
    }

    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public double inquireBalance() {
        if (transactionLog != null) {
            transactionLog.logTransaction(
                String.format("Balance inquiry for %s: $%.2f", 
                    accountNumber, balance)
            );
        }
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            if (transactionLog != null) {
                transactionLog.logTransaction(
                    String.format("Deposit of $%.2f to %s. New balance: $%.2f", 
                        amount, accountNumber, balance)
                );
            }
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            if (transactionLog != null) {
                transactionLog.logTransaction(
                    String.format("Withdrawal of $%.2f from %s. New balance: $%.2f", 
                        amount, accountNumber, balance)
                );
            }
        } else {
            throw new IllegalArgumentException("Invalid withdrawal amount or insufficient funds");
        }
    }
}