import java.util.*;
/*
*
*
*
*/
public class Player {
    private String name;
    // Id is the Players Number (Ex: 1 = Player 1), gets initialized during game start
    private int playerID;
    private int rank = 1;
    private int dollars = 0;
    private int credits =0;
    private Role role ;
    private String location = "Trailer";
    private int Score = 0;
    private boolean working = false;

    // Get and Set Player Name from user input
    public void inputPlayerName(Scanner scanner,int playerID){
        System.out.println("Input Player: " + playerID + "Name");
        name = scanner.nextLine();
        setName(name);
    }
    // TODO If the player wants to move, what do we want to happen
    public void Move(){
        // If the Playyer is not working they can Move
        if(getWorking() == false){
            
        }else{
            System.out.println("Player Cannot work if you are currently working.");
        }
    }
    // Player Actions 
    public void act() {
    }

    public void rehearse() {
    }

    // Calculate Player Score
    public void calculateScore(){
        this.Score = getCredits() + getCredits() + (5 * getRank());
    }
    // Getters for Player Class
    public int getCredits() {
        return credits;
    }

    public boolean getWorking(){
        return working;
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

    public int getPlayerID() {
        return playerID;
    }

    public String getname() {
        return name;
    }

    // Setters
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

    public void setLocation(String location) {
        this.location = location;
    }

    public void move(String destination) {
    }

    
}