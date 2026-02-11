import java.util.*;

/**
 * The Set Class manages the set cards that will be pulled from cards.xml.
 * 
 */
public class Set {
    private String name;
    private List<String> neighbors;
    HashMap area = new HashMap<>();
    HashMap takes = new HashMap<>();
    // Parts
    // name(str),level(int),area(x,y,h,w),line(str)
    

    public Set(String name, List<String> neighbors) {
        this.name = name;
        this.neighbors = neighbors;
    }

    public boolean isAdjacent(String destination) {
        return neighbors.contains(destination);
    }

    // getters
    public String getName() {
        return name;
    }

    public List<String> getNeighbors() {
        return neighbors;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setNeighbors(List<String> neighbors) {
        this.neighbors = neighbors;
    }
}