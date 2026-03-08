import java.util.*;

/**
 * This is our move manager, it will handle any location changes and provide
 * neighbors
 */
public class MoveManager {
    private List<Set> sets;

    public MoveManager(ArrayList<Set> sets) {
        this.sets = sets;
    }

    //
    public List<String> getNeighborNames(Player player) {
        return player.getLocation().getNeighbors();
    }

    //
    public boolean executeMove(Player player, String destinationName) {
        Optional<Set> destination = getDestination(destinationName);
        if (destination.isPresent()) {
            player.setLocation(destination.get());
            return true;
        }
        return false;
    }

    // get destination if it exists otherwise return an empty optional
    private Optional<Set> getDestination(String destinationName) {
        for (Set s : this.sets) {
            if (s.getName().equalsIgnoreCase(destinationName)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }
}
