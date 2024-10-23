public class Customer extends Person {
    // Unique identifier for customer
    private String customerID;
    
    // Constructor with name and ID
    public Customer(String name, String customerID) {
        // Call parent constructor with name
        super(name);
        this.customerID = customerID;
    }
    
    // Getter for customer ID
    public String getCustomerID() {
        return customerID;
    }
    
    // Setter for customer ID
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}