public abstract class Account {
    protected double balance;
    protected String accountNumber;
    
    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Account(){}

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
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            logTransaction("Deposit of $" + amount + " successful. New balance: $" + balance);
        } else {
            throw new IllegalArgumentException("Not Valid Amount: Deposit amount must be positive");
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            logTransaction("Withdrawal of $" + amount + " successful. New balance: $" + balance);
        } else {
            throw new IllegalArgumentException("Invalid withdrawal amount or insufficient funds");
        }
    }
    
    // TODO: make this work
    public void logTransaction(String transaction) {
        return;
    }
}