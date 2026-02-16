import java.util.*;

/**
 * The Set Class manages the set cards that will be pulled from cards.xml.
 * 
 */
public class Set {
    private String name;
    private List<String> neighbors;
    private HashMap<String, String> area = new HashMap<>();
    private HashMap<String, HashMap<String, String>> takes = new HashMap<>();
    private ArrayList<Part> parts = new ArrayList<>();

    public Set(String name, List<String> neighbors) {
        this.name = name;
        this.neighbors = neighbors;
    }

    public boolean isAdjacent(String destination) {
        return neighbors.contains(destination);
    }

    // getters
    public HashMap<String, String> getArea() {
        return area;
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