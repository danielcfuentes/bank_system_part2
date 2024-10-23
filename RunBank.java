// Add these imports at the very top of RunBank.java
import java.io.*;
import java.nio.file.*;
import java.util.*;
public class RunBank {
    // Store all customers with their names as keys
    private static Map<String, Customer> customers = new HashMap<>();
    
    // Transaction logger instance
    // private static TransactionLog transactionLog = new TransactionLog();
    
    // Scanner for user input
    private static Scanner scanner = new Scanner(System.in);
    
    // CSV file paths
    private static final String INPUT_CSV = "CS 3331 - Bank Users.csv";
    private static final String OUTPUT_CSV = "updated_bank_users.csv";

    // Main method - entry point
    public static void main(String[] args) {
        try {
            // Load customer data when program starts
            loadCustomerData();
            System.out.println(customers);
            
            // Main program loop
            while (true) {
                // Display main menu
                System.out.println("\nWelcome to El Paso Miners Bank");
                System.out.println("1. Individual Customer");
                System.out.println("2. Bank Manager");
                System.out.println("Type 'EXIT' to quit");
                
                String choice = scanner.nextLine();
                
                // Check for exit condition
                if (choice.equalsIgnoreCase("EXIT")) {
                    saveAndExit();
                    break;
                }
                
                // Process user choice
                switch (choice) {
                    case "1":
                        handleCustomer();
                        break;
                    case "2":
                        handleBankManager();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void loadCustomerData() {
        try {
            // Read all lines from CSV
            List<String> lines = Files.readAllLines(Paths.get("CS 3331 - Bank Users.csv"));
            
            // Skip header
            for (int i = 1; i < lines.size(); i++) {
                try {
                    // Split line while preserving commas in quoted values
                    String[] data = lines.get(i).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                    
                    // Remove any quotes from values
                    for (int j = 0; j < data.length; j++) {
                        data[j] = data[j].replace("\"", "").trim();
                    }
                    
                    createCustomer(data);
                } catch (Exception e) {
                    System.out.println("Error on line " + i + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }
    
    private static void createCustomer(String[] data) {
        try {
            // Create full name
            String fullName = data[1] + " " + data[2];
            
            // Create new customer
            Customer customer = new Customer(fullName, data[0]);
            
            // Create accounts list
            List<Account> accounts = new ArrayList<>();
            
            // Try-catch block for each account creation
            try {
                // Create checking account
                double checkingBalance = Double.parseDouble(data[7].trim());
                Checkings checking = new Checkings(data[6], checkingBalance);
                accounts.add(checking);
                
                // Create savings account
                double savingsBalance = Double.parseDouble(data[9].trim());
                Savings savings = new Savings(data[8], savingsBalance);
                accounts.add(savings);
                
                // Create credit account
                double creditBalance = Double.parseDouble(data[12].trim());
                double creditLimit = Double.parseDouble(data[11].trim());
                Credit credit = new Credit(data[10], creditBalance, creditLimit);
                accounts.add(credit);
                
                // Set accounts and add customer to map
                customer.setAccounts(accounts);
                customers.put(fullName, customer);
                
            } catch (NumberFormatException e) {
                System.out.println("Error parsing balance for customer " + fullName + 
                                 ": " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error creating customer from data: " + e.getMessage());
        }
    }

    // Handle customer login and operations
    private static void handleCustomer() {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        
        Customer customer = customers.get(name);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        
        while (true) {
            // Display customer menu
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

    // Handle customer menu choices
    private static void handleCustomerChoice(String choice, Customer customer) {
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

    // Handle balance inquiry
    private static void handleBalanceInquiry(Customer customer) {
        List<Account> accounts = customer.inquireAllAccounts();
        System.out.println("\nYour account balances:");
        for (Account account : accounts) {
            String accountType = account.getClass().getSimpleName();
            System.out.printf("%s (%s): $%.2f%n", 
                accountType, 
                account.getAccountNumber(), 
                account.getBalance()
            );
            // transactionLog.logTransaction(
            //     customer.getName() + " made a balance inquiry on " + 
            //     account.getAccountNumber() + ". Balance: $" + 
            //     account.getBalance()
            // );
        }
    }

    // Handle deposit operation
    private static void handleDeposit(Customer customer) {
        // Show accounts
        List<Account> accounts = customer.inquireAllAccounts();
        System.out.println("\nSelect account for deposit:");
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            System.out.printf("%d. %s ($%.2f)%n", 
                i + 1, 
                account.getAccountNumber(), 
                account.getBalance()
            );
        }

        try {
            System.out.print("Enter choice (1-" + accounts.size() + "): ");
            int accountChoice = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (accountChoice < 0 || accountChoice >= accounts.size()) {
                System.out.println("Invalid account selection.");
                return;
            }

            System.out.println("Enter amount to deposit:");
            double amount = Double.parseDouble(scanner.nextLine());
            
            Account selectedAccount = accounts.get(accountChoice);
            selectedAccount.deposit(amount);
            
            System.out.printf("Successfully deposited $%.2f to account %s%n",
                amount, selectedAccount.getAccountNumber());
            
            // transactionLog.logTransaction(
            //     String.format("%s deposited $%.2f to %s. New balance: $%.2f",
            //         customer.getName(),
            //         amount,
            //         selectedAccount.getAccountNumber(),
            //         selectedAccount.getBalance())
            // );
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Handle withdrawal operation
    private static void handleWithdrawal(Customer customer) {
        // Show accounts
        List<Account> accounts = customer.inquireAllAccounts();
        System.out.println("\nSelect account for withdrawal:");
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            System.out.printf("%d. %s ($%.2f)%n", 
                i + 1, 
                account.getAccountNumber(), 
                account.getBalance()
            );
        }

        try {
            System.out.print("Enter choice (1-" + accounts.size() + "): ");
            int accountChoice = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (accountChoice < 0 || accountChoice >= accounts.size()) {
                System.out.println("Invalid account selection.");
                return;
            }

            System.out.println("Enter amount to withdraw:");
            double amount = Double.parseDouble(scanner.nextLine());
            
            Account selectedAccount = accounts.get(accountChoice);
            selectedAccount.withdraw(amount);
            
            System.out.printf("Successfully withdrew $%.2f from account %s%n",
                amount, selectedAccount.getAccountNumber());
            
            // transactionLog.logTransaction(
            //     String.format("%s withdrew $%.2f from %s. New balance: $%.2f",
            //         customer.getName(),
            //         amount,
            //         selectedAccount.getAccountNumber(),
            //         selectedAccount.getBalance())
            // );
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Handle transfer between accounts
    private static void handleTransfer(Customer customer) {
        List<Account> accounts = customer.inquireAllAccounts();
        
        // Show source accounts
        System.out.println("\nSelect source account:");
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            System.out.printf("%d. %s ($%.2f)%n", 
                i + 1, 
                account.getAccountNumber(), 
                account.getBalance()
            );
        }

        try {
            // Get source account
            System.out.print("Enter source account (1-" + accounts.size() + "): ");
            int fromChoice = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (fromChoice < 0 || fromChoice >= accounts.size()) {
                System.out.println("Invalid source account selection.");
                return;
            }

            // Show destination accounts
            System.out.println("\nSelect destination account:");
            for (int i = 0; i < accounts.size(); i++) {
                if (i != fromChoice) {
                    Account account = accounts.get(i);
                    System.out.printf("%d. %s ($%.2f)%n", 
                        i + 1, 
                        account.getAccountNumber(), 
                        account.getBalance()
                    );
                }
            }

            // Get destination account
            System.out.print("Enter destination account (1-" + accounts.size() + "): ");
            int toChoice = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (toChoice < 0 || toChoice >= accounts.size() || toChoice == fromChoice) {
                System.out.println("Invalid destination account selection.");
                return;
            }

            // Get transfer amount
            System.out.println("Enter amount to transfer:");
            double amount = Double.parseDouble(scanner.nextLine());
            
            // Process transfer
            Account fromAccount = accounts.get(fromChoice);
            Account toAccount = accounts.get(toChoice);
            
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            
            System.out.printf("Successfully transferred $%.2f from %s to %s%n",
                amount, 
                fromAccount.getAccountNumber(), 
                toAccount.getAccountNumber()
            );
            
            // transactionLog.logTransaction(
            //     String.format("%s transferred $%.2f from %s to %s. " +
            //         "Source balance: $%.2f. Destination balance: $%.2f",
            //         customer.getName(),
            //         amount,
            //         fromAccount.getAccountNumber(),
            //         toAccount.getAccountNumber(),
            //         fromAccount.getBalance(),
            //         toAccount.getBalance())
            // );
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Handle payment to another customer
    private static void handlePayment(Customer sender) {
        try {
            // Get recipient's name
            System.out.println("\nEnter recipient's name:");
            String recipientName = scanner.nextLine();
            
            Customer recipient = customers.get(recipientName);
            if (recipient == null) {
                System.out.println("Recipient not found.");
                return;
            }

            // Show sender's accounts
            List<Account> senderAccounts = sender.inquireAllAccounts();
            System.out.println("\nSelect your account to pay from:");
            for (int i = 0; i < senderAccounts.size(); i++) {
                Account account = senderAccounts.get(i);
                System.out.printf("%d. %s ($%.2f)%n", 
                    i + 1, 
                    account.getAccountNumber(), 
                    account.getBalance()
                );
            }

            // Get sender's account
            System.out.print("Enter choice (1-" + senderAccounts.size() + "): ");
            int fromChoice = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (fromChoice < 0 || fromChoice >= senderAccounts.size()) {
                System.out.println("Invalid account selection.");
                return;
            }

            // Show recipient's accounts
            List<Account> recipientAccounts = recipient.inquireAllAccounts();
            System.out.println("\nSelect recipient's account:");
            for (int i = 0; i < recipientAccounts.size(); i++) {
                Account account = recipientAccounts.get(i);
                System.out.printf("%d. %s%n", 
                    i + 1, 
                    account.getAccountNumber()
                );
            }

            // Get recipient's account
            System.out.print("Enter choice (1-" + recipientAccounts.size() + "): ");
            int toChoice = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (toChoice < 0 || toChoice >= recipientAccounts.size()) {
                System.out.println("Invalid account selection.");
                return;
            }

            // Get payment amount
            System.out.println("Enter amount to pay:");
            double amount = Double.parseDouble(scanner.nextLine());

            // Process payment
            Account fromAccount = senderAccounts.get(fromChoice);
            Account toAccount = recipientAccounts.get(toChoice);
            
            sender.pay(recipient, fromAccount, toAccount, amount);
            
            System.out.printf("Successfully paid $%.2f to %s%n",
                amount, recipientName);
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Handle bank manager operations
    private static void handleBankManager() {
        System.out.println("\nA. Inquire account by name");
        System.out.println("B. Inquire account by account number");
        
        String choice = scanner.nextLine().toUpperCase();
        
        switch (choice) {
            case "A":
                System.out.println("Enter first name:");
                String firstName = scanner.nextLine();
                System.out.println("Enter last name:");
                String lastName = scanner.nextLine();
                
                // Handle bank manager operations (continued)
                List<Account> accountsByName = Person.inquireAccount(firstName, lastName, customers);
                displayAccounts(accountsByName, firstName + " " + lastName);
                break;
                
            case "B":
                System.out.println("Enter account number:");
                String accountNum = scanner.nextLine();
                
                List<Account> accountsByNumber = Person.inquireAccount(accountNum, customers);
                displayAccounts(accountsByNumber, "Account " + accountNum);
                break;
                
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    // Helper method to display accounts
    private static void displayAccounts(List<Account> accounts, String identifier) {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for " + identifier);
            return;
        }
        
        System.out.println("\nAccounts for " + identifier + ":");
        for (Account account : accounts) {
            System.out.printf("%s (%s): $%.2f%n", 
                account.getClass().getSimpleName(),
                account.getAccountNumber(), 
                account.getBalance()
            );
        }
    }

    // Save data and exit
    private static void saveAndExit() {
        try {
            // Create list for CSV lines
            List<String> lines = new ArrayList<>();
            
            // Add CSV header
            lines.add("Identification Number,First Name,Last Name,Date of Birth,Address," +
                     "Phone Number,Checking Account Number,Checking Starting Balance," +
                     "Savings Account Number,Savings Starting Balance,Credit Account Number," +
                     "Credit Max,Credit Starting Balance");
            
            // Read original file to get unchanged data
            List<String> originalLines = Files.readAllLines(Paths.get(INPUT_CSV));
            Map<String, String[]> originalData = new HashMap<>();
            
            // Skip header and process original data
            for (int i = 1; i < originalLines.size(); i++) {
                String[] data = originalLines.get(i).split(",");
                String id = data[0];
                originalData.put(id, data);
            }
            
            // Create updated lines using original data and new balances
            for (Customer customer : customers.values()) {
                String[] originalInfo = originalData.get(customer.getCustomerID());
                if (originalInfo != null) {
                    List<Account> accounts = customer.getAccounts();
                    
                    // Update balances in original data
                    originalInfo[7] = String.valueOf(accounts.get(0).getBalance());  // Checking
                    originalInfo[9] = String.valueOf(accounts.get(1).getBalance());  // Savings
                    originalInfo[12] = String.valueOf(accounts.get(2).getBalance()); // Credit
                    
                    // Join data back into CSV line
                    lines.add(String.join(",", originalInfo));
                }
            }
            
            // Write updated data to new CSV file
            Files.write(Paths.get(OUTPUT_CSV), lines);
            
            // Update transaction log
            // transactionLog.exitUpdate();
            
            System.out.println("Data saved successfully!");
            System.out.println("Thank you for using El Paso Miners Bank!");
            
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}