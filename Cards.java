import java.util.*;

/**
 * MANAGES CARDS, BUDGET, AND ROLES AVAILABLE
 */
public class Cards {
    private String name;
    private int budget;
    private int sceneNumber;
    private String sceneDescription;
    private ArrayList<Part> parts = new ArrayList<>();

    // constructor, getters, setters
    public Cards(String name, int budget, int sceneNumber, String description) {
        this.name = name;
        this.budget = budget;
        this.sceneNumber = sceneNumber;
        this.sceneDescription = description;
    }

    public void addRole(Part part) {
        this.parts.add(part);
    }

    public int getBudget() { return budget; }
    public ArrayList<Part> getParts() { return parts; }
    public String getName() { return name; }
}