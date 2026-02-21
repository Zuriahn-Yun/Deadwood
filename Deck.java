
import java.io.File;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 *
 */
public class Deck {

    private ArrayList<Scene> deck = new ArrayList<>();

    // PARSE CARDS (SCENES)
    public Deck() throws Exception {
        File xmlFile = new File("xml/cards.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList cardNodes = doc.getElementsByTagName("card");

        for (int i = 0; i < cardNodes.getLength(); i++) {
            Element cardElem = (Element) cardNodes.item(i);

            // Extract attributes for the Scenes constructor
            String cardName = cardElem.getAttribute("name");
            int budget = Integer.parseInt(cardElem.getAttribute("budget"));

            // Dig into the <scene> tag for number and description
            Element sceneElem = (Element) cardElem.getElementsByTagName("scene").item(0);
            int sceneNum = Integer.parseInt(sceneElem.getAttribute("number"));
            String desc = sceneElem.getTextContent().trim();

            // Create the Scenes object
            Scene sceneCard = new Scene(cardName, sceneNum, budget, desc);

            // Parse the <part> tags (starring roles) for this specific card
            NodeList partNodes = cardElem.getElementsByTagName("part");
            for (int j = 0; j < partNodes.getLength(); j++) {
                Element partElem = (Element) partNodes.item(j);

                Part starringRole = new Part();
                starringRole.setName(partElem.getAttribute("name"));
                starringRole.setLevel(Integer.parseInt(partElem.getAttribute("level")));
                starringRole.setLine(partElem.getElementsByTagName("line").item(0).getTextContent());
                starringRole.setArea(getArea(partElem));

                // Add the fully formed part to the scene card
                sceneCard.addPart(starringRole);
            }

            this.deck.add(sceneCard);
        }

        Collections.shuffle(deck);
    }

    // Parse Area, returns a <String,String> Hashmap
    public HashMap<String, String> getArea(Element nodeElement) {
        HashMap<String, String> map = new HashMap<>();
        try {
            Node area = nodeElement.getElementsByTagName("area").item(0);
            Element areaElement = (Element) area;
            map.put("x", areaElement.getAttribute("x"));
            map.put("y", areaElement.getAttribute("y"));
            map.put("h", areaElement.getAttribute("h"));
            map.put("w", areaElement.getAttribute("w"));

        } catch (Exception e) {
            System.out.print(e);
        }
        return map;
    }

    public Scene drawCard() {
        if (deck.isEmpty()) {
            return null;
        }
        return deck.remove(0);
    }

    public Integer numberScenes() {
        return deck.size();
    }
}
