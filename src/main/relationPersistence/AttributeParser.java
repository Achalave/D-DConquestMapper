
package main.relationPersistence;

import java.io.FileInputStream;

/**
 *
 * @author Michael
 */
public interface AttributeParser {
    /**
     * This should parse one attribute of the type supported by the implementing
     * class. It is assumed that at least one attribute of that type is available
     * in the FileInputStream.
     * @param stream
     * @return The Object representation of the parsed data.
     */
    public Object parseAttribute(FileInputStream stream);
    
    /**
     * This should skip one attribute of the type supported by the implementing
     * class. It is assumed that at least one attribute of that type is available
     * in the FileInputStream.
     * @param stream
     * @return The number of bytes skipped.
     */
    public int skipAttribute(FileInputStream stream);
    
    /**
     * Implements the comparison of data of this type in a standardized manner.
     * @param attr The attribute value to be compared.
     * @param compareType The type of comparison to perform.
     * @param compareValue The value to compare the attribute to.
     * @return True if the object satisfies this condition.
     */
    public boolean compareAttribute(Object attr, String compareType, String compareValue);
    
    public int getNumLastParsedBytes();
}
