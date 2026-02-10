import java.util.*;

/*
* TODO COMMENTS
*
*
*/
public class GameManager {
    private Player current_Player;
    private int players;

    // Initialize the number of players for the game
    public void initializePlayers(Scanner scanner) {
        System.out.println("Testing Game Start");
        Integer numPlayers = null;
        while (numPlayers == null || numPlayers < 2 || numPlayers > 8) {
            System.out.println("Please Enter Number of Players");
            if (scanner.hasNextInt()) {
                numPlayers = scanner.nextInt();
                // Read Next Line
                scanner.nextLine();
                if (numPlayers < 2 || numPlayers > 8) {
                    System.err.println("That is not a valid number of Players (2-8 Players)");
                }
            } else {
                String invalidInput = scanner.nextLine();
                System.err.println("That is not a valid number of Players");
            }
        }
        setNumberofPlayers(numPlayers);
        System.out.println("Starting Game With: " + numPlayers + " Players");
    }

    // Getters
    public Player getCurrent_Player() {
        return current_Player;
    }

    public int getPlayers() {
        return players;
    }

    // Setters
    public void setCurrent_Player(Player current_Player) {
        this.current_Player = current_Player;
    }

    public void setNumberofPlayers(Integer Players) {
        this.players = Players;
    }
}