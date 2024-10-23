import java.util.Scanner;

/**
 * Start point for running the bank application.
 */
public class RunBank {

    /**
     * Main method to start the bank system.
     * 
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.println("\n=== Banking Menu ===");
            System.out.println("1. Inquire about a balance.");
            System.out.println("2. Make Deposit");
            System.out.println("3. Make Withdrawal");
            System.out.println("4. Transfer Money");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            try {
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        continue;
                    case 2:
                        continue;
                    case 3:
                        continue;
                    case 4:
                        continue;
                    case 5:
                        running = false;
                        System.out.println("Thank you for using our Bank System:)");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5!");

                }

            } catch (Exception e) {

            }
        }
    }

    /**
     * Checks the role of the specified user.
     * 
     * @param person the person whose role is to be checked
     * @return the role of the user, or null if not found
     */
    public String checkUserRole(Person person) {
        // check user role
        return null;
    }
}