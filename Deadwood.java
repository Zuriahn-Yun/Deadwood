/**
 * The main program running the console game logic.
 * 
 */
public class Deadwood {
    public static void main(String[] args) {
        try {
            // Initialize our Model 
            GameManager model = new GameManager();

            View.guiView view = new View.guiView();
            // Pick a view
            // GameView view = new View.terminalView();
            // GameView view = new View.GuiView();

            // Create Controller
            Controller controller = new Controller(model, view);
            
            // Starting game
            controller.startGame();

            // gameManager.initializeNumberOfPlayers();
            // gameManager.initializePlayers(board.getTrailer());
            // // Create locationManager
            // LocationManager locationManager = new LocationManager(gameManager.getPlayers());
            // gameManager.setLocationManager(locationManager);
            // UserInput.setLocationManager(locationManager);
            // UserInput.setGameManager(gameManager);
            // // Day Loop
            // for (int i = 1; i < gameManager.getTotalDays() + 1; i++) {
            //     gameManager.newDay();
            //     while (board.getRemainingScenes() > 1) {

            //         for (Player player : gameManager.getPlayers()) {
            //             // Check if a previous player's action ended the day early
            //             if (board.getRemainingScenes() <= 1) {
            //                 break;
            //             }

            //             System.out.println("\nDay " + gameManager.currDay + " - Remaining Scenes: " + board.getRemainingScenes());
            //             System.out.println("Turn: " + player.getName());

            //             locationManager.setActivePlayer(player.getPlayerID());
            //             gameManager.playerTurn(player);
            //         }
            //     }
            //     System.out.println("End of Day " + gameManager.currDay);

            // }
            // // Game is complete
            // gameManager.getWinners();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }

    // Should really just be
    // create Game Manager
    // pick view
    // make controller with access to view and game manager
    // controller.startgame
}