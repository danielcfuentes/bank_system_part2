public class Savings extends Account {
    public Savings(String accountNUmber, double balance){
        super(accountNUmber, balance);
    }

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
