/**
 * Interface for menu operations in the banking system.
 * Provides standard methods for displaying menus and handling user choices.
 * @author Daniel Fuentes, Rogelio Lozano
 * @version 2.0
 */
public interface Menu {
    /**
     * Displays the menu options to the user.
     */
    void displayMenu();
    
    /**
     * Handles the user's menu choice.
     * @param choice the user's selected option
     * @return true if the choice was handled successfully, false otherwise
     */
    boolean handleChoice(String choice);
    
    /**
     * Gets input from the user.
     * @return the user's input as a string
     */
    String getInput();
}