import java.util.*;

/**
 * 
 */
public class UserInput{
    Scanner scanner = new Scanner(System.in);
    static LocationManager locationManager = null;
    static GameManager gameManager = null;
    
    public void EndGame(){
        System.out.println("Ending Game");
        System.exit(1);
    }
    public void help(){
        System.out.println("Commands" + "\n"  + "[end] - Ends the Game" +  "\n" + "[help] - Provides Commands" + "\n" + "[location] - Displays all players location and active player " + "\n" + "[score] - Displays all players score");
    }
    public void displayLocation(){
        if(locationManager != null){
            System.out.println("hello");
            locationManager.displayLocation();
        }
        else{
            System.out.println("Location Display is not Available at this time.");
        }
    }
    public void identify(){
        gameManager.getCurrent_Player().Identify();
    }

    public String getInput(){
        String userInput = scanner.nextLine().trim();
        if(userInput.equalsIgnoreCase("end")){
            EndGame();
        }
        else if(userInput.equalsIgnoreCase("help")){
            help();
            System.out.println("Please enter another choice.");
            return getInput();
        }
        else if(userInput.equalsIgnoreCase("location")){
            displayLocation();
            System.out.println("Please enter another choice.");
            return getInput();
        }
        else if(userInput.equalsIgnoreCase("identify")){
            identify();
            System.out.println("Please enter another choice.");
            return getInput();
        }
        else if(userInput.equalsIgnoreCase("score")){
            scoreBoard();
            System.out.println("Please enter another choice.");
            return getInput();
        }
        return userInput;
    }
    public void scoreBoard(){
        if(gameManager != null){
            for(Player player: gameManager.getPlayers()){  
                System.out.println(player.getName() + " has " + player.getScore() + " Points");
            }
        }else{
            System.out.println("Score is not Availible yet.");
        }
    }
    public static void setLocationManager(LocationManager locationManager) {
        UserInput.locationManager = locationManager;
    }
    public static void setGameManager(GameManager gameManager) {
        UserInput.gameManager = gameManager;
    }
}