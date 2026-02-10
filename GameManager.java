import java.util.*;

/*
*
*
*
*/
public class GameManager {
    private ArrayList<Player> players = new ArrayList<>();
    private Player current_Player;
    private Map<String, Set> board = new HashMap<>(); // Using map for adjacent moves

    public void initializePlayers(Scanner scanner){
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
    }

    public void GameStart(int numPlayers) {
        initializeBoard();
        initializePlayers(numPlayers);
        // set number of players
    }

    private void initializeBoard() {
        // players only move to adjacent rooms
        board.put("Train Station", new Set("Train Station", Arrays.asList("Jail", "Main Street", "Casting Office")));
        board.put("Jail", new Set("Jail", Arrays.asList("Train Station", "Main Street")));
        board.put("Main Street", new Set("Main Street", Arrays.asList("Train Station", "Jail", "Trailer")));
    }

    private void initializePlayers(int num) {
        for (int i = 0; i < num; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
        current_Player = players.get(0);
    }

    // Getters
    public Map<String, Set> getBoard() {
        return board;
    }

    public Player getCurrent_Player() {
        return current_Player;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    // Setters
    public void setBoard(Map<String, Set> board) {
        this.board = board;
    }

    public void setCurrent_Player(Player current_Player) {
        this.current_Player = current_Player;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
   
    
}