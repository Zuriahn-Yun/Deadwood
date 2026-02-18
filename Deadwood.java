import java.util.*;

/**
*
* 
*/
public class Deadwood {
    public static void main(String[] args) {
        // Start the Game
        System.out.println("Game Starting");
        try {
            while (true) {
                // Initialize Board
                Board board = new Board();
                GameManager gameManager = new GameManager();
                // Initialize Player Count
                gameManager.initializeNumberOfPlayers();
                // Initialize Player Objects
                gameManager.initializePlayers(board.getTrailer());
                // Create locationManager
                LocationManager locationManager = new LocationManager(gameManager.getPlayers());
                UserInput.setLocationManager(locationManager);
                UserInput.setGameManager(gameManager);
                for (int i = 1; i < gameManager.getTotalDays() + 1; i++) {
                    System.out.println("Start Day: " + i );
                    for (Player player : gameManager.getPlayers()) {
                        System.out.println("Player " + player.getPlayerID() + ": " + player.getName() + "'s Turn");
                        System.out.println("HERE? ");
                        // Setting Active Player
                        locationManager.setActivePlayer(player.getPlayerID());
                        player.playerTurn();
                        
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}