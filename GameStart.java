import java.util.*;

/*
*
*
*
*/
public class GameStart {
    public static void main(String[] args) {
        // Scanner to Read Input, this logic can be moved to a function in the Deadwood
        // Class
        Scanner scanner = new Scanner(System.in);
        System.out.println("Testing Game Start");
        Integer numPlayers = null;
        while (numPlayers == null || numPlayers < 2 || numPlayers > 8) {
            System.out.println("Please Enter Number of Players");
            if (scanner.hasNextInt()) {
                numPlayers = scanner.nextInt();
                // Read Next Line
                scanner.nextLine();
            } else {
                String invalidInput = scanner.nextLine();
                System.err.println("That is not a valid number of Players");
            }
        }
        System.out.println("Starting Game With: " + numPlayers + " Players");
        // Close Scanner
        scanner.close();
    }
}