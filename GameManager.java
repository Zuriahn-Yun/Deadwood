import java.util.*;

/*
* TODO COMMENTS
* TODO TRANSACTION MANAGER LOCATION MANAGER, WHILE THIS MANAGES PLAYERS AND WHOS TURN
*
*/
public class GameManager {
    private Player current_Player;
    private int NumberOfPlayers;
    // Hold our Player Objects
    List<Player> players = new ArrayList<>();

    // Initialize the number of players for the game
    public void initializeNumberOfPlayers(Scanner scanner) {
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

    // Initialize Player Objects with names and Ids;
    public void initializePlayers(Scanner scanner) {
        for (int i = 0; i < getNumberOfPlayers(); i++) {
            // Create a player Object
            Player player = new Player();
            // TODO player.getname()
            // Set PlayerId
            player.setPlayerID(i);
            // Add to Player List
            players.add(player);
        }
    }

    // Getters
    public Player getCurrent_Player() {
        return current_Player;
    }

    public int getNumberOfPlayers() {
        return NumberOfPlayers;
    }

    // Setters
    public void setCurrent_Player(Player current_Player) {
        this.current_Player = current_Player;
    }

    public void setNumberofPlayers(Integer Players) {
        this.NumberOfPlayers = Players;
    }
}