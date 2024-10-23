import java.util.*;
import java.util.stream.Collectors;

public abstract class Person {
    // Store person's name
    private String name;
    
    // List to store all accounts belonging to this person
    private List<Account> accounts;
    
    // Constructor that initializes name and empty accounts list
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
        return accounts.stream()
            .filter(account -> account.getAccountNumber().equals(accountNumber))
            .collect(Collectors.toList());
    }
    
    // Method 2: Bank manager inquiring any account by account number (static method)
    public static List<Account> inquireAccount(String accountNumber, Map<String, Customer> allCustomers) {
        // Search all customers for the account
        for (Customer customer : allCustomers.values()) {
            List<Account> found = customer.inquireAccount(accountNumber);
            if (!found.isEmpty()) {
                //log
                return found;
            }
        }
        return new ArrayList<>();
    }
    
    // Method 3: Bank manager inquiring accounts by customer name (static method)
    public static List<Account> inquireAccount(String firstName, String lastName, Map<String, Customer> allCustomers) {
        String fullName = firstName + " " + lastName;
        Customer customer = allCustomers.get(fullName);
        if (customer != null) {
            // log
            return customer.getAccounts();
        }
        return new ArrayList<>();
    }
    
    // Get all accounts for this person
    public List<Account> inquireAllAccounts() {
        return new ArrayList<>(accounts);
    }
}