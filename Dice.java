import java.util.*;

/*
* Dice Class. For use by Player Object when Dice need to be rolled.
* TODO: MOVE THIS TO ALTERNATE CLASS REDUNDANT?
*/
public class Dice {
    // Create a Random Instance
    private static Random random = new Random();

    // Roll the dice, Max = 6 , Min = 1
    public static int roll() {
        return random.nextInt(6) + 1;
    }
}
