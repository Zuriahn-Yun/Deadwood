import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/*
* TODO READ AND PROCESS XML Path: xml\board.xml
*/
public class Board {
    // Read XML
    public Board() throws Exception {
        // FilePath: xml\board.xml
        File xmlFile = new File("xml/board.xml");
        // Create a DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Parse the XML file
        Document document = builder.parse(xmlFile);
        // Root Node: board
        // Node root = document.getDocumentElement();

        // Grab all Sets
        NodeList setList = document.getElementsByTagName("set");
        
        // Iterate through Sets
        for(int i = 0; i < setList.getLength();i++){
            // Grab node
            Node curr = setList.item(i);
            // Cast node to element
            Element element = (Element) curr;
            // Grab name of Set
            // Set set = new Set(element.getAttribute("name"), null)
            System.out.println(element.getAttribute("name"));
            Node neighbors = element.getElementsByTagName("neighbors").item(0);
            NodeList curre = neighbors.getChildNodes();
            System.out.println(neighbors);
            System.out.println(curre);
            
        }

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
