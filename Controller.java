import java.util.ArrayList;
import java.util.List;

public class Controller {
    // will contain reference to view and to Game Manager
    // The controller talks to the Model(Game Mangers) view reflects whats happening in the model
    // this will interact with the user -> model -> return to controller -> deliver to view
    GameManager model; // this is our model
    GameView view; // view
    UserInput userInput; // this is how we manage commands and grab user input
    public Controller(GameManager gameManager,GameView view) {
        this.model = gameManager;
        this.view = view;
        this.userInput = new UserInput();
    }
    
    // We need to do this to start the game
    public void initializeNumberOfPlayers(){
        while (true){
        view.display("Please Enter Number of Players:");
        String input = userInput.getInput();
        try {
            int num = Integer.parseInt(input);
            if(num >= 2 && num <= 8){
                // Pass info to the Game Manager
                model.setNumberofPlayers(num);
                model.setupDiffGroupSizes();
                view.display("Starting Game With: " + num + " Players");
                initializePlayers();
                return;
            }
            view.display("Invalid range! Please enter 2-8 players.");
            } catch (Exception e) {
            view.display("That's not a number. Try again!.");
            }
        }
    }
     // Initialize Player Objects with names and Ids;
    public void initializePlayers() {
        for (int i = 0; i < model.getNumberOfPlayers(); i++) {
            // Create a player Object
            Player player = new Player(model.board.getTrailer());
            System.out.println("Get Player Name: ");
            String name = userInput.getInput();
            player.setName(name);
            // Add to Player List
            model.players.add(player);
        }
        // Randomly Initialize Player Order
        view.display("Randomly Initializing Player Order");
        ArrayList<Player> orderedPlayers = new ArrayList<>();
        List<Integer> shuffledList = model.generateShuffledList(model.getNumberOfPlayers());
        for (int index : shuffledList) {
            orderedPlayers.add(model.players.get(index - 1));
        }
        model.players = orderedPlayers;
        int i = 1;
        for (Player player : model.players) {
            player.setPlayerID(i);
            view.display("Player " + i + ": " + player.getname());
            i += 1;
        }

    }

    public void playerTurn(Player player){
        player.current_Player = true;
        player.resetFlags();
        while (player.current_Player) {
            List<String> availableActions = new ArrayList<>();
            // Decide the options the player has on this turn
            if (player.getWorkingRole()) {
                if (!player.hasActed) {
                    availableActions.add("Act");
                    int budget = (player.getLocation()).getCurrentScene().getBudget();
                    // Check if the succeess is not guaranteed
                    if (1 + player.getRehearsalChips() < budget) {
                        availableActions.add("Rehearse");
                    } else {
                        view.display("Success is guaranteed.Player must Act.");
                    }
                }
            } else {
                if (!player.hasMoved)
                    availableActions.add("Move");
                if (player.isAtCastingOffice)
                    availableActions.add("Upgrade Rank");
                if (!player.canTakePart)
                    availableActions.add("Take Role");
            }
            availableActions.add("End Turn");
            // List Avaiblible actions
            for (int i = 1; i < availableActions.size() + 1; i++) {
                view.display("Press " + i + " for action " + availableActions.get(i - 1));
            }
            String choice = pickPlayerArgs(1, availableActions.size());
            int index = Integer.parseInt(choice) - 1;
            String action = availableActions.get(index);
            handleChoice(player, action);

        }
    }
public String pickPlayerArgs(Integer int1, Integer int2) {
        while (true) {
            try {
                String input = userInput.getInput();
                int choice = Integer.parseInt(input);

                if (choice >= int1 && choice <= int2) {
                    return String.valueOf(choice);
                } else {
                    view.display("Invalid choice. Please pick a number between " + int1 + " and " + int2 + ".");
                }
            } catch (Exception e) {
                view.display("Please provide a valide Integer between " + int1 + " and " + int2);
            }
        }
    }

    public void handleChoice(Player player, String choice) {
        if (choice.equalsIgnoreCase("Move")) {
            model.moveManager.move(player, userInput);
            player.hasMoved = true;
        } else if (choice.equalsIgnoreCase("Act")) {
            model.act(player);
        } else if (choice.equalsIgnoreCase("Rehearse")) {
            player.rehearse();
        } else if (choice.equalsIgnoreCase("Upgrade Rank")) {
            model.upgradePlayer(player, model.castingOffice);
        } else if (choice.equalsIgnoreCase("Take Role")) {
            model.takeRole(player);
        } else if (choice.equalsIgnoreCase("End Turn")) {
            player.current_Player = false;
        } else {
            view.display("Not a valid Action.");
        }
    }
    public void startGame(){
        view.display("Starting Game");
        try {
            // Create Neccessary Classes
            UserInput userInput = new UserInput();
            initializeNumberOfPlayers();
            LocationManager locationManager = new LocationManager(model.getPlayers());
            model.setLocationManager(locationManager);
            // now what happens a while loop and player turns and things 
            // game over returns true if the curr day is equal to max days
            for (int i = 1; i < model.getTotalDays() + 1; i++) {
                model.newDay();
                while (model.board.getRemainingScenes() > 1) {

                    for (Player player : model.getPlayers()) {
                        // Check if a previous player's action ended the day early
                        if (model.board.getRemainingScenes() <= 1) {
                            break;
                        }

                        view.display("\nDay " + model.currDay + " - Remaining Scenes: " + model.board.getRemainingScenes());
                        view.display("Turn: " + player.getName());

                        model.locationManager.setActivePlayer(player.getPlayerID());
                        model.playerTurn(player);
                    }
                }
                System.out.println("End of Day " + model.currDay);

            }
            // Game is complete
            model.getWinners();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
