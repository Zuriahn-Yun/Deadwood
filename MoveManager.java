import java.util.*;

/**
 * 
 */
public class MoveManager {

    ArrayList<Set> sets;

    public MoveManager(ArrayList<Set> sets) {
        this.sets = sets;
    }

    public void move(Player player, UserInput userInput) {
        System.out.println("Current Location: " + player.getLocation().getName());
        String currentlocation = player.getLocation().getName();
        // Iterate through possible locations
        List<String> neighbors = player.getLocation().getNeighbors();
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < neighbors.size(); i++) {
            System.out.println("Neightbor " + (i + 1) +" " + neighbors.get(i));
            map.put(i + 1, neighbors.get(i));
        }
        // Valid user input will be from 1 to neighbors.size
        String userchoice = "";
        System.out.println("Pick a neighboring destination using numbers 1 through " + neighbors.size() + ".");
        userchoice = userInput.getInput();
        while (Integer.parseInt(userchoice) < 1 || Integer.parseInt(userchoice) > neighbors.size()) {
            System.out.println("That was not valid input.");
            System.out.println("Pick a neighboring destination using numbers 1 through " + neighbors.size() + ".");
            userchoice = userInput.getInput();
        }
        // if we have valid user input
        String destinationName = map.get(Integer.parseInt(userchoice));
        Set Destination = getDestination(destinationName).orElse(null);
        System.out.println("Moving Player from " + currentlocation + " to " + Destination.getName() + ".");
        player.setLocation(Destination);
    }

    // Get the Destination
    public Optional<Set> getDestination(String destinationName) {
        for (Set currSet : sets) {
            if (currSet.getName().equals(destinationName)) {
                return Optional.ofNullable(currSet);
            }
        }
        return Optional.empty();
    }

}
