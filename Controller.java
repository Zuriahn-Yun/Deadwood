// import java.util.ArrayList;
// import java.util.List;

import java.util.*;

public class Controller {
    // will contain reference to view and to Game Manager
    // The controller talks to the Model(Game Mangers) view reflects whats happening
    // in the model
    // this will interact with the user -> model -> return to controller -> deliver
    // to view
    GameManager model; // this is our model
    GameView view; // view
    UserInput userInput; // this is how we manage commands and grab user input

    public Controller(GameManager gameManager, GameView view) {
        this.model = gameManager;
        this.view = view;
        this.userInput = new UserInput();
    }

    public void handleMove(Player player) {
        // get neightbors from our model
        List<String> neighbors = model.moveManager.getNeighborNames(player);
        // Pass the to the view to show the neightbors
        String selectedDestination = view.requestMoveDestination(neighbors);
        // if we have none
        if (selectedDestination != null) {
            boolean success = model.moveManager.executeMove(player, selectedDestination);
            // otherwise
            if (success) {
                // change player args
                player.hasMoved = true;
                // check if we are at the casting office
                player.isAtCastingOffice = selectedDestination.equalsIgnoreCase("office");
                Set loc = player.getLocation();
                if (loc != null && loc.getCurrentScene() != null) {
                    loc.getCurrentScene().setFlipped(true);
                }
                // update in our view and display where we moved
                view.updatePlayerLocation(player);
                view.displayMessage("Successfully moved to " + selectedDestination);
            }
        }
    }

    // We need to do this to start the game
    public void initializeNumberOfPlayers() throws Exception {
        while (true) {
            String input = view.requestInput("Please Enter Number of Players (2-8):");
            // loop
            if (input == null || input.isEmpty())
                continue;
            try {
                int num = Integer.parseInt(input);
                if (num >= 2 && num <= 8) {
                    // Pass info to the Game Manager
                    model.setNumberofPlayers(num);
                    model.setupDiffGroupSizes();
                    view.displayMessage("Starting Game With: " + num + " Players");
                    initializePlayers();
                    return;
                }
            } catch (NumberFormatException e) {
                // Ignore and loop
            }
            view.displayMessage("Invalid range! Please enter 2-8 players.");
        }
    }

    // Initialize Player Objects with names and Ids;
    public void initializePlayers() {
        for (int i = 0; i < model.getNumberOfPlayers(); i++) {
            // Create a player Object
            Player player = new Player(model.board.getTrailer());
            String name = view.requestInput("Enter Name for Player " + (i + 1) + ":");
            if (name == null || name.isEmpty())
                name = "Player " + (i + 1);
            player.setName(name);
            // Add to Player List
            model.players.add(player);
        }
        // Randomly Initialize Player Order
        view.displayMessage("Randomly Initializing Player Order");
        ArrayList<Player> orderedPlayers = new ArrayList<>();
        List<Integer> shuffledList = model.generateShuffledList(model.getNumberOfPlayers());
        for (int index : shuffledList) {
            orderedPlayers.add(model.players.get(index - 1));
        }
        model.players = orderedPlayers;
        // passing colors for the dice
        String[] colors = { "b", "c", "g", "o", "p", "r", "v", "y" };
        int i = 1;
        // setting player ids, we never used these
        for (Player player : model.players) {
            player.setPlayerID(i);
            player.setColor(colors[i - 1]);
            view.displayMessage("Player " + i + ": " + player.getname());
            i += 1;
        }
        // add players into view
        view.initPlayers(model.players);
        view.updatePlayerStats(model.players.get(0));
    }

    // turn logic
    public void playerTurn(Player player) {
        player.current_Player = true;
        // reset flags so we know what the player has done so far
        player.resetFlags();
        view.updatePlayerStats(player);
        while (player.current_Player) {
            // get what we can do
            List<String> availableActions = new ArrayList<>();
            // Decide the options the player has on this turn
            if (player.getWorkingRole()) {
                if (!player.hasActed) {
                    Scene currentScene = player.getLocation().getCurrentScene();
                    if (currentScene != null) {
                        availableActions.add("Act");
                        int budget = currentScene.getBudget();
                        // check if the succeess is not guaranteed
                        if (1 + player.getRehearsalChips() < budget) {
                            availableActions.add("Rehearse");
                        } else {
                            view.displayMessage("Success is guaranteed.Player must Act.");
                        }
                    } else {
                        // Scene wrapped, we might just be allowing them to unwork their role or move if
                        // handled later, but role should be reset by GameManager
                        player.setWorkingRole(false);
                        availableActions.add("Move");
                    }
                }
            } else {
                if (!player.hasMoved)
                    availableActions.add("Move");
                if (player.isAtCastingOffice)
                    availableActions.add("Upgrade Rank");
                if (!player.canTakePart && player.getLocation() != null
                        && player.getLocation().getCurrentScene() != null
                        && !player.getLocation().getName().equals("trailer")
                        && !player.getLocation().getName().equals("office"))
                    availableActions.add("Take Role");
            }
            availableActions.add("End Turn");
            // Highlight available actions
            view.displayMessage("Available Actions: " + String.join(", ", availableActions));

            String action = view.waitForAction(availableActions);
            if (availableActions.contains(action)) {
                handleChoice(player, action);
            } else {
                view.displayMessage("That action is not currently available for you.");
            }
        }
    }

    // let the player pick an action
    public void handleChoice(Player player, String choice) {
        if (choice.equalsIgnoreCase("Move")) {
            handleMove(player);
            // hasMoved is handled inside handleMove upon success
        } else if (choice.equalsIgnoreCase("Act")) {
            ActionResult res = model.act(player);
            if (res.sceneWrapped) {
                view.showSceneWrap(res.wrapResult);
                // Allow them to move off the wrapped set
                player.hasMoved = false;
                player.hasActed = true; // Still can't act again
            }
        } else if (choice.equalsIgnoreCase("Rehearse")) {
            player.rehearse();
        } else if (choice.equalsIgnoreCase("Upgrade Rank")) {
            String currency = view.requestUpgradeCurrency();
            if (!currency.equals("cancel")) {
                int level = view.requestUpgradeLevel(player.getRank());
                int currencyType = currency.equals("dollar") ? 1 : 2;
                String resultMessage = model.processUpgrade(player, level, currencyType);
                view.displayMessage(resultMessage);
            }
        } else if (choice.equalsIgnoreCase("Take Role")) {
            List<Part> availableParts = model.getAllAvailableParts(player);
            String chosenPartName = view.requestRoleChoice(availableParts);
            if (chosenPartName != null) {
                Part chosenPart = null;
                for (Part p : availableParts) {
                    if (p.getName().equals(chosenPartName)) {
                        chosenPart = p;
                        break;
                    }
                }
                boolean success = model.attemptTakeRole(player, chosenPart);
                if (success) {
                    view.displayMessage("Successfully took the role: " + chosenPartName);
                } else {
                    view.displayMessage("Failed to take role. Rank too low or already working.");
                }
            }
        } else if (choice.equalsIgnoreCase("End Turn")) {
            player.current_Player = false;
        } else {
            view.displayMessage("Not a valid Action.");
        }

        view.updatePlayerStats(player);
        for (Player p : model.getPlayers()) {
            view.updatePlayerLocation(p);
        }
        view.updateBoard(model.board.getSets());
    }

    public void startGame() {
        view.displayMessage("Starting Game");
        try {
            initializeNumberOfPlayers();
            LocationManager locationManager = new LocationManager(model.getPlayers());
            model.setLocationManager(locationManager);

            // Day Loop
            for (int i = 1; i < model.getTotalDays() + 1; i++) {
                model.newDay();
                view.updateBoard(model.board.getSets());
                while (model.board.getRemainingScenes() > 1) {
                    for (Player player : model.getPlayers()) {
                        // Check if a previous player's action ended the day early
                        if (model.board.getRemainingScenes() <= 1) {
                            break;
                        }

                        view.displayMessage(
                                "\nDay " + model.currDay + " - Remaining Scenes: " + model.board.getRemainingScenes());
                        view.displayMessage("Turn: " + player.getName());

                        model.locationManager.setActivePlayer(player.getPlayerID());
                        playerTurn(player);
                    }
                }
                view.displayMessage("End of Day " + model.currDay);
            }
            // Game is complete
            view.displayMessage(model.calculateWinners());
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
