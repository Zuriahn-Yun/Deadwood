import java.util.*;

/**
 * The Set Class manages the set cards that will be pulled from cards.xml.
 * 
 */
public class Set {
    private String name;
    private List<String> neighbors;
    private Scene currentScene;
    private HashMap<String, String> area = new HashMap<>();
    // Setup as a hashmap incase any take needed to be accessed
    private HashMap<String, HashMap<String, String>> takes = new HashMap<>();
    private int takeCount;
    private int remainingTakes;
    private ArrayList<Part> parts = new ArrayList<>();

    public void setTotalTakes(int count) {
        this.takeCount = count;
        this.remainingTakes = count;
    }

    public int getRemainingTakes() {
        return remainingTakes;
    }

    public Set(String name, List<String> neighbors) {
        this.name = name;
        this.neighbors = neighbors;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
        if (this.currentScene != null) {
            // false for new day
            this.currentScene.setFlipped(false);
        }
    }

    public Scene removeTake() {
        if (remainingTakes > 0) {
            remainingTakes--;
            System.out.println("Shot completed! Remaining takes: " + remainingTakes);

            if (remainingTakes == 0) {
                return wrapScene();
            }
            
        }
        return null;
    }

    private Scene wrapScene() {
        System.out.println("Scene " + currentScene.getSceneName() + " is finished.");
        Scene wrappedScene = this.currentScene;
        this.currentScene = null;
        return wrappedScene;
    }

    public boolean isAdjacent(String destination) {
        return neighbors.contains(destination);
    }

    // getters
    public HashMap<String, String> getArea() {
        return area;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public ArrayList<Part> getParts() {
        return parts;
    }

    public HashMap<String, HashMap<String, String>> getTakes() {
        return takes;
    }

    public String getName() {
        return name;
    }

    public List<String> getNeighbors() {
        return neighbors;
    }

    // setters
    public void setParts(ArrayList<Part> parts) {
        this.parts = parts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTakes(HashMap<String, HashMap<String, String>> takes) {
        this.takes = takes;
    }

    public void setNeighbors(List<String> neighbors) {
        this.neighbors = neighbors;
    }

    public void setArea(HashMap<String, String> area) {
        this.area = area;
    }
}

class Office {
    // office is a set, but it also has upgrades and no parts
    private Set set;
    private ArrayList<Upgrade> upgrades;

    public Office(Set set, ArrayList<Upgrade> upgrades) {
        this.set = set;
        this.upgrades = upgrades;

    }

    // getters
    public Set getSet() {
        return set;
    }

    public ArrayList<Upgrade> getUpgrades() {
        return upgrades;
    }

}