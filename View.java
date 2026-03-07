/**
 * This File is our View, Game View depicts all the functions
 * that must be present in every type of view we create.
 * 
 * 
*/

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

interface GameView {
    void display(String text);
    int getSelection(String prompt, int min, int max);
    String getStringInput(String prompt);
}

public class View {

    public static class terminalView implements GameView {
        @Override
        public void display(String text) {
            System.out.println(text);
        }
        public int getSelection(String prompt, int min, int max) { return 0; }
        public String getStringInput(String prompt) { return "test"; }
    }

    //guiView extends JFrame
    public static class guiView extends JFrame implements GameView {
        //to stack board image, scene cards, and player dice
        private JLayeredPane layeredPane;
        private JPanel statsPanel; 
        private JTextArea statusTextArea;
        
        //commponents for the Stats Panel
        private JLabel nameLabel, rankLabel, cashLabel, creditLabel, practiceLabel;
        public guiView() {
            setTitle("Deadwood");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(1200, 900); // Typical size for the Deadwood board
            setLayout(new BorderLayout());

            layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(800, 600));

            statsPanel = new JPanel();
            statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
            statsPanel.setPreferredSize(new Dimension(200, 900));
            statsPanel.setBorder(BorderFactory.createTitledBorder("Player Stats"));

            statusTextArea = new JTextArea(10, 20);
            statusTextArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(statusTextArea);
            
            // Layout components
            this.add(layeredPane, BorderLayout.CENTER);
            this.add(statsPanel, BorderLayout.EAST);
            this.add(scrollPane, BorderLayout.SOUTH);

            this.setVisible(true);
        }

        @Override
        public void display(String text) {
            statusTextArea.append(text + "\n");
            statusTextArea.setCaretPosition(statusTextArea.getDocument().getLength());
        }

        @Override
        public int getSelection(String prompt, int min, int max) {
            // You can use JOptionPane for simple GUI inputs
            String input = JOptionPane.showInputDialog(this, prompt);
            return Integer.parseInt(input);
        }

        @Override
        public String getStringInput(String prompt) {
            return JOptionPane.showInputDialog(this, prompt);
        }
    }

    
}
// interface GameView{
//     void display(String text);
//     int getSelection(String prompt, int min, int max);
//     String getStringInput(String prompt);
// }
// public class View{
// // Implement the Terminal View 
// public static class terminalView implements GameView{
//     @Override
//     public void display(String text){
//         System.out.println(text);
//     }

//     public int getSelection(String prompt,int min,int max){
//         return 0;
//     }
//     public String getStringInput(String prompt){
//         return "test";
//     }
// }

// // Implement the GUI View
// public static class guiView extends JFrame implements GameView {
//     private JLabel boardLabel;
//     private JPanel statsPanel; // For Rank, Wealth, Credits [cite: 25]
//     private JLayeredPane layeredPane;

//     public guiView() {
//         setTitle("Deadwood");
//         setDefaultCloseOperation(EXIT_ON_CLOSE);
        
//         layeredPane = new JLayeredPane();
//         // Load board.png as a JLabel background
//         // Add layers for Cards, Shot Counters, and Players [cite: 27]
        
//         statsPanel = new JPanel(); // Display info like Rank/Credits here [cite: 24]
//         this.add(statsPanel, BorderLayout.EAST);
//         this.add(layeredPane, BorderLayout.CENTER);
//         this.setVisible(true);
//     }

//     @Override
//     public void display(String text) {
//         // Instead of System.out, update a status JTextArea
//         statusTextArea.append(text + "\n");
//     }
// }
// }

