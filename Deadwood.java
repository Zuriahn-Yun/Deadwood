import java.util.*;

public class Deadwood{

    // Cannot be name system our causes System.out.prinln to error due to reference bug 
    public class Game{

    }
    public class Set{

    }
    public class Player{

    }

    // Dice Class
    public class Dice{
        // Create a Random Instance
        private static Random random = new Random();
        public static int roll(){
            return random.nextInt(6) + 1;
        }
    }

    public static void main(String[] args) {
        System.out.println("Start Game");
        Deadwood game = new Deadwood();
        Dice dice = game.new Dice();
        System.out.println(dice.roll());

    }

}