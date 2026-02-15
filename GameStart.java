import java.util.*;

/*
*
* TODO COmments
*/
public class GameStart {
    public static void main(String[] args) {
        // Start the Game
        System.out.println("Game Starting");
        // Initialize Board
        try {
            Board board = new Board();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        
        Scanner scanner = new Scanner(System.in);
        GameManager gameManager = new GameManager();
        gameManager.initializeNumberOfPlayers(scanner);
        // Initialize castingOffice 
        CastingOffice castingOffice = new CastingOffice();

    }
}