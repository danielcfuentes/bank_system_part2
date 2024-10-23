/**
 * Abstract class representing a generic bank account.
 */
public abstract class Account {
    protected double balance;
    protected String accountNumber;
    
    /**
     * Initializes an account with the given account number and balance.
     * 
     * @param accountNumber the account identifier
     * @param balance the initial balance
     */
    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Account(){}

    /**
     * Returns the current account balance.
     * 
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * Sets a new balance for the account.
     * 
     * @param balance the new balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    /**
     * Returns the account number.
     * 
     * @return the account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }
    
    /**
     * Returns the current balance.
     * 
     * @return the balance
     */
    public double inquireBalance() {
        return balance;
    }

    /**
     * Deposits a specified amount into the account.
     * 
     * @param amount the amount to deposit
     * returns an error if the amount is not positive
     */
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            logTransaction("Deposit of $" + amount + " successful. New balance: $" + balance);
        } else {
            throw new IllegalArgumentException("Not Valid Amount: Deposit amount must be positive");
        }
    }
    
    /**
     * Withdraws a specified amount from the account.
     * 
     * @param amount the amount to withdraw
     * @throws IllegalArgumentException if the amount is invalid or insufficient funds
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            logTransaction("Withdrawal of $" + amount + " successful. New balance: $" + balance);
        } else {
            throw new IllegalArgumentException("Invalid withdrawal amount or insufficient funds");
        }
    }
    
    /**
     * Logs a transaction (placeholder implementation).
     * 
     * @param transaction the transaction details
     */
    // TODO: make this work
    public void logTransaction(String transaction) {
        return;
    }
}