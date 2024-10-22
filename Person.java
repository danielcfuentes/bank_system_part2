import java.util.ArrayList;
import java.util.List;

public abstract class Person {
    private String name;
    private List<Account> accounts;
    private String role;

    // constrcutor
    public Person(String name, String role) {
        this.name = name;
        this.role = role;
        this.accounts = new ArrayList<>();
    }

    public Person(){
        this.accounts = new ArrayList<>();
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // method to check any account based on account number
    public abstract List<Account> inquireAnyAccount(String accountNumber);

    // method to pay another person
    public void pay(Person receiver, Account fromAccount,Account toAccount, double amount) {
        if(amount <= 0){
            throw new IllegalArgumentException("Not valid amount");
        }
        if (!accounts.contains(fromAccount)){
            throw new IllegalArgumentException("From Account does not belong to this person");
        }
        if(!receiver.getAccounts().contains(toAccount)){
            throw new IllegalArgumentException("Destination account does not belong to the reciever's person");
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        //need to log
    }
}