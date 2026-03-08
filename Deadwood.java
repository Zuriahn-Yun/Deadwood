/**
 * The main program running the console game logic.
 * 
 */
public class Deadwood {
    public static void main(String[] args) throws Exception {
        // Initialize our Model
        GameManager model = new GameManager();

        // Pick a view
        // GameView view = new View.terminalView();
        GameView view = new View.guiView();

        // Create Controller
        Controller controller = new Controller(model, view);

        // Run the game loop
        controller.startGame();

    }
}