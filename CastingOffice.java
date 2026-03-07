import java.util.*;
/*
* Casting Office Class. In Charge of Player upgrades with credit or dollars.
* 
*/
public class CastingOffice {
    // Key: Upgrade level, Value: Cost in Dollars
    public Map<Integer, Integer> dollarUpgradeMap = Map.of(
            2, 4,
            3, 10,
            4, 18,
            5, 28,
            6, 40);

    // Key: Upgrade level, Value: Cost in Credits
    public Map<Integer, Integer> creditUpgradeMap = Map.of(
            2, 5,
            3, 10,
            4, 15,
            5, 20,
            6, 25);

    // Check if player can afford the payment and return a boolean
    public boolean canAfford(Player player, int target, String currency){
        if(currency.toLowerCase().equals("dollar")){
            return dollarUpgradeMap.get(target) <= player.getDollars();
        }else{
            return creditUpgradeMap.get(target) <= player.getCredits();
        }
    }

    // This will be used to have the player pay and upgrade their rank 
    public void pay(Player player, int target, String currency){
        if(currency.toLowerCase().equals("dollar")){
            player.setDollars(player.getDollars() - dollarUpgradeMap.get(target));
            player.setRank(target);
        }else{
            player.setCredits((player.getDollars() - dollarUpgradeMap.get(target)));
            player.setRank(target);
        }
    }

}