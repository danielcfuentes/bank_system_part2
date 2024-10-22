/**
 * Represents a savings account with the ability to transfer funds to another account.
 */
public class Savings extends Account {

    /**
     * Initializes a Savings account with an account number and balance.
     * 
     * @param accountNumber the account identifier
     * @param balance the initial balance
     */
    public Savings(String accountNUmber, double balance){
        super(accountNUmber, balance);
    }

    /**
     * Transfers a specified amount to another account.
     * 
     * @param to the target account
     * @param amount the amount to transfer
     * @throws IllegalArgumentException if the transfer amount is invalid or insufficient funds
     */
    public void tansfer(Account to, double amount){
        if ( amount > 0 && balance >= amount){
            this.withdraw(amount);
            to.deposit(amount);
            // need to log transaction
        }else{
            throw new IllegalArgumentException("Not Valid Transfer Amount");
        }
    }    
}
