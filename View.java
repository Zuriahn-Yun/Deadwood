import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

/**
 * This File is our View, Game View depicts all the functions
 * that must be present in every type of view we create.
 */
interface GameView {
    // Display any arbitrary message
    void displayMessage(String message);

    // Display Updated Player Stats
    void updatePlayerStats(Player player);

    // Show Result of an action
    void showActionResult(ActionResult result);

    // Show the wrapping of a scene
    void showSceneWrap(SceneWrapResult wrap);

    // Show the winners at the end of the game
    void showWinners(List<Player> winners);

    // requesti user input for name and any others
    String requestInput(String prompt);

    // wait for user response
    String waitForAction();

    // Get upgrade currency from user
    String requestUpgradeCurrency();

    // get upgrade rank level
    int requestUpgradeLevel(int currentRank);

    // get what role the user wants
    String requestRoleChoice(List<Part> availableParts);

    // get where the user wants to move
    String requestMoveDestincation(List<String> neighbors);

}

public class View {
    // Implement the Terminal View
    public static class terminalView implements GameView {
        @Override
        public void displayMessage(String message) {

        }

        @Override
        public void updatePlayerStats(Player player) {
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

        @Override
        public void showWinners(List<Player> winners) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String requestInput(String prompt) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String waitForAction() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String requestUpgradeCurrency() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int requestUpgradeLevel(int currentRank) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String requestRoleChoice(List<Part> availableParts) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String requestMoveDestincation(List<String> neighbors) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    // Implement the GUI View
    public static class guiView extends JFrame implements GameView {
        

        // JLabels
        JLabel boardlabel;
        JLabel cardlabel;
        JLabel playerlabel;
        JLabel mLabel;
        // JButtons
        JButton bAct;
        JButton bRehearse;
        JButton bMove;
        // JLayered Pane
        JLayeredPane bPane;

        // Constructor
        public guiView() {
        
          // Set the title of the JFrame
       super("Deadwood");
       // Set the exit option for the JFrame
       setDefaultCloseOperation(EXIT_ON_CLOSE);
      
       // Create the JLayeredPane to hold the display, cards, dice and buttons
       bPane = getLayeredPane();
    
       // Create the deadwood board
       boardlabel = new JLabel();
       ImageIcon icon =  new ImageIcon("gui/board.jpg");
       boardlabel.setIcon(icon); 
       boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
      
       // Add the board to the lowest layer
       bPane.add(boardlabel, new Integer(0));
      
       // Set the size of the GUI
       setSize(icon.getIconWidth()+200,icon.getIconHeight());
       
       // Add a scene card to this room
       cardlabel = new JLabel();
       ImageIcon cIcon =  new ImageIcon("01.png");
       cardlabel.setIcon(cIcon); 
       cardlabel.setBounds(20,65,cIcon.getIconWidth()+2,cIcon.getIconHeight());
       cardlabel.setOpaque(true);
      
       // Add the card to the lower layer
       bPane.add(cardlabel, new Integer(1));
       
      

    
       // Add a dice to represent a player. 
       // Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
       playerlabel = new JLabel();
       ImageIcon pIcon = new ImageIcon("r2.png");
       playerlabel.setIcon(pIcon);
       //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());  
       playerlabel.setBounds(114,227,46,46);
       playerlabel.setVisible(false);
       bPane.add(playerlabel,new Integer(3));
      
       // Create the Menu for action buttons
       mLabel = new JLabel("MENU");
       mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
       bPane.add(mLabel,new Integer(2));

       // Create Action buttons
       bAct = new JButton("ACT");
       bAct.setBackground(Color.white);
       bAct.setBounds(icon.getIconWidth()+10, 30,100, 20);
       bAct.addMouseListener(new boardMouseListener());
       
       bRehearse = new JButton("REHEARSE");
       bRehearse.setBackground(Color.white);
       bRehearse.setBounds(icon.getIconWidth()+10,60,100, 20);
       bRehearse.addMouseListener(new boardMouseListener());
       
       bMove = new JButton("MOVE");
       bMove.setBackground(Color.white);
       bMove.setBounds(icon.getIconWidth()+10,90,100, 20);
       bMove.addMouseListener(new boardMouseListener());

       // Place the action buttons in the top layer
       bPane.add(bAct, new Integer(2));
       bPane.add(bRehearse, new Integer(2));
       bPane.add(bMove, new Integer(2));
  }
      
        public String requestMoveDestination(List<String> neighbors){
            throw new UnsupportedOperationException("not supported yet");
        }
        
  // This class implements Mouse Events
  
  class boardMouseListener implements MouseListener{
  
      // Code for the different button clicks
      public void mouseClicked(MouseEvent e) {
         
         if (e.getSource()== bAct){
            playerlabel.setVisible(true);
            System.out.println("Acting is Selected\n");
         }
         else if (e.getSource()== bRehearse){
            System.out.println("Rehearse is Selected\n");
         }
         else if (e.getSource()== bMove){
            System.out.println("Move is Selected\n");
         }         
      }
      public void mousePressed(MouseEvent e) {
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
}
    }
}

