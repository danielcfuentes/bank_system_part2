import java.util.*;

public abstract class Person {
    // Store person's name
    private String name;
    
    // List to store accounts
    private List<Account> accounts;
    
    // Constructor
    public Person(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }
    
    // Getter for name
    public String getName() {
        return name;
    }
    
    // Setter for name
    public void setName(String name) {
        this.name = name;
    }
    
    // Getter for accounts
    public List<Account> getAccounts() {
        return accounts;
    }
    
    // Setter for accounts
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    
    // Method 1: Customer inquiring their own account by account number
    public List<Account> inquireAccount(String accountNumber) {
        // Create new list for matching accounts
        List<Account> foundAccounts = new ArrayList<>();
        
        // Loop through accounts to find matches
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                foundAccounts.add(account);
            }
        }
        return foundAccounts;
    }
    
    // Method 2: Bank manager inquiring any account by account number
    public static List<Account> inquireAccount(String accountNumber, Map<String, Customer> allCustomers) {
        // Look through all customers
        for (Customer customer : allCustomers.values()) {
            // Check each customer's accounts
            for (Account account : customer.getAccounts()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    // Create list for found account
                    List<Account> found = new ArrayList<>();
                    found.add(account);
                    
                    // Log the inquiry
                    return found;
                }
            }
        }
        // Return empty list if not found
        return new ArrayList<>();
    }
    
    // Method 3: Bank manager inquiring accounts by customer name
    public static List<Account> inquireAccount(String firstName, String lastName, Map<String, Customer> allCustomers) {
        // Combine first and last name
        String fullName = firstName + " " + lastName;
        
        // Look up customer
        Customer customer = allCustomers.get(fullName);
        
        if (customer != null) {
            // Log the inquiry
            // Return copy of customer's accounts
            return new ArrayList<>(customer.getAccounts());
        }
        
        // Return empty list if customer not found
        return new ArrayList<>();
    }
    
    // Get all accounts
    public List<Account> inquireAllAccounts() {
        // Return a copy of accounts list
        return new ArrayList<>(accounts);
    }

      // Method to pay another person
    public void pay(Person receiver, Account fromAccount, Account toAccount, double amount) {
        // Check if amount is valid
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        
        // Check if sending account belongs to this person
        if (!this.accounts.contains(fromAccount)) {
            throw new IllegalArgumentException("Source account does not belong to you");
        }
        
        // Check if receiving account belongs to receiver
        if (!receiver.getAccounts().contains(toAccount)) {
            throw new IllegalArgumentException("Destination account does not belong to receiver");
        }
        
        try {
            // Withdraw from sender's account
            fromAccount.withdraw(amount);
            
            // Deposit to receiver's account
            toAccount.deposit(amount);
            
            // Log the transaction
            
            // Log receiver's updated balance
            
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Payment failed: " + e.getMessage());
        }
    }
}