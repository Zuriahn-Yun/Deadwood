import java.util.*;

/**
 * This File is our View, Game View depicts all the functions
 * that must be present in every type of view we create.
 */
interface GameView {
    // Display any arbitrary message
    void displayMessage(String message);

    // Display Updated Player Stats
    void updatePlayerStats(Player p);

    // Show Result of an action
    void showActionResult(ActionResult result);

    // Show the wrapping of a scene
    void showSceneWrap(SceneWrapResult wrap);

    // Show the winners at the end of the game 
    void showWinners(List<Player> winners);
    
    String requestInput(String prompt);
  
}

public class View {
    // Implement the Terminal View
    public static class terminalView implements GameView {
        @Override
        public void displayMessage(String text) {
            System.out.println(text);
        }

        @Override
        public void updatePlayerStats(Player p) {
            System.out.println(p.getname());
        }

        @Override
        public void showWinners(List<Player> winners) {
            for (Player p : winners) {
                System.out.println(p.getname());
            }
        }

        @Override
        public String requestInput(String prompt) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void showActionResult(ActionResult result) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void showSceneWrap(SceneWrapResult wrap) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        // Inside your View Interface
public String requestMoveDestination(List<String> neighbors) {
    displayMessage("Neighbors available:");
    for (int i = 0; i < neighbors.size(); i++) {
        displayMessage((i + 1) + ": " + neighbors.get(i));
    }
    
    int choice = pickPlayerArgs(1, neighbors.size()); 
    return neighbors.get(choice - 1);
}
    }

    // Implement the GUI View
    public static class guiView implements GameView {
        @Override
        public void displayMessage(String text) {
            System.out.println(text);
        }

        @Override
        public void updatePlayerStats(Player p) {
            System.out.println(p.getname());
        }

        @Override
        public void showWinners(List<Player> winners) {
            for (Player p : winners) {
                System.out.println(p.getname());
            }
        }

        @Override
        public String requestInput(String prompt) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void showActionResult(ActionResult result) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void showSceneWrap(SceneWrapResult wrap) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}
