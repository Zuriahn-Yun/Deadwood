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
    private String img;
    private List<Part> parts = new ArrayList<>();
    private boolean isFlipped = false;

    public Scene(String name, int number, int budget, String description, String img) {
        this.sceneName = name;
        this.sceneNumber = number;
        this.budget = budget;
        this.description = description;
        this.img = img;
    }

    public void addPart(Part part) {
        this.parts.add(part);
    }

    // Getters
    public String getSceneName() {
        return sceneName;
    }

    public int getSceneNumber() {
        return sceneNumber;
    }

    public int getBudget() {
        return budget;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
    }

    public List<Part> getParts() {
        return parts;
    }

    // Setters
    public void setImg(String img) {
        this.img = img;
    }

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