import java.util.HashMap;

/*
 * To hold parts and upgrades from board.xml
 */
public class Part {
    private String name;
    private int level;
    private HashMap<String,String> area;
    private String line;
    // only used if it if a

    // setters
    public void setArea(HashMap<String, String> area) {
        this.area = area;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setLine(String line) {
        this.line = line;
    }
    public void setName(String name) {
        this.name = name;
    }
    // getters
    public HashMap<String, String> getArea() {
        return area;
    }
    public int getLevel() {
        return level;
    }
    public String getLine() {
        return line;
    }
    public String getName() {
        return name;
    }
}
