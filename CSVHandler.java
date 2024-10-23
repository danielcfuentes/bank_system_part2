import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CSVHandler {
    private static final String CSV_FILE = "CS 3331 - Bank Users.csv";

    /**
     * Loads customer data from CSV file
     * @return Map of customers with names as keys
     */
    public Map<String, Customer> loadCustomerData() {
        Map<String, Customer> customers = new HashMap<>();
        try {
            // Read all lines except header
            List<String> lines = Files.readAllLines(Paths.get(CSV_FILE));
            lines.remove(0); // Remove header

            for (String line : lines) {
                String[] data = line.split(",");
                
                // Create customer name and ID
                String fullName = data[1] + " " + data[2];
                Customer customer = new Customer(fullName, data[0]);
                
                // Create accounts
                List<Account> accounts = new ArrayList<>();
                
                // Add checking account
                accounts.add(new Checkings(data[6], Double.parseDouble(data[7])));
                
                // Add savings account
                accounts.add(new Savings(data[8], Double.parseDouble(data[9])));
                
                // Add credit account
                accounts.add(new Credit(data[10], Double.parseDouble(data[12]), 
                                     Double.parseDouble(data[11])));
                
                customer.setAccounts(accounts);
                customers.put(fullName, customer);
            }
            
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return customers;
    }
    
    /**
     * Saves updated customer data back to CSV
     * @param customers Map of customers to save
     */
    public void saveCustomerData(Map<String, Customer> customers) {
        try {
            // First read all lines to keep original data
            List<String> lines = Files.readAllLines(Paths.get(CSV_FILE));
            List<String> newLines = new ArrayList<>();
            
            // Keep the header
            newLines.add(lines.get(0));
            
            // Update each line with new balances
            for (int i = 1; i < lines.size(); i++) {
                String[] data = lines.get(i).split(",");
                String fullName = data[1] + " " + data[2];
                Customer customer = customers.get(fullName);
                
                if (customer != null) {
                    List<Account> accounts = customer.getAccounts();
                    // Update balances in data array
                    data[7] = String.valueOf(accounts.get(0).getBalance()); // Checking
                    data[9] = String.valueOf(accounts.get(1).getBalance()); // Savings
                    data[12] = String.valueOf(accounts.get(2).getBalance()); // Credit
                }
                
                // Join the data back together and add to new lines
                newLines.add(String.join(",", data));
            }
            
            // Write everything back to file
            Files.write(Paths.get(CSV_FILE), newLines);
            
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}