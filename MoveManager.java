import java.util.*;

/**
 * 
 */
public class MoveManager {
    private ArrayList<Set> sets = new ArrayList<>();

    public void move(Player player, Scanner scanner) {
        System.out.println("Current Location: " + player.getLocation().getName());
        // Iterate through possible locations
        List<String> neighbors = player.getLocation().getNeighbors();
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < neighbors.size(); i++) {
            System.out.println("Neightbor " + (i + 1) + neighbors.get(i));
            map.put(i + 1, neighbors.get(i));
        }
        // Valid user input will be from 1 to neighbors.size
        String userInput = "";
        System.out.println("Pick a neighboring destination using numbers 1 through " + neighbors.size() + ".");
        userInput = scanner.nextLine();
        while (Integer.parseInt(userInput) < 1 || Integer.parseInt(userInput) > neighbors.size()) {
            System.out.println("That was not valid input.");
            System.out.println("Pick a neighboring destination using numbers 1 through " + neighbors.size() + ".");
            userInput = scanner.nextLine();
        }
        // if we have valid user input
        String destinationName = map.get(Integer.parseInt(userInput));

    }

    public Set getDestination(String destinationName) throws Exception null {
        try {
            for (Set set : sets) {
                if (set.getName() == destinationName) {
                    return set;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }

    }
}
