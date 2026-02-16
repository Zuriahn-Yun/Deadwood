import java.util.*;

/*
*
* TODO COmments
*/
public class GameStart {
    public static void main(String[] args) {
        // Start the Game
        System.out.println("Game Starting");
        try {
            // Initialize Board
            Board board = new Board();

            Scanner scanner = new Scanner(System.in);
            GameManager gameManager = new GameManager();
            gameManager.initializeNumberOfPlayers(scanner);
            gameManager.initializePlayers(scanner, board.getTrailer());
            
            for(int i = 0; i < gameManager.getTotalDays();i++){
                System.out.println("Start Day: " + i + 1);
                for(Player player: gameManager.getPlayers()){
                    System.out.println("Player " + player.getPlayerID() + ": " + player.getName() + "'s Turn");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}