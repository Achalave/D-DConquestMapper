
package main.GUI;

import main.map.MapDataManager;
import main.relationPersistence.DatabaseManager;
import main.relationPersistence.DatabaseNotFoundException;

/**
 *
 * @author Michael
 */
public class Test {

    public static void main(String[] args) throws DatabaseNotFoundException{
        MapDataManager data = new MapDataManager("DD1/testmap/");
        //data.load();
        //DatabaseManager.generateTableFiles("DD1/database/", "main/relationPersistence/dataschemas.mschema");
        DatabaseManager manager = new DatabaseManager("DD1/database/");
        manager.loadTables();
        manager.test();
    }
    
}
