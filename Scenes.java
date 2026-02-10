import java.util.*;
/**
 * Scene class is meant to manage
 * 
 */
public class Scenes {
    private ArrayList<String> roles = new ArrayList<>();
    private int budget;

    // Setters
    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    // Getters
    public int getBudget() {
        return budget;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }
}