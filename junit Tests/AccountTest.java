import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
    private Checking checkingAccount;
    private Savings savingsAccount;
    private Credit creditAccount;
    
    @BeforeEach
    void setUp() {
        checkingAccount = new Checking("CHK001", 1000.0);
        savingsAccount = new Savings("SAV001", 2000.0);
        creditAccount = new Credit("CRD001", 0.0, 5000.0);
    }
    
    @AfterEach
    void tearDown() {
        checkingAccount = null;
        savingsAccount = null;
        creditAccount = null;
    }
    
    @Test
    void testValidDeposit() {
        checkingAccount.deposit(500.0);
        assertEquals(1500.0, checkingAccount.getBalance(), 0.001);
    }
    
    @Test
    void testZeroDeposit() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            checkingAccount.deposit(0.0));
        assertEquals("Not Valid Amount: Deposit amount must be positive", 
                    exception.getMessage());
    }
    
    @Test
    void testNegativeDeposit() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            checkingAccount.deposit(-100.0));
        assertEquals("Not Valid Amount: Deposit amount must be positive", 
                    exception.getMessage());
    }

}