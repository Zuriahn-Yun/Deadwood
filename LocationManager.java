import java.util.*;

/*
*
*
*/
public class LocationManager {
    List<Player> players = new ArrayList<>();
    Player activePlayer;
    Integer activePlayerId;

    public LocationManager(List<Player> players) {
        this.players = players;
    }

    // Display the Location of all players and indicate the active player
    public void displayLocation() {
        if (activePlayer != null) {
            for (Player player : players) {

                if (player.getPlayerID() == activePlayerId) {
                    System.out.println(
                            "Active Player " + player.getPlayerID() + "  Location:  " + player.getLocation().getName());
                } else {
                    System.out
                            .println("Player " + player.getPlayerID() + " Location: " + player.getLocation().getName());
                }
            }
        } else {
            for (Player player : players) {
                System.out.println("Player " + player.getPlayerID() + " Location: " + player.getLocation().getName());
            }

        }

    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    // Based on Player Id
    public void setActivePlayer(Integer playerID) {
        this.activePlayerId = playerID;
        this.activePlayer = players.get(playerID);
    }

}
