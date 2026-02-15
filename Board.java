import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/*
*  Class to hold all information from board.xml
*/
public class Board {
    // Read XML
    // All general sets are in this ArrayList
    private ArrayList<Set> sets = new ArrayList<>();
    // Trailer and office are directly accesible variables
    private Set trailer;
    private Office office;

    public Board() throws Exception {
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

        // Trailer
        Node trailerNode = document.getElementsByTagName("trailer").item(0);
        Element trailElement = (Element) trailerNode;
        Set trailer = new Set("trailer", getNeighbors(trailElement));
        trailer.setArea(getArea(trailElement));
        setTrailer(trailer);

        // Office
        Node officeNode = document.getElementsByTagName("office").item(0);
        Element officeElement = (Element) officeNode;
        Set officeSet = new Set("office", getNeighbors(officeElement));
        officeSet.setArea(getArea(officeElement));
        // Setting Office
        setOffice(new Office(officeSet, getUpgrades(officeElement)));

        for (int i = 0; i < setsNodeList.getLength(); i++) {
            Node setNode = setsNodeList.item(i);

            if (setNode.getNodeType() == Node.ELEMENT_NODE) {
                Element nodeElement = (Element) setNode;

                // Create a Set Object with name and neighbors
                Set set = new Set(nodeElement.getAttribute("name"), getNeighbors(nodeElement));
                set.setArea(getArea(nodeElement));
                set.setTakes(getTakes(nodeElement));
                set.setParts(getParts(nodeElement));

                // Add set to Set list
                sets.add(set);
            }
        }

    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }

    public void setTrailer(Set trailer) {
        this.trailer = trailer;
    }

    public Office getOffice() {
        return office;
    }

    public Set getTrailer() {
        return trailer;
    }

    // Parse Upgrades
    public ArrayList<Upgrade> getUpgrades(Element nodeElement) {
        ArrayList<Upgrade> upgrades = new ArrayList<>();
        try {
            Node upgradeNode = nodeElement.getElementsByTagName("upgrades").item(0);
            Element upgradeElement = (Element) upgradeNode;
            NodeList upgradeList = upgradeElement.getElementsByTagName("upgrade");
            for (int i = 0; i < upgradeList.getLength(); i++) {
                Node up = upgradeList.item(i);
                Element upgradeSingleElement = (Element) up;
                Upgrade upgrade = new Upgrade();
                upgrade.setLevel(Integer.parseInt(upgradeSingleElement.getAttribute("level")));
                upgrade.setAmt(upgradeSingleElement.getAttribute("amt"));
                upgrade.setArea(getArea(upgradeSingleElement));
                upgrade.setCurrency(upgradeSingleElement.getAttribute("currency"));
                upgrades.add(upgrade);
            }

        } catch (Exception e) {
            System.out.println("Exception" + e);
        }
        return upgrades;
    }

    // Parse Parts
    public ArrayList<Part> getParts(Element nodeElement) {
        ArrayList<Part> parts = new ArrayList<>();
        try {
            // Parse Parts
            Node partsNode = nodeElement.getElementsByTagName("parts").item(0);
            Element partsElement = (Element) partsNode;
            NodeList partsNodeList = partsElement.getElementsByTagName("part");
            for (int i = 0; i < partsNodeList.getLength(); i++) {
                Node part = partsNodeList.item(i);
                Element partElement = (Element) part;

                // Create a parts Object
                Part partObj = new Part();
                // Parse Parts and add to partObj
                partObj.setName(partElement.getAttribute("name"));
                partObj.setLevel(Integer.parseInt(partElement.getAttribute("level")));
                partObj.setArea(getArea(partsElement));
                partObj.setLine(partElement.getAttribute("line"));
                parts.add(partObj);
            }

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
                HashMap<String, String> area = getArea(takeElement);
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

    public ArrayList<Set> getSets() {
        return sets;
    }

    public static void main(String[] args) {
        try {
            Board board = new Board();
            
        } catch (Exception e) {
            System.out.println("Exception" + e);
        }

    }
}
