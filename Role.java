// import java.util.*;

/**
 * The Role class manages
 * 
 */
public class Role {
    private String name;
    private int rank;
    private boolean occupied = false;

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public boolean getOccupied() {
        return occupied;
    }
}