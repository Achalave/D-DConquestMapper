
package main.GUI;

import main.map.MapDataManager;

/**
 *
 * @author Michael
 */
public class Test {

    public static void main(String[] args){
        MapDataManager data = new MapDataManager("DD1/testmap/");
        data.load();
    }
    
}
