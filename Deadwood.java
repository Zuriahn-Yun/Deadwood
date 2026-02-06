import java.util.*;

/*
* CSCI 345 Deadwood Assignment
* Zuriahn Yun and Amy Pham 
*/


// Game System Class 
public class Deadwood {
    private ArrayList<Player> players = new ArrayList<>();
    private Player current_Player;
    private Map<String, Set> board = new HashMap<>(); // Using map for adjacent moves

    public Deadwood(int numPlayers) {
        initializeBoard();
        initializePlayers(numPlayers);
        // set number of players
    }

    private void initializeBoard() {
        // players only move to adjacent rooms
        board.put("Train Station", new Set("Train Station", Arrays.asList("Jail", "Main Street", "Casting Office")));
        board.put("Jail", new Set("Jail", Arrays.asList("Train Station", "Main Street")));
        board.put("Main Street", new Set("Main Street", Arrays.asList("Train Station", "Jail", "Trailer")));
    }

    private void initializePlayers(int num) {
        for (int i = 0; i < num; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
        current_Player = players.get(0);
    }

    // Getters
    public Map<String, Set> getBoard() {
        return board;
    }

    public Player getCurrent_Player() {
        return current_Player;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    // Setters
    public void setBoard(Map<String, Set> board) {
        this.board = board;
    }

    public void setCurrent_Player(Player current_Player) {
        this.current_Player = current_Player;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Casting Office Class. In Charge of Player upgrades with credit or dollars.
     * 
     */
    public class CastingOffice {
        // Key: Upgrade level, Value: Cost in Dollars
        private Map<Integer, Integer> dollarUpgradeMap = Map.of(
                2, 4,
                3, 10,
                4, 18,
                5, 28,
                6, 40);

        // Key: Upgrade level, Value: Cost in Credits
        private Map<Integer, Integer> creditUpgradeMap = Map.of(
                2, 5,
                3, 10,
                4, 15,
                5, 20,
                6, 25);

        // Method to Upgrade Player Object to an arbitrary level when paying with
        // dollars
        public void dollarUpgradePlayer(Player player, int level) {
            if (player.getDollars() < dollarUpgradeMap.get(level)) {
                throw new IllegalAccessError("Player Does Not Have Enough Dollars");
            } else {
                player.setDollars(player.getDollars() - dollarUpgradeMap.get(level));
                System.out.println("Player is now upgraded to Level: " + level);
            }
        }

        // Method to Upgrade Player Object to an arbitrary level when paying with
        // credits
        public void creditUpgradePlayer(Player player, int level) {
            if (player.getCredits() < creditUpgradeMap.get(level)) {
                throw new IllegalAccessError("Player Does Not Have Enough Credits");
            } else {
                player.setCredits(player.getCredits() - creditUpgradeMap.get(level));
                System.out.println("Player is now upgraded to Level: " + level);
            }
        }

    }

    public class Player {
        private String name;
        private int rank;
        private int dollars;
        private int credits;
        private Role role;
        private String location = "Trailer";

        public Player(String name) {
            this.name = name;
        }

        // Getters for Player Class
        public int getCredits() {
            return credits;
        }

        public String getName() {
            return name;
        }

        public int getRank() {
            return rank;
        }

        public int getDollars() {
            return dollars;
        }

        public Role getRole() {
            return role;
        }

        public String getLocation() {
            return location;
        }

        // Setters for Player Class
        public void setCredits(int credits) {
            this.credits = credits;
        }

        public void setDollars(int dollars) {
            this.dollars = dollars;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void move(String destination) {
        }

        public void act() {
        }

        public void rehearse() {
        }
    }

    public class Set {
        private String name;
        private List<String> neighbors;

        public Set(String name, List<String> neighbors) {
            this.name = name;
            this.neighbors = neighbors;
        }

        public boolean isAdjacent(String destination) {
            return neighbors.contains(destination);
        }

        // getters
        public String getName() {
            return name;
        }

        public List<String> getNeighbors() {
            return neighbors;
        }

        // setters
        public void setName(String name) {
            this.name = name;
        }

        public void setNeighbors(List<String> neighbors) {
            this.neighbors = neighbors;
        }
    }

    public class Role {
        private String name;
        private int rank;
        private boolean occupied = false;

        // Setters
        public void setName(String name) {
            this.name = name;
        }

        public void setOccupied(boolean occupied) {
            this.occupied = occupied;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        // Getters
        public String getName() {
            return name;
        }

        public int getRank() {
            return rank;
        }

        public boolean getOccupied() {
            return occupied;
        }
    }

    public class Scenes {
        private ArrayList<String> roles = new ArrayList<>();
        private int budget;

        // Setters
        public void setRoles(ArrayList<String> roles) {
            this.roles = roles;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }

        // Getters
        public int getBudget() {
            return budget;
        }

        public ArrayList<String> getRoles() {
            return roles;
        }
    }

    /*
     * Dice Class. For use by Player Object when Dice need to be rolled.
     */
    public class Dice {
        // Create a Random Instance
        private static Random random = new Random();

        // Roll the dice, Max = 6 , Min = 1
        public static int roll() {
            return random.nextInt(6) + 1;
        }
    }

    public static void main(String[] args) {
        // Scanner to Read Input, this logic can be moved to a function in the Deadwood
        // Class
        Scanner scanner = new Scanner(System.in);
        System.out.println("Testing Game Start");
        Integer numPlayers = null;
        while (numPlayers == null || numPlayers < 2 || numPlayers > 8) {
            System.out.println("Please Enter Number of Players");
            if (scanner.hasNextInt()) {
                numPlayers = scanner.nextInt();
                // Read Next Line
                scanner.nextLine();
            } else {
                String invalidInput = scanner.nextLine();
                System.err.println("That is not a valid number of Players");
            }
        }
        System.out.println("Starting Game With: " + numPlayers + " Players");
        // Close Scanner
        scanner.close();
    }
}