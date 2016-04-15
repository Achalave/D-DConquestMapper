
package main.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Michael
 */
public class DDStructure extends DataElement implements Comparable<DDStructure>{

    String structureName;
    ArrayList<DDNote> notes;
    int x,y,id;
    Shape shape;
    Color outline;
    Color fill;

    public DDStructure(String path) {
        super(path);
        notes = new ArrayList<>();
        id = Integer.parseInt(path.substring((path.lastIndexOf('/')+1),(path.length()-3)));
    }

    @Override
    public int compareTo(DDStructure o) {
        return x-o.x;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }

    @Override
    protected void parseData(NodeList nodes) throws XMLFormatException{
        /*
        name
        notes
        - note <date>
        location assertions
        - structureID
        - distance
        */
        for(int i=0; i<nodes.getLength();i++){
            Node node = nodes.item(i);
            switch(node.getNodeName()){
                case "name":
                    structureName = node.getNodeValue();
                    break;
                case "notes":
                    parseNotes(node);
                    break;
                case "location-assertions":
                    parseLocationAssertions(node);
                    break;
            }
        }
    }
    
    /**
     * Parses the notes from an XML document.
     * @param node The starting node for notes.
     * @throws XMLFormatException 
     */
    protected void parseNotes(Node node) throws XMLFormatException{
        NodeList nodes = node.getChildNodes();
        for(int i=0; i<nodes.getLength(); i++){
            Node noteNode = nodes.item(i);
            String date = noteNode.getAttributes().getNamedItem("date").getNodeValue();
            String noteString = noteNode.getNodeValue();
            if(date == null || noteString == null){
                throw new XMLFormatException();
            }
            DDNote note = new DDNote(noteString,date);
            notes.add(note);
        }
    }
    
    /**
     * Parses the assertion locations from an XML document.
     * @param node The starting node for location assertions.
     * @throws XMLFormatException 
     */
    protected void parseLocationAssertions(Node node) throws XMLFormatException{
        NodeList nodes = node.getChildNodes();
        for(int i=0; i<nodes.getLength(); i++){
            NodeList assertion = nodes.item(i).getChildNodes();
            for(int k=0;k<assertion.getLength();k++){
                Node assertItem = assertion.item(i);
                int structID = -1;
                float distance = -1;
                switch(assertItem.getNodeName()){
                    case "structureID":
                        structID = Integer.parseInt(assertItem.getNodeValue());
                        break;
                    case "distance":
                        distance = Float.parseFloat(assertItem.getNodeValue());
                        break;
                }
                if(structID < 0 || distance < 0){
                    throw new XMLFormatException();
                }
                DDistanceAssertion a = new DDistanceAssertion(structID, distance);
            }
        }
    }

    public void draw(Graphics2D g, int shiftx, int shifty){
        g.setColor(fill);
        g.fill(shape);
        g.setColor(outline);
        g.draw(shape);
    }
    
}
