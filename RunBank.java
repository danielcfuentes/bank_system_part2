import java.util.*;

public class RunBank {
    public static void main(String[] args) {
        Map<String, Customer> customers;
        Scanner scanner = new Scanner(System.in);
        TransactionLog logger = new TransactionLog();
        CSVHandler csvHandler = new CSVHandler();
        
        try {
            // Load data
            customers = csvHandler.loadCustomerData();
            BankOperations operations = new BankOperations(customers, logger);

            // Main menu loop
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

            // Save and exit
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