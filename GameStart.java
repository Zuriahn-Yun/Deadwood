import java.util.*;

/*
*
* TODO COmments
*/
public class GameStart {
    // Function to EndGame
    public static void EndGame(){
        System.out.println("Ending Game");
        System.exit(0);
    }
    public static void main(String[] args) {
        // Start the Game
        System.out.println("Game Starting");
        try {
            String input = "";
            while(true){
            // Initialize Board
            Board board = new Board();

            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            if(input.equals("Exit Game")){
                EndGame();
            }
            GameManager gameManager = new GameManager();
            gameManager.initializeNumberOfPlayers(scanner);
            gameManager.initializePlayers(scanner, board.getTrailer());
            
            for(int i = 0; i < gameManager.getTotalDays();i++){
                System.out.println("Start Day: " + i + 1);
                for(Player player: gameManager.getPlayers()){
                    System.out.println("Player " + player.getPlayerID() + ": " + player.getName() + "'s Turn");
                    
                }
            }
        }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}