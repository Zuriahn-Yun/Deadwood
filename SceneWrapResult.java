import java.util.*;
/**
 *  This is a data class to hold any data from when the scene wraps up. Who gets paid, bonus as well as scenes and set. 
 */
public class SceneWrapResult {
    public final String sceneName;
    public final String setName;
    public final List<PayoutRecord> payouts;
    public final boolean bonusGiven; 
    
    public SceneWrapResult(String sceneName, String setName, List<PayoutRecord> payouts, boolean bonusGiven) {
        this.sceneName = sceneName;
        this.setName = setName;
        this.payouts = payouts;
        this.bonusGiven = bonusGiven;
    }
}