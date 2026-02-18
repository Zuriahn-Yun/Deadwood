import java.util.*;

/*
*
*
*
*/
public class Player {
    private String name;
    private int playerID;
    private int rank = 1;
    private int dollars = 0;
    private int credits = 0;
    private Role role;
    private Set location;
    private int Score = 0;

    UserInput userInput = new UserInput();

    // Turn Flags
    boolean current_Player = false;
    boolean workingRole = false;
    boolean isAtCastingOffice = false;
    boolean canTakeRole = false;

    boolean hasMoved = false;
    boolean hasUpgraded = false;
    boolean hasActed = false;
    boolean hasRehearsed = false;

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

    public void resetFlags() {
        this.hasMoved = false;
        this.hasUpgraded = false;
        this.hasActed = false;
        this.hasRehearsed = false;

    }

    public void playerTurn() {
        current_Player = true;
        resetFlags();
        while (current_Player) {
            List<String> availableActions = new ArrayList<>();
            // Decide the options the player has on this turn
            if (getWorkingRole()) {
                if (!this.hasActed) {
                    availableActions.add("Act");
                    availableActions.add("Rehearse");
                }
            } else {
                if (!this.hasMoved)
                    availableActions.add("Move");
                if (!this.isAtCastingOffice)
                    availableActions.add("Upgrade Rank");
                if (!this.canTakeRole)
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
            handleChoice(action);

        }

    }
    public void handleChoice(String choice){
        if(choice.equalsIgnoreCase("Move")){
            move();
        }
        if(choice.equalsIgnoreCase("Act")){
            act();
        }
        if(choice.equalsIgnoreCase("Rehearse")){
            rehearse();
        }
        if(choice.equalsIgnoreCase("Upgrade Rank")){
            upgradeRank();
        }
        if(choice.equalsIgnoreCase("Take Role")){
            takeRole();
        }
        else{
            System.out.println("Not a valid Action.");
        }
    }
    public void takeRole(){

    }
    public void upgradeRank(){
        if(isAtCastingOffice){
            for(int i = 0; 0 < 4; i ++){
                System.out.println("Level: " + i + "Cost in Dollars: " + dollarUpgradeMap.get(i) + " Cost in Credits: " + creditUpgradeMap.get(i));
            }

        }else{
            System.out.println("Player is not at Casting Office and Cannot Upgrade.");
        }

    }
    // Method to Upgrade Player Object to an arbitrary level when paying with
    // dollars
    public void dollarUpgradePlayer(int level) {
        if (getDollars() < dollarUpgradeMap.get(level)) {
            throw new IllegalAccessError("Player Does Not Have Enough Dollars");
        } else {
            setDollars(getDollars() - dollarUpgradeMap.get(level));
            System.out.println("Player is now upgraded to Level: " + level);
        }
    }

    // Method to Upgrade Player Object to an arbitrary level when paying with
    // credits
    public void creditUpgradePlayer(Player player, int level) {
        if (getCredits() < creditUpgradeMap.get(level)) {
            throw new IllegalAccessError("Player Does Not Have Enough Credits");
        } else {
            setCredits(getCredits() - creditUpgradeMap.get(level));
            System.out.println("Player is now upgraded to Level: " + level);
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

    public Player(Set trailer) {
        this.location = trailer;
    }

    public void move() {
        System.out.println("moving");
    }

    // Player Actions
    public void act() {
        System.out.println("Actinv");
    }

    public void rehearse() {
        System.out.println("Rehearsing");
    }

    public void Identify() {
        System.out.println("Player Id: " + getPlayerID());
        System.out.println("Player Name: " + getName());
        System.out.println("Rank: " + getRank());
        System.out.println("Dollars: " + getDollars());
        System.out.println("Credits: " + getCredits());
        System.out.println("Score: " + getScore());
    }

    // Get and Set Player Name from user input
    public void inputPlayerName(Scanner scanner, int playerID) {
        System.out.println("Input Player: " + playerID + "Name");
        name = scanner.nextLine();
        setName(name);
    }

    // Calculate Player Score
    public void calculateScore() {
        this.Score = getCredits() + getCredits() + (5 * getRank());
    }

    // getters
    public int getScore() {
        calculateScore();
        return Score;
    }

    public int getCredits() {
        return credits;
    }

    public boolean getWorkingRole() {
        return workingRole;
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

    public Set getLocation() {
        return location;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getname() {
        return name;
    }

    // Setters
    public void setLocation(Set location) {
        this.location = location;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

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

    public void Move(Set set) {
        this.location = set;
    }

}