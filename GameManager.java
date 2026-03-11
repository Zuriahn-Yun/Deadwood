import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
*
* This is our model, it will handle all the classes/data in the backend and report to our controller
*/
public class GameManager {
    // Player Data
    private Player current_Player;
    private int NumberOfPlayers;
    List<Player> players = new ArrayList<>();

    // Day Game Data
    private int TotalDays = 4;
    public int currDay = 0;

    // XML Data
    Deck deck;
    Board board;
    CastingOffice castingOffice;
    LocationManager locationManager;
    MoveManager moveManager;

    // Create Model
    public GameManager() throws Exception {
        this.deck = new Deck();
        this.board = new Board();
        this.castingOffice = new CastingOffice();
        this.moveManager = new MoveManager(this.board.getAllSets());
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
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

    // Randomly Generate a list of numbers from 1 to n for player shuffling
    public List<Integer> generateShuffledList(int n) {
        List<Integer> list = IntStream.rangeClosed(1, n)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(list);
        return list;
    }

    public List<Part> getAllAvailableParts(Player player) {
        if (!(player.getLocation() instanceof Set))
            return new ArrayList<>();

        Set currSet = (Set) player.getLocation();
        List<Part> allParts = new ArrayList<>();

        // Cannot take ANY parts if the scene has wrapped
        if (currSet.getCurrentScene() != null) {
            allParts.addAll(currSet.getCurrentScene().getParts());
            allParts.addAll(currSet.getParts());
        }

        return allParts;
    }

    // MODEL: Just executes the change
    public boolean attemptTakeRole(Player player, Part part) {
        if (player.getRank() >= part.getLevel() && !player.getWorkingRole()) {
            player.setPart(part);
            player.setWorkingRole(true);
            return true;
        }
        return false;
    }

    public ActionResult act(Player player) {
        Set currSet = player.getLocation();
        int roll = new Random().nextInt(6) + 1;
        int total = roll + player.getRehearsalChips();

        boolean isSuccess = total >= currSet.getCurrentScene().getBudget();
        int dollarsGained = 0;
        int creditsGained = 0;
        boolean wrapped = false;
        SceneWrapResult payoutInfo = null;

        if (isSuccess) {
            Scene wrappedScene = currSet.removeTake();
            if (player.getPart().getStarringRole()) {
                creditsGained = 2;
            } else {
                dollarsGained = 1;
                creditsGained = 1;
            }

            if (wrappedScene != null) {
                wrapped = true;
                payoutInfo = handlePayout(wrappedScene, currSet);
            }
        } else {
            // Failure consolation
            if (!player.getPart().getStarringRole()) {
                dollarsGained = 1;
            }
        }

        // Apply the data changes to the Model
        player.addDollars(dollarsGained);
        player.addCredits(creditsGained);
        player.hasActed = true;

        // Action result is a data class that will tell our controller what happened
        return new ActionResult(isSuccess, roll, player.getRehearsalChips(), dollarsGained, creditsGained, wrapped,
                payoutInfo);
    }

    // same as previous payout function but now we return a data object
    public SceneWrapResult handlePayout(Scene currScene, Set currSet) {
        List<Player> localPlayers = locationManager.getPlayersAtLocation(currSet);
        List<Player> onCardPlayers = new ArrayList<>();
        List<Player> offCardPlayers = new ArrayList<>();

        for (Player player : localPlayers) {
            if (player.getPart() != null) {
                if (player.getPart().getStarringRole()) {
                    onCardPlayers.add(player);
                } else {
                    offCardPlayers.add(player);
                }
            }
        }

        List<PayoutRecord> payouts = new ArrayList<>();
        boolean bonusGiven = !onCardPlayers.isEmpty();

        if (bonusGiven) {
            payouts = distributeBonuses(currScene, onCardPlayers, offCardPlayers);
        } else {
            // nothing
        }

        for (Player player : localPlayers) {
            player.setWorkingRole(false);
            player.setPart(null);
            player.addRehearsalChips(-player.getRehearsalChips());
        }

        // return scene wrap data 
        return new SceneWrapResult(currScene.getSceneName(), currSet.getName(), payouts, bonusGiven);
    }

    // new object to hold the result of dritibute bonuses for each player
    public List<PayoutRecord> distributeBonuses(Scene scene, List<Player> onCard, List<Player> offCard) {
        Random random = new Random();
        int budget = scene.getBudget();
        List<PayoutRecord> payouts = new ArrayList<>();

        List<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < budget; i++) {
            rolls.add(random.nextInt(6) + 1);
        }
        Collections.sort(rolls, Collections.reverseOrder());

        List<Part> starringParts = scene.getParts();
        starringParts.sort((p1, p2) -> Integer.compare(p2.getLevel(), p1.getLevel()));

        for (int j = 0; j < rolls.size(); j++) {
            int dieValue = rolls.get(j);
            Part targetPart = starringParts.get(j % starringParts.size());

            for (Player player : onCard) {
                if (player.getPart() == targetPart) {
                    player.addDollars(dieValue);
                    payouts.add(new PayoutRecord(player.getName(), dieValue, true));
                }
            }
        }

        for (Player player : offCard) {
            int amount = player.getPart().getLevel();
            player.addDollars(amount);
            payouts.add(new PayoutRecord(player.getName(), amount, false));
        }

        // list of payout objects
        return payouts;
    }

    public int newDay() {
        for (Set set : board.getSets()) {
            if (set.getTakes() != null) {
                set.setTotalTakes(set.getTakes().size());
            }
            Scene nextMovie = deck.drawCard();
            if (nextMovie != null) {
                set.setCurrentScene(nextMovie);
            }
        }
        // move everyone to the trailer
        for (Player player : players) {
            player.setLocation(board.getTrailer());
            player.setWorkingRole(false);
            player.resetFlags(); // Crucial for GUI button states!
        }
        currDay++;
        return currDay;
    }

    // Calcualte winners
    public String calculateWinners() {
        for (Player player : players) {
            player.calculateScore();
        }
        // Sort descending by score
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            res.append("Place Number: ").append((i + 1));
            res.append(" ")
                    .append(players.get(i).getName())
                    .append(": ")
                    .append(players.get(i).getScore())
                    .append(" points.\n");
        }
        return res.toString();
    }

    public String processUpgrade(Player player, int level, int currencyType) {
        // currencyType: 1 for Dollars, 2 for Credits
        if (!player.isAtCastingOffice) {
            return "You must be at the Casting Office to upgrade!";
        }

        boolean success = false;
        String currencyLabel = (currencyType == 1) ? "dollar" : "credit";

        if (castingOffice.canAfford(player, level, currencyLabel)) {
            castingOffice.pay(player, level, currencyLabel);
            success = true;
        }

        if (success) { // Fixed the incomplete if statement
            return "Upgrade successful! " + player.getName() + " is now rank " + player.getRank() + ".";
        } else {
            return "Upgrade failed. Insufficient funds or invalid rank.";
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