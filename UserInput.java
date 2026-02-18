import java.util.*;

/**
 * 
 */
public class UserInput{
    Scanner scanner = new Scanner(System.in);
    static LocationManager locationManager;

    
    public void EndGame(){
        System.out.println("Ending Game");
        System.exit(1);
    }
    public void help(){
        System.out.println("Commands" + "\n"  + "[end] - Ends the Game" +  "\n" + "[help] - Provides Commands" + "\n" + "[location] - Displays all players location and active player " + "\n");
    }
    public void EndTurn(){
        System.out.println("Ending Turn");
        // End the Players turn 
    }
    public void displayLocation(){
        if(locationManager != null){
            locationManager.displayLocation();
        }
        else{
            System.out.println("Location Display is not Available at this time.");
        }
        
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
            locationManager.displayLocation();
        }
        return userInput;
    }
    public static void setLocationManager(LocationManager locationManager) {
        UserInput.locationManager = locationManager;
    }

}