import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/*
* TODO READ AREA,TAKES,PART,AND LINES, add storage locations in set class
*/
public class Board {
    // Read XML
    public Board() throws Exception {;
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
        System.out.println("upgradesNodeList" + upgradesNodeList.getLength());
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
                List<String> neighborList= getNeighbors(nodeElement);
                // Create a Set Object with name and neighbors
                Set set = new Set(nodeElement.getAttribute("name"), neighborList);
                // Add set to Set list
                sets.add(set);
            }
        }

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
            // 3
            System.out.println(neighborsNodeList.getLength());
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
