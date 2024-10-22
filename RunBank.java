import java.util.Scanner;

public class RunBank {

    public static Scanner scanner = new Scanner(System.in); 
    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to El Paso Miners Bank");
            System.out.println("1. Individual Customer");
            System.out.println("2. Bank Manager");
            System.out.println("Type 'EXIT' to quit");
            
            String choice = scanner.nextLine();
            
            if (choice.equalsIgnoreCase("EXIT")) {
                // saveAndExit();
                break;
            }
            
            switch (choice) {
                case "1":
                    break;
                case "2":
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}