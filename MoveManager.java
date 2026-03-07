import java.util.*;

/**
 * This is our move manager, it will handle any location changes
 */
public class MoveManager {
    private List<Set> sets;

    public MoveManager(ArrayList<Set> sets) {
        this.sets = sets;
    }

    // Step 1: Model provides the data (The "What")
    public List<String> getNeighborNames(Player player) {
        return player.getLocation().getNeighbors();
    }

    // Step 2: Model executes the change (The "Action")
    public boolean executeMove(Player player, String destinationName) {
        Optional<Set> destination = getDestination(destinationName);
        if (destination.isPresent()) {
            player.setLocation(destination.get());
            return true;
        }
        return false;
    }

    private Optional<Set> getDestination(String destinationName) {
        return this.sets.stream()
                   .filter(s -> s.getName().equalsIgnoreCase(destinationName))
                   .findFirst();
    }
}
