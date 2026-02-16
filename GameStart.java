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
            // Initialize Casting Office
            CastingOffice castingOffice = new CastingOffice();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}