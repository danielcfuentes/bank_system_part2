import java.util.*;

/**
 * Implementation of Menu interface for customer operations.
 * Handles display and processing of customer-specific menu options.
 * @author Daniel Fuentes, Rogelio Lozano
 * @version 2.0
 */
public class CustomerMenu implements Menu {
    /** Scanner for reading user input */
    private Scanner scanner;
    /** The customer using this menu */
    private Customer customer;
    /** Logger for recording transactions */
    private TransactionLog logger;
    /** Map of all customers in the system */
    private Map<String, Customer> customers;

    /**
     * Creates a new customer menu instance.
     * @param customer the customer using the menu
     * @param logger transaction logging system
     * @param customers map of all customers in the system
     */
    public CustomerMenu(Customer customer, TransactionLog logger, Map<String, Customer> customers) {
        this.scanner = new Scanner(System.in);
        this.customer = customer;
        this.logger = logger;
        this.customers = customers;
    }

    @Override
    public void displayMenu() {
        System.out.println("\nWelcome " + customer.getName());
        System.out.println("1. Inquire Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Between Accounts");
        System.out.println("5. Pay Someone");
        System.out.println("6. Return to Main Menu");
        System.out.println("__________________");
    }

    @Override
    public boolean handleChoice(String choice) {
        try {
            switch (choice) {
                case "1":
                    handleBalanceInquiry(customer);
                    return true;
                case "2":
                    handleDeposit(customer);
                    return true;
                case "3":
                    handleWithdrawal(customer);
                    return true;
                case "4":
                    handleTransfer(customer);
                    return true;
                case "5":
                    handlePayment(customer);
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
     * Handles balance inquiry for all accounts owned by a customer.
     * Logs the inquiry transaction.
     * @param customer the customer whose balances are being inquired
     */
    private void handleBalanceInquiry(Customer customer) {
        List<Account> accounts = customer.inquireAllAccounts();
        System.out.println("\nYour account balances:");
        System.out.println("__________________");
        for (Account account : accounts) {
            System.out.printf("%s (%s): $%.2f%n", 
                account.getClass().getSimpleName(), 
                account.getAccountNumber(), 
                account.getBalance()
            );
            System.out.println("__________________");
            logger.logTransaction(
                String.format("%s made a balance inquiry on %s. Balance: $%.2f",
                    customer.getName(),
                    account.getAccountNumber(),
                    account.getBalance())
            );
        }
    }

    /**
     * Handles deposit operation for a selected account.
     * Prompts for account selection and amount, then processes the deposit.
     * @param customer the customer making the deposit
     */
    private void handleDeposit(Customer customer) {
        List<Account> accounts = customer.inquireAllAccounts();
        System.out.println("\nSelect account for deposit:");
        System.out.println("__________________");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("%d. %s ($%.2f)%n", 
                i + 1, 
                accounts.get(i).getAccountNumber(), 
                accounts.get(i).getBalance()
            );
        }
        try {
            System.out.print("Enter choice (1-" + accounts.size() + "): ");
            int accountChoice = Integer.parseInt(getInput()) - 1;
            
            if (accountChoice >= 0 && accountChoice < accounts.size()) {
                System.out.println("Enter amount to deposit:");
                System.out.println("__________________");
                double amount = Double.parseDouble(getInput());
                
                Account selectedAccount = accounts.get(accountChoice);
                selectedAccount.deposit(amount);
                
                System.out.printf("Successfully deposited $%.2f%n", amount);
                System.out.println("__________________");
                logger.logTransaction(String.format("%s deposited $%.2f to %s", 
                    customer.getName(), amount, selectedAccount.getAccountNumber()));
            } else {
                System.out.println("Invalid account selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles withdrawal operation for a selected account.
     * Prompts for account selection and amount, then processes the withdrawal.
     * @param customer the customer making the withdrawal
     */
    private void handleWithdrawal(Customer customer) {
        List<Account> accounts = customer.inquireAllAccounts();
        System.out.println("\nSelect account for withdrawal:");
        System.out.println("__________________");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("%d. %s ($%.2f)%n", 
                i + 1, 
                accounts.get(i).getAccountNumber(), 
                accounts.get(i).getBalance()
            );
        }

        try {
            System.out.print("Enter choice (1-" + accounts.size() + "): ");
            System.out.println("__________________");
            int accountChoice = Integer.parseInt(getInput()) - 1;
            
            if (accountChoice >= 0 && accountChoice < accounts.size()) {
                System.out.println("Enter amount to withdraw:");
                System.out.println("__________________");
                double amount = Double.parseDouble(getInput());
                
                Account selectedAccount = accounts.get(accountChoice);
                selectedAccount.withdraw(amount);
                
                System.out.printf("Successfully withdrew $%.2f%n", amount);
                logger.logTransaction(String.format("%s withdrew $%.2f from %s", 
                    customer.getName(), amount, selectedAccount.getAccountNumber()));
            } else {
                System.out.println("Invalid account selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles transfer between accounts owned by the same customer.
     * Asks for source and destination accounts and amount, then processes the transfer.
     * @param customer the customer making the transfer
     */
    private void handleTransfer(Customer customer) {
        List<Account> accounts = customer.inquireAllAccounts();
        
        // Display accounts
        System.out.println("\nYour accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("%d. %s ($%.2f)%n", 
                i + 1, 
                accounts.get(i).getAccountNumber(), 
                accounts.get(i).getBalance()
            );
        }

        try {
            // Get source account
            System.out.print("Enter source account number (1-" + accounts.size() + "): ");
            int fromAccount = Integer.parseInt(getInput()) - 1;
            
            // Get destination account
            System.out.print("Enter destination account number (1-" + accounts.size() + "): ");
            int toAccount = Integer.parseInt(getInput()) - 1;
            
            if (fromAccount == toAccount) {
                System.out.println("Cannot transfer to same account.");
                return;
            }

            if (fromAccount >= 0 && fromAccount < accounts.size() && 
                toAccount >= 0 && toAccount < accounts.size()) {
                    
                System.out.println("Enter amount to transfer:");
                double amount = Double.parseDouble(getInput());
                
                Account source = accounts.get(fromAccount);
                Account destination = accounts.get(toAccount);
                
                source.withdraw(amount);
                destination.deposit(amount);
                
                System.out.printf("Successfully transferred $%.2f%n", amount);
                logger.logTransaction(String.format("%s transferred $%.2f from %s to %s", 
                    customer.getName(), amount, source.getAccountNumber(), 
                    destination.getAccountNumber()));
            } else {
                System.out.println("Invalid account selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles payment to another customer.
     * Prompts for recipient, source and destination accounts, and amount, then processes the payment.
     * @param customer the customer making the payment
     */
    private void handlePayment(Customer customer) {
        try {
            // Get recipient
            System.out.println("Enter recipient's name:");
            String recipientName = getInput();
            Customer recipient = customers.get(recipientName);
            
            if (recipient == null) {
                System.out.println("Recipient not found.");
                return;
            }

            // Show payer's accounts
            List<Account> payerAccounts = customer.inquireAllAccounts();
            System.out.println("\nYour accounts:");
            for (int i = 0; i < payerAccounts.size(); i++) {
                System.out.printf("%d. %s ($%.2f)%n", 
                    i + 1, 
                    payerAccounts.get(i).getAccountNumber(), 
                    payerAccounts.get(i).getBalance()
                );
            }

            // Get source account
            System.out.print("Select your account (1-" + payerAccounts.size() + "): ");
            int fromAccount = Integer.parseInt(getInput()) - 1;

            // Show recipient's accounts
            List<Account> recipientAccounts = recipient.inquireAllAccounts();
            System.out.println("\nRecipient's accounts:");
            for (int i = 0; i < recipientAccounts.size(); i++) {
                System.out.printf("%d. %s%n", 
                    i + 1, 
                    recipientAccounts.get(i).getAccountNumber()
                );
            }

            // Get destination account
            System.out.print("Select recipient's account (1-" + recipientAccounts.size() + "): ");
            int toAccount = Integer.parseInt(getInput()) - 1;

            if (fromAccount >= 0 && fromAccount < payerAccounts.size() && 
                toAccount >= 0 && toAccount < recipientAccounts.size()) {
                
                System.out.println("Enter amount to pay:");
                double amount = Double.parseDouble(getInput());
                
                customer.pay(recipient, 
                           payerAccounts.get(fromAccount), 
                           recipientAccounts.get(toAccount), 
                           amount);
                
                System.out.printf("Successfully paid $%.2f to %s%n", amount, recipientName);
                logger.logTransaction(String.format("%s paid %s $%.2f", 
                    customer.getName(), recipientName, amount));
            } else {
                System.out.println("Invalid account selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}