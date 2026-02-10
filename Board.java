import java.util.*;

/*
* TODO READ AND PROCESS XML Path: xml\board.xml
*/
public class Board {
    private Map<String, Set> board = new HashMap<>(); // Using map for adjacent moves

    private void initializeBoard() {
        // players only move to adjacent rooms
        board.put("Train Station", new Set("Train Station", Arrays.asList("Jail", "Main Street", "Casting Office")));
        board.put("Jail", new Set("Jail", Arrays.asList("Train Station", "Main Street")));
        board.put("Main Street", new Set("Main Street", Arrays.asList("Train Station", "Jail", "Trailer")));
    }
}
