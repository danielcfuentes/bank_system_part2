import java.util.*;
/**
 * Manages banking operations for customers and bank managers.
 * Handles transactions, inquiries, and account management.
 * @author Daniel Fuentes, Rogelio Lozano
 * @version 1.0
 */
public class BankOperations {
    /** Map of customer names to Customer objects */
    private Map<String, Customer> customers;
    /** Scanner for reading user input */
    private Scanner scanner;
    /** Logger for recording transactions into log */
    private TransactionLog logger;

    /**
     * Initializes bank operations with customer data and transaction logging.
     * @param customers map of customer names to Customer objects
     * @param logger transaction logging system
     */
    public BankOperations(Map<String, Customer> customers, TransactionLog logger) {
        this.customers = customers;
        this.scanner = new Scanner(System.in);
        this.logger = logger;
    }

    /**
     * Handles customer login and menu operations.
     * Provides interface for account inquiries, deposits, withdrawals, transfers, and payments.
     */
    public void handleCustomer() {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        
        Customer customer = customers.get(name);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        
        // Create customer menu
        CustomerMenu menu = new CustomerMenu(customer, logger, customers);
        
        // Handle menu operations until user exits
        while (true) {
            menu.displayMenu();
            String choice = menu.getInput();
            if (!menu.handleChoice(choice)) {
                break;
            }
        }
    }

    /**
     * Handles bank manager operations.
     * Provides interface for account inquiries by name or account number.
     */
    public void handleBankManager() {
        // Create bank manager menu
        BankManagerMenu menu = new BankManagerMenu(logger, customers);
        
        // Handle menu operations until manager exits
        while (true) {
            menu.displayMenu();
            String choice = menu.getInput();
            if (!menu.handleChoice(choice)) {
                break;
            }
        }
    }
}