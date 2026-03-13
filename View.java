import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.w3c.dom.events.MouseEvent;

/**
 * This File is our View, Game View depicts all the functions
 * that must be present in every type of view we create.
 */

// Interface containing all methods any view needs, most are for gui
interface GameView {
    // Display any arbitrary message
    void displayMessage(String message);

    // Show player stats, name color dollars and credits
    void updatePlayerStats(Player player);

    // CREATE PLAYER DICE in GUI
    void initPlayers(List<Player> players);

    // mainly to update the location of players on the board
    void updatePlayerLocation(Player player);

    // refresh our set cards
    void updateBoard(List<Set> sets);

    // show what happens after an action/player turn we get an action object
    void showActionResult(ActionResult result);

    // display after scenes are completed
    void showSceneWrap(SceneWrapResult wrap);

    // game end show winners
    void showWinners(List<Player> winners);

    // get player input gui and terminal need this
    String requestInput(String prompt);

    // wait for a user to do something
    String waitForAction(List<String> availableActions);

    // get what currency the user wants
    String requestUpgradeCurrency();

    // get the upgrade level from a user
    int requestUpgradeLevel(int currentRank);

    // let them decide what role they want, should we display only roles they can
    // take or any role?
    String requestRoleChoice(List<Part> availableParts);

    // get where they want to move
    String requestMoveDestination(List<String> neighbors);

    // update the day display
    void updateDay(int day);
}

public class View {
    // Classic terminal view
    // override every method in our interface
    public static class terminalView implements GameView {
        // terminal will just use this scanner now instead of user input
        Scanner scanner = new Scanner(System.in);

        @Override
        public void displayMessage(String message) {
            System.out.println(message);
        }

        @Override
        public void initPlayers(List<Player> players) {
            // unnecesary for terminal
        }

        @Override
        public void updatePlayerLocation(Player player) {
            // unnecesary for terminal
        }

        @Override
        public void updateBoard(List<Set> sets) {
            // unnecesary for terminal
        }

        @Override
        public void updatePlayerStats(Player p) {
            // unnecesary for terminal
        }

        @Override
        public void showWinners(List<Player> winners) {
            // print the player names, winners is already sorted
            for (Player player : winners) {
                System.out.println(player.getname());
            }
        }

        @Override
        // unnecesary , does this still work for terminal version? Do we need to test
        // this?
        public String requestInput(String prompt) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void showActionResult(ActionResult result) {
            // message says failure or success depending on the action result
            displayMessage("Action outcome: " + result.message);
        }

        @Override
        public void showSceneWrap(SceneWrapResult wrap) {
            // show the user the scene has wrapped
            displayMessage("Scene Wrapped!");
        }

        @Override
        // terminal version used to use User Input
        public String waitForAction(List<String> availableActions) {
            return "";
        }

        // Inside your View Interface
        @Override
        public String requestMoveDestination(List<String> neighbors) {
            // display neighbors
            displayMessage("Neighbors:");
            for (int i = 0; i < neighbors.size(); i++) {
                displayMessage((i + 1) + ": " + neighbors.get(i));
            }

            // make the user pick
            int choice = pickPlayerArgs(1, neighbors.size());

            // index into the right spot
            return neighbors.get(choice - 1);
        }

        // pick player
        public int pickPlayerArgs(int min, int max) {
            while (true) {
                try {
                    // read user input,
                    String input = scanner.nextLine().trim();
                    int choice = Integer.parseInt(input);

                    if (choice >= min && choice <= max) {
                        return choice;
                    } else {
                        displayMessage("Invalid choice. Please pick a number between " + min + " and " + max + ".");
                    }
                } catch (Exception e) {
                    displayMessage("Please provide a valid Integer between " + min + " and " + max + ".");
                }
            }
        }

        @Override
        public String requestUpgradeCurrency() {
            // make the player pick a currency
            displayMessage("Select currency: 1. Dollars, 2. Credits, 3. CANCEL");
            int choice = pickPlayerArgs(1, 3);
            if (choice == 1)
                return "dollar";
            if (choice == 2)
                return "credit";
            // if they dont want to buy the upgrade
            return "cancel";
        }

        @Override
        public int requestUpgradeLevel(int currentRank) {
            // get the level
            displayMessage("Pick Upgrade Level between current rank: " + currentRank + " and 6.");
            return pickPlayerArgs(currentRank, 6);
        }

        @Override
        public String requestRoleChoice(List<Part> availableParts) {
            // as long as available parts is implemented correctly this will work
            if (availableParts.isEmpty()) {
                displayMessage("No parts available to take.");
                return null;
            }
            // otherwise show to the player what they can pic ffrom
            displayMessage("Available Parts:");

            for (int d = 0; d < availableParts.size(); d++) {
                // if they pick d
                Part player = availableParts.get(d);
                // #: playername + (Level # )
                displayMessage((d + 1) + ": " + player.getName() + " (Level " + player.getLevel() + ")");
            }
            // adding cancel as an option teminal version has had minimal testing
            displayMessage((availableParts.size() + 1) + ": CANCEL");

            int choice = pickPlayerArgs(1, availableParts.size() + 1);
            if (choice == availableParts.size() + 1) {
                return null;
            }
            // return the part name
            return availableParts.get(choice - 1).getName();
        }

        @Override
        public void updateDay(int day) {
            // unnecessary for terminal
        }
    }

    // Implement the GUI View
    public static class guiView extends JFrame implements GameView {

        // JLabels
        private JLayeredPane boardPane;
        private JLabel boardLabel;
        private JLabel dayLabel;
        private JLabel mLabel;
        private JLabel playerLabel;
        private JTextArea logArea;

        // JButtons
        JButton bAct;
        JButton bRehearse;
        JButton bMove;

        // dice
        private java.util.Map<Integer, JLabel> playerLabels = new java.util.HashMap<>();
        // shot images
        private java.util.Map<String, List<JLabel>> shotLabels = new java.util.HashMap<>();
        // card images
        private java.util.Map<String, JLabel> cardLabels = new java.util.HashMap<>();
        // Action buttons tracking
        private java.util.Map<String, JButton> actionButtons = new java.util.HashMap<>();

        // This queue links Swing button clicks back to the blocking Controller logic
        private BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();

        // BoardLayersListeners
        public guiView() {
            // Set the title of the JFrame
            super("Deadwood");
            // Set the exit option for the JFrame
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create the JLayeredPane to hold the display, cards, dice and buttons
            boardPane = getLayeredPane();

            // Load board image
            ImageIcon icon = new ImageIcon("gui/board.jpg");
            boardLabel = new JLabel();
            boardLabel.setIcon(icon);
            boardLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
            // Add the board ot the lowest layer
            // new Integer(0)
            boardPane.add(boardLabel, Integer.valueOf(0));
            boardLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
            setSize(icon.getIconWidth() + 200, icon.getIconHeight());

            // icon positions
            int uiX = icon.getIconWidth() + 10;

            // Status Labels
            dayLabel = new JLabel("Day: 1");
            dayLabel.setFont(new Font("Western", Font.BOLD, 16));
            dayLabel.setBounds(uiX, 10, 200, 20);
            boardPane.add(dayLabel, Integer.valueOf(2));

            playerLabel = new JLabel("Current Player: ");
            playerLabel.setFont(new Font("Western", Font.BOLD, 14));
            playerLabel.setBounds(uiX, 35, 180, 80);
            boardPane.add(playerLabel, Integer.valueOf(2));

            // Action Buttons

            // MAKING BUTTONS Individually

            // Move Button
            JButton bMove = new JButton("Move");
            bMove.setBackground(Color.white);
            bMove.setBounds(uiX, 120, 120, 25);
            bMove.addActionListener(e -> inputQueue.offer("Move"));
            boardPane.add(bMove, Integer.valueOf(2));
            actionButtons.put("Move", bMove);

            // Act Button
            JButton bAct = new JButton("Act");
            bAct.setBackground(Color.white);
            bAct.setBounds(uiX, 155, 120, 25);
            bAct.addActionListener(e -> inputQueue.offer("Act"));
            boardPane.add(bAct, Integer.valueOf(2));
            actionButtons.put("Act", bAct);

            // Rehearse Button
            JButton bRehearse = new JButton("Rehearse");
            bRehearse.setBackground(Color.white);
            bRehearse.setBounds(uiX, 190, 120, 25);
            bRehearse.addActionListener(e -> inputQueue.offer("Rehearse"));
            boardPane.add(bRehearse, Integer.valueOf(2));
            actionButtons.put("Rehearse", bRehearse);

            // Take Role Button
            JButton bTakeRole = new JButton("Take Role");
            bTakeRole.setBackground(Color.white);
            bTakeRole.setBounds(uiX, 225, 120, 25);
            bTakeRole.addActionListener(e -> inputQueue.offer("Take Role"));
            boardPane.add(bTakeRole, Integer.valueOf(2));
            actionButtons.put("Take Role", bTakeRole);

            // Upgrade Rank Button
            JButton bUpgradeRank = new JButton("Upgrade Rank");
            bUpgradeRank.setBackground(Color.white);
            bUpgradeRank.setBounds(uiX, 260, 120, 25);
            bUpgradeRank.addActionListener(e -> inputQueue.offer("Upgrade Rank"));
            boardPane.add(bUpgradeRank, Integer.valueOf(2));
            actionButtons.put("Upgrade Rank", bUpgradeRank);

            // End Turn Button
            JButton bEndTurn = new JButton("End Turn");
            bEndTurn.setBackground(Color.white);
            bEndTurn.setBounds(uiX, 295, 120, 25);
            bEndTurn.addActionListener(e -> inputQueue.offer("End Turn"));
            boardPane.add(bEndTurn, Integer.valueOf(2));
            actionButtons.put("End Turn", bEndTurn);

            // disable all initially
            for (JButton btn : actionButtons.values()) {
                btn.setEnabled(false);
            }

            // tetting up an area to display text
            logArea = new JTextArea();
            logArea.setEditable(false);
            logArea.setLineWrap(true);
            logArea.setWrapStyleWord(true);

            // scrollable text bounds
            JScrollPane scrollPane = new JScrollPane(logArea);
            scrollPane.setBounds(uiX, 330, 220, 400);
            boardPane.add(scrollPane, Integer.valueOf(2));

            setLocationRelativeTo(null); // Center window
            setVisible(true);
        }

        @Override
        public void displayMessage(String text) {
            SwingUtilities.invokeLater(() -> {
                logArea.append(text + "\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            });
        }

        @Override
        public void updatePlayerStats(Player player) {
            SwingUtilities.invokeLater(() -> {
                // switching to html otherwiser it wont fit on page
                playerLabel.setText("<html>Current Player: " + player.getname() +
                        "<br>$" + player.getDollars() + ", C" + player.getCredits() +
                        "<br>Rank " + player.getRank() + ", Rehearsal " + player.getRehearsalChips() +
                        "<br>Score " + player.getScore() + "</html>");
                // ensure the player is in the correct location
                // this is bugged either here or in the card locations, when players take on
                // card roles
                // they do not get placed into the righ position
                updatePlayerLocation(player);
            });
        }

        @Override
        public void initPlayers(List<Player> players) {
            // add players and labels
            SwingUtilities.invokeLater(() -> {
                for (Player player : players) {
                    JLabel label = new JLabel();
                    playerLabels.put(player.getPlayerID(), label);
                    boardPane.add(label, Integer.valueOf(3));
                }
                for (Player player : players) {
                    // update location the update function might be bugged
                    updatePlayerLocation(player);
                }
            });
        }

        @Override
        public void updateBoard(List<Set> sets) {
            SwingUtilities.invokeLater(() -> {
                for (Set set : sets) {
                    // update scene cards
                    Scene currentScene = set.getCurrentScene();
                    JLabel cardLabel = cardLabels.get(set.getName());

                    // not null
                    if (currentScene != null) {
                        if (cardLabel == null) {
                            cardLabel = new JLabel();
                            cardLabels.put(set.getName(), cardLabel);
                            boardPane.add(cardLabel, Integer.valueOf(1));
                        }

                        // this doesn towrk
                        String imgName = currentScene.isFlipped() ? currentScene.getImg() : "Cardback.png";
                        // file path with gui folder
                        String imgPath = "gui/Card/" + imgName;
                        ImageIcon cIcon = new ImageIcon(imgPath);
                        cardLabel.setIcon(cIcon);
                        // set areas
                        java.util.HashMap<String, String> area = set.getArea();
                        if (area != null && area.containsKey("x")) {
                            // try to find x,y
                            try {
                                int x = Integer.parseInt(area.get("x"));
                                int y = Integer.parseInt(area.get("y"));
                                cardLabel.setBounds(x, y, cIcon.getIconWidth(), cIcon.getIconHeight());
                            } catch (Exception e) {
                            }
                        }
                        // check this function
                        cardLabel.setVisible(true);
                    } else {
                        if (cardLabel != null)
                            cardLabel.setVisible(false);
                    }

                    // Update Shots, when we only have one shot left the game glichets and breaks
                    List<JLabel> setShots = shotLabels.get(set.getName());
                    if (setShots == null && set.getTakes() != null) {
                        setShots = new java.util.ArrayList<>();
                        shotLabels.put(set.getName(), setShots);

                        // add takes to hashmap
                        java.util.HashMap<String, java.util.HashMap<String, String>> takes = set.getTakes();
                        for (int i = 1; i <= takes.size(); i++) {
                            JLabel shotLabel = new JLabel();
                            // grab shots
                            ImageIcon sIcon = new ImageIcon("gui/shot.png");
                            shotLabel.setIcon(sIcon);
                            // add labels
                            boardPane.add(shotLabel, Integer.valueOf(1));

                            java.util.HashMap<String, String> tArea = takes.get(String.valueOf(i));
                            if (tArea != null && tArea.containsKey("x")) {
                                try {
                                    // parse locations
                                    int sx = Integer.parseInt(tArea.get("x"));
                                    int sy = Integer.parseInt(tArea.get("y"));
                                    shotLabel.setBounds(sx, sy, sIcon.getIconWidth(), sIcon.getIconHeight());
                                } catch (Exception e) {
                                    // nothing
                                }
                            }
                            // add shots
                            setShots.add(shotLabel);
                        }
                    }

                    // get remaining takes
                    if (setShots != null) {
                        int remaining = set.getRemainingTakes();
                        for (int i = 0; i < setShots.size(); i++) {
                            setShots.get(i).setVisible(i >= remaining);
                        }
                    }
                }
            });
        }

        // amy's all functions below here
        @Override
        // Draw the player's die into the correct spot
        public void updatePlayerLocation(Player player) {
            // get label for specific player
            int id = player.getPlayerID();
            JLabel label = playerLabels.get(id);
            // if theres no label or player info, stop
            if (label == null || player.getLocation() == null) {
                return;
            }

            // create image path
            String color = player.getColor();
            int rank = player.getRank();
            String path = "gui/Dice/" + player.getColor() + player.getRank() + ".png";

            // update the icon on the label
            ImageIcon icon = new ImageIcon(path);
            label.setIcon(icon);
            label.setSize(icon.getIconWidth(), icon.getIconHeight());

            // figure out where the player should be
            java.util.HashMap<String, String> area;

            if (player.getWorkingRole() && player.getPart() != null) {
                area = player.getPart().getArea();
            } else {
                area = player.getLocation().getArea();
            }

            if (area != null) {
                int x = Integer.parseInt(area.get("x"));
                int y = Integer.parseInt(area.get("y"));
                int offset = (id - 1) * 20;
                if (player.getWorkingRole() == true) {
                    offset = 0; // snap exact for roles
                    if (player.getPart() != null && player.getPart().getStarringRole()) {
                        JLabel cardLabel = cardLabels.get(player.getLocation().getName());
                        if (cardLabel != null) {
                            x += cardLabel.getX();
                            y += cardLabel.getY();
                        }
                    }
                }
                label.setLocation(x + offset, y + offset);
            }
        }

        @Override
        public void showWinners(List<Player> winners) {
            // show all players.. winners
            String message = "The winners are: \n";
            for (Player player : winners) {
                message = message + player.getName() + "\n";
            }

            JOptionPane.showMessageDialog(this, message);
        }

        @Override
        public String requestInput(String prompt) {
            // pop box that prompts user to answer a question
            String input = JOptionPane.showInputDialog(this, prompt);
            if (input == null) {
                return "";
            }
            return input.trim();
        }

        @Override
        public String waitForAction(List<String> availableActions) {
            SwingUtilities.invokeLater(() -> {
                for (String action : actionButtons.keySet()) {
                    JButton btn = actionButtons.get(action);
                    btn.setEnabled(availableActions != null && availableActions.contains(action));
                }
            });

            try {
                String action = inputQueue.take();
                SwingUtilities.invokeLater(() -> {
                    for (JButton btn : actionButtons.values()) {
                        btn.setEnabled(false);
                    }
                });
                return action;
            } catch (Exception e) {
                return "";
            }
        }

        @Override
        public void showActionResult(ActionResult result) {
            System.out.println("GUI Stub: showActionResult");
        }

        @Override
        public void showSceneWrap(SceneWrapResult wrap) {
            // get name of the set where the scene finished
            String setName = wrap.setName;
            String message = "Scene at " + setName + " has wrapped!";
            // alert window
            JOptionPane.showMessageDialog(this, message, "Scene Wrapped", JOptionPane.INFORMATION_MESSAGE);
            displayMessage("Scene wrapped at " + setName);
        }

        @Override
        public String requestUpgradeCurrency() {
            // create labels for buttons
            Object[] options = { "Dollars", "Credits", "Cancel" };
            int choice = JOptionPane.showOptionDialog(this, "Select currency for upgrade:", "Upgrade Currency",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            // 0 is dollars, 1 is credits
            if (choice == 0) {
                return "dollar";
            }
            if (choice == 1) {
                return "credit";
            }
            return "cancel";
        }

        @Override
        public int requestUpgradeLevel(int currentRank) {
            // creating a list of available levels, if you're rank 2, see options for
            // 2,3,4,5,6
            String[] levels = new String[7 - currentRank];
            for (int i = 0; i < levels.length; i++) {
                int levelNumber = currentRank + i;
                levels[i] = String.valueOf(levelNumber);
            }

            String choice = (String) JOptionPane.showInputDialog(this,
                    "Pick Upgrade Level (Current rank: " + currentRank + "):", "Upgrade Level",
                    JOptionPane.QUESTION_MESSAGE, null, levels, levels[0]);

            if (choice != null) {
                return Integer.parseInt(choice);
            }
            return currentRank;
        }

        @Override
        public String requestRoleChoice(List<Part> availableParts) {
            // check if any jobs are available
            if (availableParts.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No parts available to take.");
                return null;
            }
            // create list of text options for menu
            String[] options = new String[availableParts.size() + 1];
            for (int i = 0; i < availableParts.size(); i++) {
                Part p = availableParts.get(i);
                options[i] = p.getName() + " (Level " + p.getLevel() + ")";
            }
            options[availableParts.size()] = "Cancel";

            // show dropdown menu
            String choice = (String) JOptionPane.showInputDialog(this,
                    "Select a role to take:", "Take Role", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choice != null) {
                if (choice.equals("Cancel") == false) {
                    int cutPoint = choice.lastIndexOf(" (Level");
                    return choice.substring(0, cutPoint);
                }
            }
            return null;
        }

        @Override
        public String requestMoveDestination(List<String> neighbors) {
            // check if there is available rooms
            if (neighbors == null || neighbors.isEmpty()) {
                JOptionPane.showMessageDialog(this, "You are stuck! No rooms available.");
                return null;
            }

            // prepare list of rooms for menu
            String[] options = new String[neighbors.size() + 1];
            for (int i = 0; i < neighbors.size(); i++) {
                options[i] = neighbors.get(i);
            }
            options[neighbors.size()] = "Cancel";

            String choice = (String) JOptionPane.showInputDialog(this,
                    "Select a destination to move to:", "Move",
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choice != null) {
                if (choice.equals("Cancel") == false) {
                    return choice;
                }
            }
            return null;
        }

        @Override
        public void updateDay(int day) {
            SwingUtilities.invokeLater(() -> {
                if (dayLabel != null) {
                    dayLabel.setText("Day: " + day);
                }
            });
        }
    }
}
