// import java.util.*;

/**
 * 
 */
public class Role {
    private String name;
    private int rank;
    private boolean occupied = false;
    boolean isExtra;
    String line;
    Integer level;
    public Role(String name, int level, String line, boolean isExtra){
        this.name = name;
        this.level = level;
        this.line = line;
        this.isExtra = isExtra;
    }

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