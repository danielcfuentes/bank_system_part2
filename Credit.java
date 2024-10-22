public class Credit extends Account {
    //in here we are saying that the balance is negative
    
    private double creditLimit;
    private double principle;

    public Credit(){}

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

    public void borrow(double amount){
        if ((amount > 0) && ((Math.abs(balance) + amount) <= creditLimit)) {
            balance -= amount;
            principle += amount;
            //log tanscation
        }else{
            throw new IllegalArgumentException("Not Valid Amount or Would Exceed Credit Limit");
        }
    }

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
