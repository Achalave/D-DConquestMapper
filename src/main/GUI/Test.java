
package main.GUI;

import java.io.File;
import main.persistance.DatabaseManager;

/**
 *
 * @author Michael
 */
public class Test {

    public static void main(String[] args){
        File map = new File("testmap");
        map.mkdir();
        DatabaseManager m = new DatabaseManager("testmap/");
        File db = new File("testmap/relations.db");
        db.delete();
    }
    
}
