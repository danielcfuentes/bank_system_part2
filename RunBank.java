import java.util.*;

/**
 * Main class for the banking application.
 * @author Daniel Fuentes, Rogelio Lozano
 * @version 1.0
 */
public class RunBank {
    /**
     * Main starting point for the banking application.
     * Initializes the system, presents menu options, and user interaction.
     */
    public static void main(String[] args) {
        /** Stores all customer data */
        Map<String, Customer> customers;
        /** Handles user input */
        Scanner scanner = new Scanner(System.in);
        /** Records all transactions */
        TransactionLog logger = new TransactionLog();
        /** Manages CSV file operations */
        CSVHandler csvHandler = new CSVHandler();
        
        try {
            // loads data
            customers = csvHandler.loadCustomerData();
            BankOperations operations = new BankOperations(customers, logger);

            // main menu loop
            while (true) {
                System.out.println("\nWelcome to El Paso Miners Bank");
                System.out.println("1. Individual Customer");
                System.out.println("2. Bank Manager");
                System.out.println("Type 'EXIT' to quit");
                System.out.println("_________________________");
                
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("EXIT")) {
                    break;
                }
                
                switch (choice) {
                    case "1":
                        operations.handleCustomer();
                        break;
                    case "2":
                        operations.handleBankManager();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            // save and exit
            csvHandler.saveCustomerData(customers);
            logger.exitUpdate();
            System.out.println("____________________");
            System.out.println("Thank you for using El Paso Miners Bank!");
            
        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}