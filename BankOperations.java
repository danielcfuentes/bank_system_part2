import java.util.*;

public class BankOperations {
    private Map<String, Customer> customers;
    private Scanner scanner;
    private TransactionLog logger;

    public BankOperations(Map<String, Customer> customers, TransactionLog logger) {
        this.customers = customers;
        this.scanner = new Scanner(System.in);
        this.logger = logger;
    }

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

    public void handleBankManager() {
        System.out.println("\nA. Inquire account by name");
        System.out.println("B. Inquire account by account number");
        
        String choice = scanner.nextLine().toUpperCase();
        
        switch (choice) {
            case "A":
                System.out.println("Enter first name:");
                String firstName = scanner.nextLine();
                System.out.println("Enter last name:");
                String lastName = scanner.nextLine();
                handleManagerNameInquiry(firstName + " " + lastName);
                break;
            case "B":
                System.out.println("Enter account number:");
                String accountNum = scanner.nextLine();
                handleManagerAccountInquiry(accountNum);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

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

    private void handleBalanceInquiry(Customer customer) {
        List<Account> accounts = customer.inquireAllAccounts();
        System.out.println("\nYour account balances:");
        for (Account account : accounts) {
            System.out.printf("%s (%s): $%.2f%n", 
                account.getClass().getSimpleName(), 
                account.getAccountNumber(), 
                account.getBalance()
            );
            logger.logTransaction(
                String.format("%s made a balance inquiry on %s. Balance: $%.2f",
                    customer.getName(),
                    account.getAccountNumber(),
                    account.getBalance())
            );
        }
    }

    private void handleDeposit(Customer customer) {
        List<Account> accounts = customer.inquireAllAccounts();
        System.out.println("\nSelect account for deposit:");
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
                double amount = Double.parseDouble(scanner.nextLine());
                
                Account selectedAccount = accounts.get(accountChoice);
                selectedAccount.deposit(amount);
                
                System.out.printf("Successfully deposited $%.2f%n", amount);
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

    private void handleWithdrawal(Customer customer) {
        List<Account> accounts = customer.inquireAllAccounts();
        System.out.println("\nSelect account for withdrawal:");
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
                System.out.println("Enter amount to withdraw:");
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
            int fromAccount = Integer.parseInt(scanner.nextLine()) - 1;
            
            // Get destination account
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

    private void handlePayment(Customer customer) {
        try {
            // Get recipient
            System.out.println("Enter recipient's name:");
            String recipientName = scanner.nextLine();
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
            int fromAccount = Integer.parseInt(scanner.nextLine()) - 1;

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

    private void handleManagerNameInquiry(String fullName) {
        Customer customer = customers.get(fullName);
        if (customer != null) {
            handleBalanceInquiry(customer);
        } else {
            System.out.println("Customer not found.");
        }
    }

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