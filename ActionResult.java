/**
 * 
 * This class will hold results of when a player acts
 */
public class ActionResult {
    public final boolean success;
    public final int roll;
    // Terminal View Can use this message but the GUI will not be able to 
    public final String message; 
    public final int dollarsGained;
    public final int creditsGained;
    public final int chips;

    public final boolean sceneWrapped;
    public final SceneWrapResult wrapResult;
    
    // Constructor
    public ActionResult(boolean success, int roll,int chips, int dollars, int credits, boolean wrapped, SceneWrapResult result) {
        this.success = success;
        this.roll = roll;
        this.chips = chips;
        this.dollarsGained = dollars;
        this.creditsGained = credits;
        this.sceneWrapped = wrapped;
        this.wrapResult = result;
        // Failure or not failure message
        if (success == true){
            this.message = "Sucess";
        }else{
            this.message = "Failure";
        }
    }
}