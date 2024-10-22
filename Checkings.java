public class Checkings extends Account{
    private double overdraftLimit;

    public Checkings(){}

    public void setOverdraftLimit(double overdraftLimit){
        this.overdraftLimit = overdraftLimit;
    }

    public void tansfer(Account to, double amount){
        if ( amount > 0 && (balance - amount) >= overdraftLimit){
            this.withdraw(amount);
            to.deposit(amount);
            // need to log transaction
        }else{
            throw new IllegalArgumentException("Not Valid Transfer Amount");
        }
    }
}
