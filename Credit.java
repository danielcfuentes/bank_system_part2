public class Credit extends Account {
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
        
    }

    public void pay(double amount){

    }
}
