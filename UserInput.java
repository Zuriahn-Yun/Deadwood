import java.util.*;

/**
 * This is a class designed to take in user input, currently only for terminal
 * view.
 */
public class UserInput {
    // Singleton scanner to prevent multiple System.in streams closing each other
    private static Scanner scanner = new Scanner(System.in);

    // UserInput should solely be for getting string data from the user and
    // returning it.
    // We removed the static LocationManager and GameManager variables.
    public String getInput() {
        if (!scanner.hasNextLine())
            return "";
        String input = scanner.nextLine().trim();
        return input;
    }

    public static Scanner getScanner() {
        return scanner;
    }
}