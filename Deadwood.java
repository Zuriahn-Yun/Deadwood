/**
 * The main program running the console game logic.
 * 
 */
public class Deadwood {
    public static void main(String[] args) {
        // Start the Game
        System.out.println("Game Starting");
        try {
            // Initialize Board,Deck and Casting Office to be passed to the Game Manager
            Board board = new Board();
            Deck deck = new Deck();
            CastingOffice castingOffice = new CastingOffice();
            MoveManager moveManager = new MoveManager(board.getSets());
            GameManager gameManager = new GameManager(deck, board, castingOffice, moveManager);

            gameManager.initializeNumberOfPlayers();
            gameManager.initializePlayers(board.getTrailer());
            // Create locationManager
            LocationManager locationManager = new LocationManager(gameManager.getPlayers());
            gameManager.setLocationManager(locationManager);
            UserInput.setLocationManager(locationManager);
            UserInput.setGameManager(gameManager);
            // Day Loop
            for (int i = 1; i < gameManager.getTotalDays() + 1; i++) {
                gameManager.newDay();
                while (board.getRemainingScenes() > 1) {

                    for (Player player : gameManager.getPlayers()) {
                        // Check if a previous player's action ended the day early
                        if (board.getRemainingScenes() <= 1) {
                            break;
                        }

                        System.out.println("\nDay " + gameManager.currDay + " - Remaining Scenes: " + board.getRemainingScenes());
                        System.out.println("Turn: " + player.getName());

                        locationManager.setActivePlayer(player.getPlayerID());
                        gameManager.playerTurn(player);
                    }
                }
                System.out.println("End of Day " + gameManager.currDay);

            }
            // Game is complete
            gameManager.getWinners();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}