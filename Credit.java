/**
 * Represents a credit account where the balance is negative, with a set credit limit.
 * @author Daniel Fuentes, Rogelio Lozano
 * @version 1.0
 */
public class Credit extends Account {
    /** Maximum amount that can be borrowed */
    private double creditLimit;
    /** Current amount borrowed */
    private double principle;
    
    /**
     * Initializes a Credit account with an account number, balance, and credit limit.
     * @param accountNumber the account identifier
     * @param balance the initial balance (negative value for credit)
     * @param creditLimit the maximum credit limit
     */
    public Credit(String accountNumber, double balance, double creditLimit){
        super(accountNumber, balance);
        this.creditLimit = creditLimit;
        this.principle = 0;
    }

    /**
     * Sets the credit limit.
     * @param creditLimit new credit limit
     */
    public void setCreditLimit(double creditLimit){
        this.creditLimit = creditLimit;
    }

    /**
     * Sets the principle amount.
     * @param principle new principle amount
     */
    public void setPrinciple(double principle){
        this.principle = principle;
    }

    /**
     * Gets the credit limit.
     * @return current credit limit
     */
    public double getCreditLimit(){
        return creditLimit;
    }

    /**
     * Gets the principle amount.
     * @return current principle
     */
    public double getPrinciple(){
        return principle;
    }

    /**
     * Borrows a specified amount, increasing the balance and principle.
     * Checks if the amount is invalid or exceeds the credit limit
     * @param amount the amount to borrow
     * 
     */
    public void borrow(double amount){
        if ((amount > 0) && ((Math.abs(balance) + amount) <= creditLimit)) {
            balance -= amount;
            principle += amount;
        }else{
            throw new IllegalArgumentException("Not Valid Amount or Would Exceed Credit Limit");
        }
    }

    /**
     * Makes a payment towards the credit balance, reducing the principle.
     * @param amount the amount to pay
     */
    public void pay(double amount){
        if (amount > 0){
            balance += amount;
            principle -= amount;
        }else{
            throw new IllegalArgumentException("Invalid Payment Amount");
        }
    }
}
