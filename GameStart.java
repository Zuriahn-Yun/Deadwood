import java.util.*;

/**
*
* 
*/
public class GameStart {
    public static void main(String[] args) {
        // Start the Game
        System.out.println("Game Starting");
        try {
            while (true) {
                // Initialize Board
                Board board = new Board();

                GameManager gameManager = new GameManager();
                gameManager.initializeNumberOfPlayers();
                gameManager.initializePlayers(board.getTrailer());
                LocationManager locationManager = new LocationManager(gameManager.getPlayers());
                UserInput.setLocationManager(locationManager);

                for (int i = 1; i < gameManager.getTotalDays() + 1; i++) {
                    System.out.println("Start Day: " + i );
                    for (Player player : gameManager.getPlayers()) {
                        System.out.println("Player " + player.getPlayerID() + ": " + player.getName() + "'s Turn");
                        
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}