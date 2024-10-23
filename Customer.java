// Customer class - Represents a bank customer, extends Person
public class Customer extends Person {
    // Unique identifier for the customer
    private String customerID;
    
    // Default constructor
    public Customer() {
        // Call parent (Person) constructor with no arguments
        super();
    }
    
    // Constructor with name and ID
    public Customer(String name, String customerID) {
        // Call parent constructor with name and "Customer" role
        super(name, "Customer");
        // Set the customer's ID
        this.customerID = customerID;
    }
    
    // Get customer's ID
    public String getCustomerID() {
        return customerID;
    }
    
    // Set customer's ID
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

}