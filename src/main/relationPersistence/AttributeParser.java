
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
    
    public int getNumLastParsedBytes();
}
