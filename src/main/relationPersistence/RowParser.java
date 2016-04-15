
package main.relationPersistence;

import java.io.FileInputStream;

/**
 *
 * @author Michael
 */
public class RowParser {

    static StringParser stringParser;
    AttributeParser[] parsers;
    String[] attributeNames;
    FileInputStream stream;
    int attributeIndex, bytesProcessed;
    
    public RowParser(String[] attributeNames, String[] varTypes){
        this.attributeNames = attributeNames;
        //Fill out parsers with a parser corresponding to attribute type
        parsers = new AttributeParser[varTypes.length];
        for(int i=0; i<varTypes.length; i++){
            if(varTypes[i].equals("String")){
                if(stringParser == null){
                    stringParser = new StringParser();
                }
                parsers[i] = stringParser;
            }else{
                System.err.println("Variable type: "+varTypes[i]+" is not supported.");
            }
        }
    }
    
    /**
     * Sets the FileInputStream that this object will read from. It is assumed
     * that the stream is pointed at the beginning of a row of the type supported
     * by this instance.
     * @param stream 
     */
    public void setInputStream(FileInputStream stream){
        this.stream = stream;
        attributeIndex = 0;
    }
    
    /**
     * Parse the next attribute.
     * @return A reference to the parsed attribute
     */
    public Object getNextAttribute(){
        Object a = parsers[attributeIndex].parseAttribute(stream);
        incrementAttributeIndex();
        return a;
    }
    
    /**
     * Skip the next attribute.
     */
    public void skipNextAttribute(){
        parsers[attributeIndex].skipAttribute(stream);
        incrementAttributeIndex();
    }
    
    /**
     * Parses the remainder of the current tuple.
     * @return An array of object corresponding to each attribute remaining in
     * this tuple.
     */
    public Object[] getRemainingTuple(){
        Object[] tuple = new Object[parsers.length-attributeIndex];
        for(int i=0; i<tuple.length; i++){
            tuple[i] = getNextAttribute();
        }
        return tuple;
    }
    
    /**
     * Skips one full tuple or the remainder of the current tuple.
     */
    public void nextTuple(){
        for(int i=0; i<parsers.length-attributeIndex; i++){
            skipNextAttribute();
        }
    }
    
    /**
     * Increment the attribute counter and check if it should be reset for the
     * next row.
     */
    private void incrementAttributeIndex(){
        attributeIndex++;
        if(attributeIndex == parsers.length){
            attributeIndex = 0;
        }
    }
    
    public AttributeParser getParserForAttribute(int index){
        return parsers[index];
    }
    
    public String getNameOfAttribute(int index){
        return attributeNames[index];
    }
    
    public int getIndexOfAttribute(String attr){
        for(int i=0; i<attributeNames.length; i++){
            if(attributeNames[i].equals(attr)){
                return i;
            }
        }
        return -1;
    }
}
