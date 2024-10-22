/**
 * Represents a credit account where the balance is negative, with a set credit limit.
 */
public class Credit extends Account {

    /**
     * Initializes a Credit account with an account number, balance, and credit limit.
     * 
     * @param accountNumber the account identifier
     * @param balance the initial balance (negative value for credit)
     * @param creditLimit the maximum credit limit
     */
    private double creditLimit;
    private double principle;

    public Credit(String accountNumber, double balance, double creditLimit){
        super(accountNumber, balance);
        this.creditLimit = creditLimit;
        this.principle = 0;
    }

    public void setCreditLimit(double creditLimit){
        this.creditLimit = creditLimit;
    }

    public void setPrinciple(double principle){
        this.principle = principle;
    }

    public double getCreditLimit(){
        return creditLimit;
    }

    public double getPrinciple(){
        return principle;
    }

    /**
     * Borrows a specified amount, increasing the balance and principle.
     * 
     * @param amount the amount to borrow
     * @throws IllegalArgumentException if the amount is invalid or exceeds the credit limit
     */
    public void borrow(double amount){
        if ((amount > 0) && ((Math.abs(balance) + amount) <= creditLimit)) {
            balance -= amount;
            principle += amount;
            //log tanscation
        }else{
            throw new IllegalArgumentException("Not Valid Amount or Would Exceed Credit Limit");
        }
    }

    /**
     * Makes a payment towards the credit balance, reducing the principle.
     * 
     * @param amount the amount to pay
     * @throws IllegalArgumentException if the amount is invalid
     */
    public void pay(double amount){
        if (amount > 0){
            balance += amount;
            principle -= amount;
            //log
        }else{
            throw new IllegalArgumentException("Invalid Payment Amount");
        }
    }
}
