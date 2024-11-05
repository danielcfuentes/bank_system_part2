import java.util.*;

/**
 * Implementation of Menu interface for bank manager operations.
 * Handles display and processing of manager menu options.
 * @author Daniel Fuentes, Rogelio Lozano
 * @version 2.0
 */
public class BankManagerMenu implements Menu {
    /** Scanner for reading user input */
    private Scanner scanner;
    /** Logger for recording transactions */
    private TransactionLog logger;
    /** Map of all customers in the system */
    private Map<String, Customer> customers;

    /**
     * Creates a new bank manager menu.
     * @param logger transaction logging system
     * @param customers map of all customers in the system
     * @param newUsers system for creating new users
     * @param transactionProcessor processor for handling transactions
     */
    public BankManagerMenu(TransactionLog logger, Map<String, Customer> customers) {
        this.scanner = new Scanner(System.in);
        this.logger = logger;
        this.customers = customers;
    }

    @Override
    public void displayMenu() {
        System.out.println("\nBank Manager Menu");
        System.out.println("1. Inquire account by name");
        System.out.println("2. Inquire account by account number");
        System.out.println("3. Create new user");
        System.out.println("4. Process transaction file");
        System.out.println("5. Generate bank statement");
        System.out.println("6. Return to main menu");
        System.out.println("__________________");
    }

    @Override
    public boolean handleChoice(String choice) {
        try {
            switch (choice) {
                case "1":
                    handleNameInquiry();
                    return true;
                case "2":
                    handleAccountInquiry();
                    return true;
                case "3":
                    // handleNewUser();
                    return true;
                case "4":
                    // handleTransactionFile();
                    return true;
                case "5":
                    // handleBankStatement();
                    return true;
                case "6":
                    return false;
                default:
                    System.out.println("Invalid choice.");
                    return true;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return true;
        }
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }
    /**
     * Handles bank manager inquiry of customer accounts by customer name.
     * Can handle customers with same first or last names.
     */
    private void handleNameInquiry() {
        System.out.println("Enter first name:");
        System.out.println("__________________");
        String firstName = getInput();
        System.out.println("Enter last name:");
        System.out.println("__________________");
        String lastName = getInput();

        // Handle case of multiple customers with same name
        List<Customer> matchingCustomers = new ArrayList<>();
        String fullName = firstName + " " + lastName;
        
        // Search for exact match first
        Customer exactMatch = customers.get(fullName);
        if (exactMatch != null) {
            matchingCustomers.add(exactMatch);
        }
        
        // Search for partial matches (same first or last name)
        for (Customer customer : customers.values()) {
            String[] names = customer.getName().split(" ");
            if (!customer.getName().equals(fullName) && 
                (names[0].equals(firstName) || names[1].equals(lastName))) {
                matchingCustomers.add(customer);
            }
        }

        if (matchingCustomers.isEmpty()) {
            System.out.println("No customers found with that name.");
            return;
        }

        // If multiple matches, let manager choose
        if (matchingCustomers.size() > 1) {
            System.out.println("Multiple customers found:");
            for (int i = 0; i < matchingCustomers.size(); i++) {
                Customer c = matchingCustomers.get(i);
                System.out.printf("%d. %s (ID: %s)%n", 
                    i + 1, c.getName(), c.getCustomerID());
            }
            System.out.print("Select customer number: ");
            try {
                int choice = Integer.parseInt(getInput()) - 1;
                if (choice >= 0 && choice < matchingCustomers.size()) {
                    displayCustomerAccounts(matchingCustomers.get(choice));
                } else {
                    System.out.println("Invalid selection.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        } else {
            displayCustomerAccounts(matchingCustomers.get(0));
        }
    }

    /**
     * Handles bank manager inquiry of customer accounts by account number.
     */
    private void handleAccountInquiry() {
        System.out.println("Enter account number:");
        System.out.println("__________________");
        String accountNum = getInput();

        boolean found = false;
        for (Customer customer : customers.values()) {
            for (Account account : customer.getAccounts()) {
                if (account.getAccountNumber().equals(accountNum)) {
                    System.out.printf("%s's %s (%s): $%.2f%n",
                        customer.getName(),
                        account.getClass().getSimpleName(),
                        account.getAccountNumber(),
                        account.getBalance()
                    );
                    found = true;
                    logger.logTransaction(
                        String.format("Bank Manager inquired about account %s. Balance: $%.2f",
                            accountNum, account.getBalance())
                    );
                    break;
                }
            }
            if (found) break;
        }
        
        if (!found) {
            System.out.println("Account not found.");
        }
    }

    /**
     * Displays account information for a specific customer.
     * @param customer the customer whose accounts to display
     */
    private void displayCustomerAccounts(Customer customer) {
        System.out.printf("\nAccounts for %s (ID: %s):%n", 
            customer.getName(), customer.getCustomerID());
        System.out.println("__________________");
        
        for (Account account : customer.getAccounts()) {
            System.out.printf("%s (%s): $%.2f%n",
                account.getClass().getSimpleName(),
                account.getAccountNumber(),
                account.getBalance()
            );
            logger.logTransaction(
                String.format("Bank Manager inquired about %s's %s. Balance: $%.2f",
                    customer.getName(),
                    account.getAccountNumber(),
                    account.getBalance())
            );
        }
    }
}