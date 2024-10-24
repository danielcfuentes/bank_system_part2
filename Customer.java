/**
 * Represents a bank customer that extends the Person class.
 * Contains customer-specific information and functionality.
 * @author Daniel Fuentes, Rogelio Lozano
 * @version 1.0
 */
public class Customer extends Person {
    /** Unique identifier for each customer */
    private String customerID;
    
    /**
     * Creates a new customer.
     * @param name the customer's full name
     * @param customerID unique id for the customer
     */
    public Customer(String name, String customerID) {
        //call parent constructor with name
        super(name);
        this.customerID = customerID;
    }
    
    /**
     * Gets the customer's id.
     * @return the customer's unique id
     */
    public String getCustomerID() {
        return customerID;
    }
    
    /**
     * Sets the customer's ID.
     * @param customerID new unique id for the customer
     */
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}