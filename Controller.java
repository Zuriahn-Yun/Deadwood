import java.util.List;

public class Controller {
    // will contain reference to view and to Game Manager
    // The controller talks to the Model(Game Mangers) view reflects whats happening in the model
    // this will interact with the user -> model -> return to controller -> deliver to view
    GameManager gameManager; // this is our model
    GameView view; // view
    UserInput userInput; // this is how we manage commands and grab user input
    public Controller(GameManager gameManager,GameView view,UserInput userInput) {
        this.gameManager = gameManager;
        this.view = view;
        this.userInput = userInput;
    }
    
    // We need to do this to start the game
    public void initializeNumberOfPlayers(){
        while (true){
        view.display("Please Enter Number of Players:");
        String input = userInput.getInput();
        try {
            int num = Integer.parseInt(input);
            if(num >= 2 && num <= 8){
                // Pass info to the Game Manager
                gameManager.setNumberofPlayers(num);
                gameManager.setupDiffGroupSizes();
                view.display("Starting Game With: " + num + " Players");
            }
            view.display("Invalid range! Please enter 2-8 players.");
            } catch (Exception e) {
            view.display("That's not a number. Try again!.");
            }
        }
    }

    // Ask User to Pick How they will Pay
    public void pickCurrency(){
        List<String> options = gameManager.getCurrencyOptions();
        for(int i = 0; i < options.size();i++){
            System.out.println(options.get(i));
        }
        
    }
    public void playerTurn(Player p){
        
    }

    public static void main(String[] args) {
        System.out.println("Testing Controller.");
        GameView view = new View.terminalView();
        try {
            // We want to start with a controller
            // this needs access to the game manager, view and a userInput object
            UserInput userInput = new UserInput();
            // we need to make a model object
            Deck deck = new Deck();
            Board board = new Board();
            CastingOffice castingOffice = new CastingOffice();
            MoveManager moveManager = new MoveManager(board.getSets());
            GameManager gameManager = new GameManager(deck, board, castingOffice, moveManager);
            // Our controller has access to our Model, View and Grabbing User Input 
            Controller controller = new Controller(gameManager,view, userInput);
            // Initializing Number of Players and starting game
            controller.initializeNumberOfPlayers();
            // Now the game has started what do we do? Player turn time 
            
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
