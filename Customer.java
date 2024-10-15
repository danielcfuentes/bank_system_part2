import java.util.List;
public class Customer extends Person {
    private String customerID;

    public Customer(){}

    //getters and setters
    public String getCustomerID(){
        return customerID;
    }

    public void setCustomerID(String customerID){
        this.customerID = customerID;
    }

    public List<Account> inquireAllAccounts(){}

}
