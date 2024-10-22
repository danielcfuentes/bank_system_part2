public class Savings extends Account {
    private double interestRate;

    public Savings(){}

    public void setInterestRate(double interestRate){
        this.interestRate = interestRate;
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
