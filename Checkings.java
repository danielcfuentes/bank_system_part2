public class Checkings extends Account{

    public Checkings(String accountNumber, double balance){
        super(accountNumber, balance);
    }


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
