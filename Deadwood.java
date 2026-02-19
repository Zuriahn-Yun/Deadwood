/**
 * The main program running the console game logic.
 * 
 */
public class Deadwood {
    public static void main(String[] args) {
        // Start the Game
        System.out.println("Game Starting");
        try {
            while (true) {
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
                for (int i = 1; i < gameManager.getTotalDays() + 1; i++) {
                    System.out.println("Start Day: " + i);
                    for (Player player : gameManager.getPlayers()) {
                        System.out.println("Player " + player.getPlayerID() + ": " + player.getName() + "'s Turn");
                        // Setting Active Player
                        locationManager.setActivePlayer(player.getPlayerID());
                        // Player's Turn
                        gameManager.playerTurn(player);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}