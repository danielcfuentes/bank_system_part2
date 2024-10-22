/**
 * Represents a checking account with the ability to transfer funds.
 */
public class Checkings extends Account{

    /**
     * Initializes a Checkings account with the given account number and balance.
     * 
     * @param accountNumber the account identifier
     * @param balance the initial balance
     */
    public Checkings(String accountNumber, double balance){
        super(accountNumber, balance);
    }
    
    /**
     * Transfers a specified amount to another account.
     * 
     * @param to the target account
     * @param amount the amount to transfer
     * @throws IllegalArgumentException if the amount is invalid
     */
    public void tansfer(Account to, double amount){
        if ( amount > 0 && amount <= balance){
            this.withdraw(amount);
            to.deposit(amount);
            // need to log transaction
        }else{
            throw new IllegalArgumentException("Not Valid Transfer Amount");
        }
    }
}
