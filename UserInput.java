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
        System.out.println("Commands" + "\n"  + "[end] - Ends the Game" +  "\n" + "[help] - Provides Commands" + "\n" + "[location] - Displays all players location and active player " + "\n");
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
        String userInput = scanner.nextLine();
        if(userInput.equalsIgnoreCase("end")){
            EndGame();
        }
        if(userInput.equalsIgnoreCase("help")){
            help();
        }
        if(userInput.equalsIgnoreCase("location")){
            displayLocation();
        }
        if(userInput.equalsIgnoreCase("identify")){
            identify();
        }
        return userInput;
    }
    public static void setLocationManager(LocationManager locationManager) {
        UserInput.locationManager = locationManager;
    }
    public static void setGameManager(GameManager gameManager) {
        UserInput.gameManager = gameManager;
    }
}