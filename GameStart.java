import java.util.*;

/*
*
* TODO COmments
*/
public class GameStart {
    public static void main(String[] args) {
        // Start the Game
        System.out.println("Game Starting");
        Scanner scanner = new Scanner(System.in);
        GameManager gameManager = new GameManager();
        gameManager.initializePlayers(scanner);
    }
}