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
        // // Grab all Sets
        // NodeList setList = document.getElementsByTagName("set");
        // There are 10 Sets
        NodeList sets = document.getElementsByTagName("set");
        for (int i = 0; i < sets.getLength(); i++) {
            Node setNode = sets.item(i);
            if (setNode.getNodeType() == Node.ELEMENT_NODE) {
                // This is a single set element
                Element setElement = (Element) setNode;
                NodeList elementChildren = setElement.getChildNodes();
                for (int j = 0; i < elementChildren.getLength(); j++) {
                    Node setelementchildNode = elementChildren.item(j);
                    if (setelementchildNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element setchild = (Element) setelementchildNode;
                        // this looks at neightbors, area, takes parts, should write a function to parse each individually instead of all here togehter
                        System.out.println(setchild.getTagName());
                    }
                }

            }
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
