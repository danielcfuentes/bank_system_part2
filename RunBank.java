import java.util.*;

public class RunBank {
    private static Map<String, Customer> customers;
    private static Scanner scanner;
    private static TransactionLog logger;
    private static CSVHandler csvHandler;
    private static BankOperations operations;

    public static void main(String[] args) {
        try {
            initialize();
            runMainMenu();
            saveAndExit();
        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void initialize() {
        scanner = new Scanner(System.in);
        logger = new TransactionLog();
        csvHandler = new CSVHandler();
        customers = csvHandler.loadCustomerData();
        operations = new BankOperations(customers, logger);
    }

    private static void runMainMenu() {
        while (true) {
            System.out.println("\nWelcome to El Paso Miners Bank");
            System.out.println("1. Individual Customer");
            System.out.println("2. Bank Manager");
            System.out.println("Type 'EXIT' to quit");
            
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
    }

    private static void saveAndExit() {
        csvHandler.saveCustomerData(customers);
        logger.exitUpdate();
        System.out.println("Thank you for using El Paso Miners Bank!");
    }
}