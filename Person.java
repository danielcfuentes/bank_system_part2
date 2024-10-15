import java.util.List;

public abstract class Person {
    private String name;
    private List<Account> accounts;
    private String role;

    // constrcutor
    public Person(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public Person(){}

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
    public void pay(Person receiver, double amount) {}
}