import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Handles reading and writing customer data from/to CSV files.
 * @author Daniel Fuentes, Rogelio Lozano
 * @version 1.0
 */
public class CSVHandler {
    /** The file path for bank users data */
    private static final String CSV_FILE = "CS 3331 - Bank Users.csv";

    /**
     * Loads all customer data from the CSV file.
     * @return map of customer names to Customer objects
     */
    public Map<String, Customer> loadCustomerData() {
        Map<String, Customer> customers = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE));
            String line = reader.readLine();
            //loop through each line in csv
            while ((line = reader.readLine()) != null) {
                try {
                    //split by comma but preserve commas inside quotes
                    String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    
                    //clean the data by remove quotes and trim
                    for (int i = 0; i < parts.length; i++) {
                        parts[i] = parts[i].replace("\"", "").trim();
                    }
                    
                    //create customer from the columns in the csv
                    String fullName = parts[1] + " " + parts[2];
                    Customer customer = new Customer(fullName, parts[0]);
                    
                    //create the checkings, savings, and credit accounts
                    List<Account> accounts = new ArrayList<>();
                    accounts.add(new Checkings(parts[6], Double.parseDouble(parts[7])));
                    accounts.add(new Savings(parts[8], Double.parseDouble(parts[9])));
                    accounts.add(new Credit(parts[10], Double.parseDouble(parts[12]), 
                                         Double.parseDouble(parts[11])));
                    
                    customer.setAccounts(accounts);
                    customers.put(fullName, customer);
                    
                } catch (Exception e) {
                    System.out.println("Error processing line: " + e.getMessage());
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return customers;
    }

     /**
     * Saves customer data back to the CSV file.
     * Updates only the balance fields while keeping the other data the same.
     * @param customers map of customer names to Customer objects to save
     */
    public void saveCustomerData(Map<String, Customer> customers) {
        try {
            //read all lines to keep original data
            List<String> lines = Files.readAllLines(Paths.get(CSV_FILE));
            List<String> newLines = new ArrayList<>();
            newLines.add(lines.get(0));
            
            //process each line
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                String fullName = parts[1] + " " + parts[2];
                Customer customer = customers.get(fullName);
                
                if (customer != null) {
                    //update the balance fields
                    parts[7] = String.format("%.2f", customer.getAccounts().get(0).getBalance());
                    parts[9] = String.format("%.2f", customer.getAccounts().get(1).getBalance());
                    parts[12] = String.format("%.2f", customer.getAccounts().get(2).getBalance());
                }
                
                //join the parts back together
                newLines.add(String.join(",", parts));
            }
            
            //write back to file
            FileWriter writer = new FileWriter(CSV_FILE);
            for (String line : newLines) {
                writer.write(line + "\n");
            }
            writer.close();
            
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}