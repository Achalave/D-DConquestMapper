
package main.relationPersistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is designed to parse one null terminated string from the given
 * input stream. It is assumed that there is one string of data left in this stream.
 * See AttributeParser for more information.
 * @author Michael
 */
public class StringParser implements AttributeParser{

    int parsedBytes;
    
    @Override
    public Object parseAttribute(FileInputStream stream) {
        parsedBytes = 0;
        String attribute = "";
        try {
            char in;
            while((in = (char)stream.read()) != 0){
                parsedBytes++;
                attribute += in;
            }
            parsedBytes++;
        } catch (IOException ex) {
            Logger.getLogger(StringParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return attribute;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public int skipAttribute(FileInputStream stream) {
        int skips = 0;
        try {
            while(stream.read() != 0)skips++;
            //Include the final 0 byte
            skips++;
        } catch (IOException ex) {
            Logger.getLogger(StringParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        parsedBytes = skips;
        return skips;
    }

    @Override
    public int getNumLastParsedBytes() {
        return parsedBytes;
    }

    @Override
    public boolean compareAttribute(Object attr, String compareType, String compareValue) {
        switch (compareType) {
            case "<":
                return ((String)attr).compareTo(compareType) < 0;
            case "=":
                return ((String)attr).compareTo(compareType) == 0;
            case ">":
                return ((String)attr).compareTo(compareType) > 0;
        }
        return false;
    }

}
