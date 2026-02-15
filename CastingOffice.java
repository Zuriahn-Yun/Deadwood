import java.util.*;

/*
* Casting Office Class. In Charge of Player upgrades with credit or dollars. When do Players interact with the casting office? 
* 
*/
public class CastingOffice {
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

    // Method to Upgrade Player Object to an arbitrary level when paying with
    // dollars
    public void dollarUpgradePlayer(Player player, int level) {
        if (player.getDollars() < dollarUpgradeMap.get(level)) {
            throw new IllegalAccessError("Player Does Not Have Enough Dollars");
        } else {
            player.setDollars(player.getDollars() - dollarUpgradeMap.get(level));
            System.out.println("Player is now upgraded to Level: " + level);
        }
    }

    // Method to Upgrade Player Object to an arbitrary level when paying with
    // credits
    public void creditUpgradePlayer(Player player, int level) {
        if (player.getCredits() < creditUpgradeMap.get(level)) {
            throw new IllegalAccessError("Player Does Not Have Enough Credits");
        } else {
            player.setCredits(player.getCredits() - creditUpgradeMap.get(level));
            System.out.println("Player is now upgraded to Level: " + level);
        }
    }

}