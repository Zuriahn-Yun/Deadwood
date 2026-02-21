import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
*
*
*/
public class GameManager {
    private Player current_Player;
    private int NumberOfPlayers;
    List<Player> players = new ArrayList<>();
    private int TotalDays = 4;
    public int currDay = 0;
    UserInput userInput = new UserInput();
    Deck deck;
    Board board;
    CastingOffice castingOffice;
    LocationManager locationManager;
    MoveManager moveManager;

    public GameManager(Deck deck, Board board, CastingOffice castingOffice, MoveManager moveManager) {
        this.deck = deck;
        this.board = board;
        this.castingOffice = castingOffice;

        this.moveManager = moveManager;
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    // Initialize the number of players for the game
    public void initializeNumberOfPlayers() {
        Integer numPlayers = readPlayers();
        setNumberofPlayers(numPlayers);
        setupDiffGroupSizes();
        System.out.println("Starting Game With: " + numPlayers + " Players");
    }

    public Integer readPlayers() {
        while (true) {
            System.out.println("Please Enter Number of Players");
            String input = userInput.getInput();
            try {
                int num = Integer.parseInt(input);
                if (num >= 2 && num <= 8) {
                    return num;
                }
                System.err.println("Invalid range! Please enter 2-8 players.");
            } catch (NumberFormatException e) {
                System.err.println("That's not a number. Try again!");
            }
        }
    }

    // Setup Different Group Sizes
    public void setupDiffGroupSizes() {
        // If we have 3 or 4 Players we only play 3 days
        if (getNumberOfPlayers() == 3 || getNumberOfPlayers() == 4) {
            this.TotalDays = 3;
        }
        // If we have 5 Players set Credits to 2
        if (getNumberOfPlayers() == 5) {
            for (int i = 0; i < players.size(); i++) {
                Player curr = players.get(i);
                curr.setCredits(2);
            }
        }
        if (getNumberOfPlayers() == 6) {
            for (int i = 0; i < players.size(); i++) {
                Player curr = players.get(i);
                curr.setCredits(4);
            }
        }
        if (getNumberOfPlayers() == 8 || getNumberOfPlayers() == 7) {
            for (int i = 0; i < players.size(); i++) {
                Player curr = players.get(i);
                curr.setRank(2);
            }
        }
    }

    // Initialize Player Objects with names and Ids;
    public void initializePlayers(Set trailer) {
        for (int i = 0; i < getNumberOfPlayers(); i++) {
            // Create a player Object
            Player player = new Player(trailer);
            System.out.println("Get Player Name: ");
            String name = userInput.getInput();
            player.setName(name);
            // Add to Player List
            players.add(player);
        }
        // Randomly Initialize Player Order
        System.out.println("Randomly Initializing Player Order");
        ArrayList<Player> orderedPlayers = new ArrayList<>();
        List<Integer> shuffledList = generateShuffledList(getNumberOfPlayers());
        for (int index : shuffledList) {
            orderedPlayers.add(players.get(index - 1));
        }
        this.players = orderedPlayers;
        int i = 1;
        for (Player player : players) {
            player.setPlayerID(i);
            System.out.println("Player " + i + ": " + player.getname());
            i += 1;
        }

    }

    // Randomly Generate a list of numbers from 1 to n for player shuffling
    public List<Integer> generateShuffledList(int n) {
        List<Integer> list = IntStream.rangeClosed(1, n)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(list);
        return list;
    }

    public void playerTurn(Player player) {
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
                        System.out.println("Success is guaranteed.Player must Act.");
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
                System.out.println("Press " + i + " for action " + availableActions.get(i - 1));
            }
            String choice = pickPlayerArgs(1, availableActions.size());
            int index = Integer.parseInt(choice) - 1;
            String action = availableActions.get(index);
            handleChoice(player, action);

        }

    }

    public void handleChoice(Player player, String choice) {
        if (choice.equalsIgnoreCase("Move")) {
            moveManager.move(player, userInput);
            player.hasMoved = true;
        } else if (choice.equalsIgnoreCase("Act")) {
            act(player);
        } else if (choice.equalsIgnoreCase("Rehearse")) {
            player.rehearse();
        } else if (choice.equalsIgnoreCase("Upgrade Rank")) {
            upgradePlayer(player, castingOffice);
        } else if (choice.equalsIgnoreCase("Take Role")) {
            takeRole(player);
        } else if (choice.equalsIgnoreCase("End Turn")) {
            player.current_Player = false;
        } else {
            System.out.println("Not a valid Action.");
        }
    }

    public void takeRole(Player player) {
        if (!(player.getLocation() instanceof Set)) {
            System.out.println("You can't take a role here.");
            return;
        }

        Set currSet = (Set) player.getLocation();
        Scene currScene = currSet.getCurrentScene();
        if(currScene == null){
            System.out.println("You cannot take any roles here.");
            return;
        }
        List<Part> offCardParts = currSet.getParts();
        List<Part> onCardParts = currScene.getParts();

        System.out.println("Starring Roles On-Card: ");
        for (int i = 0; i < onCardParts.size(); i++) {
            System.out.println(
                    i + 1 + ": " + onCardParts.get(i).getName() + "[Rank " + onCardParts.get(i).getLevel() + "]");
        }
        System.out.println("Available Extra Roles (Off-Card): ");
        int extra = onCardParts.size();
        for (int i = 0; i < offCardParts.size(); i++) {
            System.out.println((i + 1 + extra) + ": " + offCardParts.get(i).getName() + "[Rank "
                    + offCardParts.get(i).getLevel() + "]");
        }
        // Grab Total Roles
        int totalRoles = onCardParts.size() + offCardParts.size();
        // Get Player Choice
        int choice = Integer.parseInt(pickPlayerArgs(1, totalRoles));

        Part chosenPart;
        if (choice <= onCardParts.size()) {
            chosenPart = onCardParts.get(choice - 1);
        } else {
            chosenPart = offCardParts.get(choice - 1 -extra);
        }
        // Ensure Player can take the role
        if (player.getRank() >= chosenPart.getLevel()) {
            player.setWorkingRole(true);
            player.setPart(chosenPart);
            player.current_Player = false;
            System.out.println("Role taken: " + chosenPart.getName());
        } else {
            System.out.println("Rank too low for this role.");
        }

    }

    public void act(Player player) {
        Set currSet = player.getLocation();
        int roll = new Random().nextInt(6) + 1;
        int total = roll + player.getRehearsalChips();
        System.out.println("Dice Value: " + roll + " + Chips: " + player.getRehearsalChips());
        if (total >= player.getLocation().getCurrentScene().getBudget()) {
            System.out.println("Success.");
            Scene wrappedScene = currSet.removeTake();
            // If we are a starring actor we make 2 credits
            if (player.getPart().getStarringRole()) {
                player.addCredits(2);
                // Otherwise its a dollar and a credit
            } else {
                player.addDollars(1);
                player.addCredits(1);
            }

            if (wrappedScene != null) {
                handlePayout(wrappedScene, currSet);
            }
        } else {
            System.out.println("Failure.");
            // Consolation: Extras get $1 even if they fail. Stars get nothing.
            if (!player.getPart().getStarringRole()) {
                System.out.println("You Receive a 1 dollar payment. ");
                player.addDollars(1);
            }
        }
        player.current_Player = false;
    }

    public void handlePayout(Scene currScene, Set currSet) {
        List<Player> localPlayers = locationManager.getPlayersAtLocation(currSet);
        List<Player> onCardPlayers = new ArrayList<>();
        List<Player> offCardPlayers = new ArrayList<>();
        Integer budget = currScene.getBudget();
        for (Player player : localPlayers) {
            if (player.getPart().getStarringRole()) {
                onCardPlayers.add(player);
            } else {
                offCardPlayers.add(player);
            }
        }
        if (!onCardPlayers.isEmpty()) {
            distributeBonuses(currScene, onCardPlayers, offCardPlayers);
        } else {
            System.out.println("No Starring Actors. No bonuses.");
        }
        // cleanup local players who are done working the set
        for (Player player : localPlayers) {
            player.setWorkingRole(false);
            player.setPart(null);
            player.addRehearsalChips(-player.getRehearsalChips());
        }
    }

    public void distributeBonuses(Scene scene, List<Player> onCard, List<Player> offCard) {
        Random random = new Random();
        int budget = scene.getBudget();

        List<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < budget; i++) {
            rolls.add(random.nextInt(6) + 1);
        }
        // sort rolls
        Collections.sort(rolls, Collections.reverseOrder());
        List<Part> starringParts = scene.getParts();
        // order starringParts
        starringParts.sort((p1, p2) -> Integer.compare(p2.getLevel(), p1.getLevel()));
        for (int j = 0; j < rolls.size(); j++) {
            int dieValue = rolls.get(j);
            // incase more dice than starring roles edge case
            Part targetPart = starringParts.get(j % starringParts.size());

            for (Player player : onCard) {
                if (player.getPart() == targetPart) {
                    player.addDollars(dieValue);
                    System.out.println(player.getname() + "receives $" + dieValue);
                }
            }
        }
        // paying extras
        for (Player player : offCard) {
            int amount = player.getPart().getLevel();
            player.addDollars(amount);
            System.out.println(player.getname() + " receives $" + amount);
        }

    }

    public void newDay() {
        for (Set set : board.getSets()) {
            Scene nextMovie = deck.drawCard();
            if (nextMovie != null) {
                set.setCurrentScene(nextMovie);
            }
        }
        // move everyone to the trailer
        Set trailer = board.getTrailer();
        for (Player player : players) {
            player.setLocation(trailer);
            player.setWorkingRole(false);
        }
        currDay += 1;
        System.out.println("Starting New Day");
    }

    public void getWinners() {
        System.out.println("Calculating winners");
        for (Player player : players) {
            player.calculateScore();
        }
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Place Number: " + (i + 1));
            System.out.println(players.get(i).getName() + ":" + players.get(i).getScore() + " points.");
        }
    }

    public void upgradePlayer(Player player, CastingOffice castingOffice) {
        if (player.isAtCastingOffice) {
            for (int i = 2; i < 7; i++) {
                System.out.println("Level: " + i + "Cost in Dollars: " + castingOffice.dollarUpgradeMap.get(i)
                        + " Cost in Credits: " + castingOffice.creditUpgradeMap.get(i));
            }
            System.out.println("Select currency: 1. Dollars, 2. Credits, 3. CANCEL");
            String choice = pickPlayerArgs(1, 3);
            if (choice.equals("3")) {
                System.out.println("Returning to menu");
                return;
            }
            System.out.println("Pick Upgrade Level between current rank: " + player.getRank() + " and 6.");
            Integer upgradeLevel = Integer.parseInt(pickPlayerArgs(player.getRank(), 6));
            if (choice.equals("1")) {
                castingOffice.dollarUpgradePlayer(player, upgradeLevel);
            } else {
                castingOffice.creditUpgradePlayer(player, upgradeLevel);
            }
        } else {
            System.out.println("Player is not at Casting Office and Cannot Upgrade.");
        }
    }

    // Assuming its a players turn and they can only pick between int1 to int2
    // Returns a string
    public String pickPlayerArgs(Integer int1, Integer int2) {
        while (true) {
            try {
                String input = userInput.getInput();
                int choice = Integer.parseInt(input);

                if (choice >= int1 && choice <= int2) {
                    return String.valueOf(choice);
                } else {
                    System.out.println("Invalid choice. Please pick a number between " + int1 + " and " + int2 + ".");
                }
            } catch (Exception e) {
                System.out.println("Please provide a valide Integer between " + int1 + " and " + int2);
            }
        }
    }

    // Getters
    public List<Player> getPlayers() {
        return players;
    }

    public int getTotalDays() {
        return TotalDays;
    }

    public Player getCurrent_Player() {
        return current_Player;
    }

    public int getNumberOfPlayers() {
        return NumberOfPlayers;
    }

    // Setters
    public void setCurrent_Player(Player current_Player) {
        this.current_Player = current_Player;
    }

    public void setNumberofPlayers(Integer Players) {
        this.NumberOfPlayers = Players;
    }
}