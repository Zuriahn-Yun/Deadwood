import java.util.*;

/*
*
*
*
*/
public class Player {
    private String name;
    private int playerID;
    public int rank = 1;
    private int dollars = 0;
    private int credits = 0;
    private Part part;
    private Set location;
    private int score = 0;
    private int rehearsalChips = 0;
    private boolean isStarringRole = false;

    // Turn Flags
    boolean current_Player = false;
    boolean workingPart = false;
    boolean isAtCastingOffice = false;
    boolean canTakePart = false;

    boolean hasMoved = false;
    boolean hasUpgraded = false;
    boolean hasActed = false;
    boolean hasRehearsed = false;

    public void resetFlags() {
        this.hasMoved = false;
        this.hasUpgraded = false;
        this.hasActed = false;
        this.hasRehearsed = false;

    }

    public void setPart(Part part) {
        this.part = part;
        this.workingPart = true;
    }

    public Player(Set trailer) {
        this.location = trailer;
    }

    public int getRehearsalChips() {
        return rehearsalChips;
    }
    public void addRehearsalChips(Integer add){
        this.rehearsalChips = this.rehearsalChips + add;
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

    public void rehearse() {
    this.rehearsalChips++;
    this.hasActed = true;
    System.out.println(name + " now has " + rehearsalChips + " rehearsal chips.");
}
    public void act(){
        System.out.println("ACT");
    }
    public void takeRole(){
        System.out.println("Taking role");
    }
    // Calculate Player Score
    public void calculateScore() {
        this.score = this.getCredits() + this.getCredits() + (5 * this.getRank());
    }
    public void addCredits(Integer credits){
        this.credits = this.credits + credits;
    }
    public void addDollars(Integer dollars){
        this.dollars = this.dollars + dollars;
    }
    // getters
    public int getScore() {
        calculateScore();
        return score;
    }

    public int getCredits() {
        return credits;
    }

    public boolean getWorkingRole() {
        return workingPart;
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

    public Part getPart(){
        return part;
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
    public void setWorkingRole(boolean working) {
        this.workingPart = working;
    }
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

    public void Move(Set set) {
        this.location = set;
    }

}