import java.util.*;

/**
 * 
 * Manages Cards Budget and Scenes
 */
public class Scene {
    private String sceneName;
    private String description;
    private int sceneNumber;
    private int budget;
    private List<Part> parts = new ArrayList<>();
    private boolean isFlipped = false;

    public Scene(String name, int number, int budget, String description) {
        this.sceneName = name;
        this.sceneNumber = number;
        this.budget = budget;
        this.description = description;
    }

    public void addPart(Part part){
        this.parts.add(part);
    }

    // Getters
    public String getSceneName() {
        return sceneName;
    }
    public int getSceneNumber(){
        return sceneNumber;
    }

    public int getBudget() {
        return budget;
    }

    public boolean isFlipped() {
        return isFlipped;
    }
    public String getDescription(){
        return description;
    }
    public List<Part> getParts(){
        return parts;
    }    
    // Setters
    public void setFlipped(boolean flipped) {
        this.isFlipped = flipped;
    }

    public Part getPartByName(String name) {
        for (Part p : parts) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}