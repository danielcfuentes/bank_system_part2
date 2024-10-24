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
        
        while (true) {
            System.out.println("\nWelcome " + name);
            System.out.println("1. Inquire Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Between Accounts");
            System.out.println("5. Pay Someone");
            System.out.println("6. Return to Main Menu");
            
            String choice = scanner.nextLine();
            if (choice.equals("6")) break;
            
            handleCustomerChoice(choice, customer);
        }
    }

    /**
     * Handles bank manager operations.
     * Provides interface for account inquiries by name or account number.
     */
    public void handleBankManager() {
        System.out.println("\nA. Inquire account by name");
        System.out.println("B. Inquire account by account number");
        System.out.println("__________________");
        
        String choice = scanner.nextLine().toUpperCase();
        
        switch (choice) {
            case "A":
                System.out.println("Enter first name:");
                System.out.println("__________________");
                String firstName = scanner.nextLine();
                System.out.println("Enter last name:");
                System.out.println("__________________");
                String lastName = scanner.nextLine();
                handleManagerNameInquiry(firstName + " " + lastName);
                break;
            case "B":
                System.out.println("Enter account number:");
                System.out.println("__________________");
                String accountNum = scanner.nextLine();
                handleManagerAccountInquiry(accountNum);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /**
     * Processes customer menu choices and directs to appropriate handling method.
     * @param choice the menu option selected by the customer
     * @param customer the customer making the request
     */
    private void handleCustomerChoice(String choice, Customer customer) {
        try {
            switch (choice) {
                case "1":
                    handleBalanceInquiry(customer);
                    break;
                case "2":
                    handleDeposit(customer);
                    break;
                case "3":
                    handleWithdrawal(customer);
                    break;
                case "4":
                    handleTransfer(customer);
                    break;
                case "5":
                    handlePayment(customer);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
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
            int accountChoice = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (accountChoice >= 0 && accountChoice < accounts.size()) {
                System.out.println("Enter amount to deposit:");
                System.out.println("__________________");
                double amount = Double.parseDouble(scanner.nextLine());
                
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
            int accountChoice = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (accountChoice >= 0 && accountChoice < accounts.size()) {
                System.out.println("Enter amount to withdraw:");
                System.out.println("__________________");
                double amount = Double.parseDouble(scanner.nextLine());
                
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
     * Asks for source and destination accounts and amount, then process the transfer.
     * @param customer the customer making the transfer
     */
    private void handleTransfer(Customer customer) {
        List<Account> accounts = customer.inquireAllAccounts();
        
        //display accounts of customer
        System.out.println("\nYour accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("%d. %s ($%.2f)%n", 
                i + 1, 
                accounts.get(i).getAccountNumber(), 
                accounts.get(i).getBalance()
            );
        }

        try {
            //get account we are remvong from
            System.out.print("Enter source account number (1-" + accounts.size() + "): ");
            int fromAccount = Integer.parseInt(scanner.nextLine()) - 1;
            
            //get the account we are adding money to
            System.out.print("Enter destination account number (1-" + accounts.size() + "): ");
            int toAccount = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (fromAccount == toAccount) {
                System.out.println("Cannot transfer to same account.");
                return;
            }

            if (fromAccount >= 0 && fromAccount < accounts.size() && 
                toAccount >= 0 && toAccount < accounts.size()) {
                    
                System.out.println("Enter amount to transfer:");
                double amount = Double.parseDouble(scanner.nextLine());
                
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
     * Handles payment to another customer's account.
     * Prompts for recipient, source and destination accounts, and amount, then processes the payment.
     * It checks and vlaidates that accounts chosen are correct inputs and verfies that account exites for that person
     * @param customer the customer making the payment
     */
    private void handlePayment(Customer customer) {
        try {
            //get recipient
            System.out.println("Enter recipient's name:");
            String recipientName = scanner.nextLine();
            Customer recipient = customers.get(recipientName);
            
            if (recipient == null) {
                System.out.println("Recipient not found.");
                return;
            }

            //show payer's accounts
            List<Account> payerAccounts = customer.inquireAllAccounts();
            System.out.println("\nYour accounts:");
            for (int i = 0; i < payerAccounts.size(); i++) {
                System.out.printf("%d. %s ($%.2f)%n", 
                    i + 1, 
                    payerAccounts.get(i).getAccountNumber(), 
                    payerAccounts.get(i).getBalance()
                );
            }

            //get source account
            System.out.print("Select your account (1-" + payerAccounts.size() + "): ");
            int fromAccount = Integer.parseInt(scanner.nextLine()) - 1;

            //show recipient's accounts
            List<Account> recipientAccounts = recipient.inquireAllAccounts();
            System.out.println("\nRecipient's accounts:");
            for (int i = 0; i < recipientAccounts.size(); i++) {
                System.out.printf("%d. %s%n", 
                    i + 1, 
                    recipientAccounts.get(i).getAccountNumber()
                );
            }

            //get destination account
            System.out.print("Select recipient's account (1-" + recipientAccounts.size() + "): ");
            int toAccount = Integer.parseInt(scanner.nextLine()) - 1;

            if (fromAccount >= 0 && fromAccount < payerAccounts.size() && 
                toAccount >= 0 && toAccount < recipientAccounts.size()) {
                
                System.out.println("Enter amount to pay:");
                double amount = Double.parseDouble(scanner.nextLine());
                
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

    /**
     * Handles bank manager inquiry of customer accounts by customer name.
     * @param fullName the full name of the customer to look up
     */
    private void handleManagerNameInquiry(String fullName) {
        Customer customer = customers.get(fullName);
        if (customer != null) {
            handleBalanceInquiry(customer);
        } else {
            System.out.println("Customer not found.");
        }
    }

    /**
     * Handles bank manager inquiry of customer accounts by account number.
     * @param accountNumber the account number to look up
     */
    private void handleManagerAccountInquiry(String accountNumber) {
        for (Customer customer : customers.values()) {
            for (Account account : customer.getAccounts()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    System.out.printf("%s's %s (%s): $%.2f%n",
                        customer.getName(),
                        account.getClass().getSimpleName(),
                        account.getAccountNumber(),
                        account.getBalance()
                    );
                    return;
                }
            }
        }
        System.out.println("Account not found.");
    }
}