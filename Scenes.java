import java.util.*;

/**
 * TODO Parse cards.xml
 * MANAGES CARDS, BUDGET, AND ROLES AVAILABLE
 */
public class Scenes {
    private String sceneName;
    private String description;
    private int sceneNumber;
    private int budget;
    private List<Role> roles = new ArrayList<>();
    private boolean isFlipped = false;

    public Scenes(String name, int number, int budget, String description) {
        this.sceneName = name;
        this.sceneNumber = number;
        this.budget = budget;
        this.description = description;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    // Getters
    public String getSceneName() { return sceneName; }
    public int getBudget() { return budget; }
    public List<Role> getRoles() { return roles; }
    public boolean isFlipped() { return isFlipped; }

    // Setters
    public void setFlipped(boolean flipped) { this.isFlipped = flipped; }
    
    public Role getRoleByName(String name) {
        for (Role r : roles) {
            if (r.getName().equalsIgnoreCase(name)) {
                return r;
            }
        }
        return null;
    }
}