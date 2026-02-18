import java.util.*;

/*
*
*
*
*/
public class Player {
    private String name;
    // Id is the Players Number (Ex: 1 = Player 1), gets initialized during game
    private int playerID;
    private int rank = 1;
    private int dollars = 0;
    private int credits = 0;
    private Role role;
    private Set location;
    private int Score = 0;
    private boolean workingRole = false;

    public Player(Set trailer){
        this.location = trailer;
    }
    
    public void Identify(){
        System.out.println("Player Id: " + getPlayerID());
        System.out.println("Player Name: " + getName());
        System.out.println("Rank: " + getRank());
        System.out.println("Dollars: " + getDollars());
        System.out.println("Credits: " +getCredits());
        System.out.println("Score: " + getScore());
    }
    // Get and Set Player Name from user input
    public void inputPlayerName(Scanner scanner, int playerID) {
        System.out.println("Input Player: " + playerID + "Name");
        name = scanner.nextLine();
        setName(name);
    }

    // Player Actions
    public void act() {
    }

    public void rehearse() {
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