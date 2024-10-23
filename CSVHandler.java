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
            List<String> lines = Files.readAllLines(Paths.get(CSV_FILE));
            lines.remove(0); // Remove header

            for (String line : lines) {
                try {
                    // Split line while preserving commas in quoted values
                    String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    
                    // Clean the data
                    for (int i = 0; i < data.length; i++) {
                        data[i] = data[i].trim().replace("\"", "");
                    }
                    
                    // Create customer name and ID
                    String fullName = data[1].trim() + " " + data[2].trim();
                    Customer customer = new Customer(fullName, data[0].trim());
                    
                    // Create accounts - using specific indexes for account data
                    List<Account> accounts = new ArrayList<>();
                    
                    // Add checking account - indexes 6 and 7
                    String checkingNumber = data[6].trim();
                    double checkingBalance = Double.parseDouble(data[7].trim());
                    accounts.add(new Checkings(checkingNumber, checkingBalance));
                    
                    // Add savings account - indexes 8 and 9
                    String savingsNumber = data[8].trim();
                    double savingsBalance = Double.parseDouble(data[9].trim());
                    accounts.add(new Savings(savingsNumber, savingsBalance));
                    
                    // Add credit account - indexes 10, 11, and 12
                    String creditNumber = data[10].trim();
                    double creditMax = Double.parseDouble(data[11].trim());
                    double creditBalance = Double.parseDouble(data[12].trim());
                    accounts.add(new Credit(creditNumber, creditBalance, creditMax));
                    
                    customer.setAccounts(accounts);
                    customers.put(fullName, customer);
                    
                    // Debug print
                    System.out.println("Loaded customer: " + fullName); // This helps see what names are actually loaded
                    
                } catch (Exception e) {
                    System.out.println("Error processing line: " + line);
                    System.out.println("Error details: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return customers;
    }

    /**
     * Saves updated customer data back to CSV
     * @param customers Map of customers to save
     */
    public void saveCustomerData(Map<String, Customer> customers) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(CSV_FILE));
            List<String> newLines = new ArrayList<>();
            newLines.add(lines.get(0)); // Keep header
            
            // Create a map of customer IDs to their data for easier lookup
            Map<String, String[]> dataMap = new HashMap<>();
            for (int i = 1; i < lines.size(); i++) {
                String[] data = lines.get(i).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                dataMap.put(data[0].trim(), data);
            }
            
            // Update data with new balances
            for (Customer customer : customers.values()) {
                String[] data = dataMap.get(customer.getCustomerID());
                if (data != null) {
                    List<Account> accounts = customer.getAccounts();
                    
                    // Update balances in data array
                    if (accounts.size() >= 3) {
                        data[7] = String.format("%.2f", accounts.get(0).getBalance()); // Checking
                        data[9] = String.format("%.2f", accounts.get(1).getBalance()); // Savings
                        data[12] = String.format("%.2f", accounts.get(2).getBalance()); // Credit
                    }
                    
                    // Reconstruct the line with proper formatting
                    StringBuilder line = new StringBuilder();
                    for (int i = 0; i < data.length; i++) {
                        // Add quotes around strings that might contain commas
                        if (i == 4 || i == 5) { // Address and phone number
                            line.append("\"").append(data[i].trim()).append("\"");
                        } else {
                            line.append(data[i].trim());
                        }
                        if (i < data.length - 1) {
                            line.append(",");
                        }
                    }
                    newLines.add(line.toString());
                }
            }
            
            Files.write(Paths.get(CSV_FILE), newLines);
            
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}