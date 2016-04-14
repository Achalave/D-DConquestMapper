package main.map;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Michael
 */
public abstract class DataElement {

    protected final String dataPath;
    protected static DocumentBuilderFactory docFactory;
    protected static DocumentBuilder docBuilder;

    public DataElement(String path) {
        dataPath = path;
    }

    /**
     * Loads the data stored in dataPath that is associated with this object.
     *
     * @throws main.map.XMLFormatException
     */
    public void loadData() throws XMLFormatException {
        try {
            if (docFactory == null) {
                docFactory = DocumentBuilderFactory.newInstance();
            }
            if (docBuilder == null) {
                docBuilder = docFactory.newDocumentBuilder();
            }
            Document doc = docBuilder.parse(new File(dataPath + Paths.xmlDocumentName));
            Element e = doc.getDocumentElement();
            e.normalize();
            NodeList nodes = e.getChildNodes();
            parseData(nodes);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(DataElement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Given the XML document object, parse out all the data and fill out the
     * data instance. This is used by DataElement's loadData() method.
     *
     * @param nodes
     * @throws main.map.XMLFormatException
     */
    protected abstract void parseData(NodeList nodes) throws XMLFormatException;
}
