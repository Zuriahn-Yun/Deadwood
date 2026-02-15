import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/*
* TODO READ AREA,TAKES,PART,AND LINES, add storage locations in set class
*/
public class Board {
    // Read XML
    public Board() throws Exception {
        ArrayList<Set> sets = new ArrayList<>();
        // FilePath: xml\board.xml
        File xmlFile = new File("xml/board.xml");
        // Create a DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Parse the XML file
        Document document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();
        // // Grab all Sets
        // NodeList setList = document.getElementsByTagName("set");
        // There are 10 Sets
        NodeList setsNodeList = document.getElementsByTagName("set");
        // 12 Neighbor Lists
        NodeList neighborsNodeList = document.getElementsByTagName("neighbors");
        // area
        NodeList areaNodeList = document.getElementsByTagName("area");
        System.out.println("Area" + areaNodeList.getLength());
        // upgrades
        NodeList upgradesNodeList = document.getElementsByTagName("upgrades");

        // Trailer
        Node trailerNode = document.getElementsByTagName("trailer").item(0);
        // Office
        Node officeNode = document.getElementsByTagName("office").item(0);

        for (int i = 0; i < setsNodeList.getLength(); i++) {
            Node setNode = setsNodeList.item(i);

            if (setNode.getNodeType() == Node.ELEMENT_NODE) {
                Element nodeElement = (Element) setNode;
                // Get Name of Set
                System.out.println("Set Name: " + nodeElement.getAttribute("name"));

                // Extract Neighbors and add to Set
                List<String> neighborList = getNeighbors(nodeElement);
                HashMap<String, HashMap<String, String>> takes = getTakes(nodeElement);
                HashMap<String, String> area = getArea(nodeElement);
                
                
                // Create a Set Object with name and neighbors
                Set set = new Set(nodeElement.getAttribute("name"), neighborList);
                set.setArea(area);
                set.setTakes(takes);
                
                // Add set to Set list
                sets.add(set);
            }
        }

    }

    // Parse Parts
    public List<String> getParts(Element nodElement) {
        ArrayList<String> parts = new ArrayList<>();
        try {
            // Parse Parts 
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return parts;
    }

    // Parse Takes, Key = Number, Value = <String,String> Hashmap
    public HashMap<String, HashMap<String, String>> getTakes(Element nodeElement) {
        HashMap<String, HashMap<String, String>> takes = new HashMap<>();
        try {
            Node takesNode = nodeElement.getElementsByTagName("takes").item(0);
            Element takesElement = (Element) takesNode;
            NodeList takesNodeList = takesElement.getElementsByTagName("take");
            for (int i = 0; i < takesNodeList.getLength(); i++) {
                Node take = takesNodeList.item(i);
                Element takeElement = (Element) take;
                String number = takeElement.getAttribute("number");
                HashMap<String,String> area = getArea(takeElement);
                takes.put(number, area);
            }
            
        } catch (Exception e) {
            System.out.println("Exception" + e);
        }
        return takes;
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

    // Parse the neighbors out of any Element and return an ArrayList on all
    // neighbors
    public List<String> getNeighbors(Element nodeElement) {
        List<String> neighborsList = new ArrayList<>();
        try {
            // Extract neighbors
            Node neighbors = nodeElement.getElementsByTagName("neighbors").item(0);
            Element neighborElement = (Element) neighbors;

            NodeList neighborsNodeList = neighborElement.getElementsByTagName("neighbor");
            for (int i = 0; i < neighborsNodeList.getLength(); i++) {
                Node neighbor = neighborsNodeList.item(i);
                Element neighborELement = (Element) neighbor;
                neighborsList.add(neighborELement.getAttribute("name"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return neighborsList;
    }

    public static void main(String[] args) {
        System.out.println("TESTING BOARD PARSING XML");
        try {
            Board board = new Board();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
