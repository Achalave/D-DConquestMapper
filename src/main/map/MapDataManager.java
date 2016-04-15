package main.map;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Michael
 */
public class MapDataManager {

    String mapBasePath;
    String pathToStructs;
    String pathToEvents;

    ArrayList<DDStructure> structures;
    ArrayList<DDEvent> events;
    ArrayList<DDStructure> structuresInView;

    public MapDataManager(String mapBasePath) {
        structuresInView = new ArrayList<>();
        structures = new ArrayList<>();
        events = new ArrayList<>();

        this.mapBasePath = mapBasePath;
        pathToStructs = mapBasePath + Paths.baseToStructures;
        pathToEvents = mapBasePath+Paths.baseToEvents;
        
    }

    public void load() {
        //Produce all the DDStructures
        File structs = new File(pathToStructs);
        if (structs.exists()) {
            for (String struct : structs.list()) {
                structures.add(new DDStructure(pathToStructs+"/"+struct));
            }
        } else {
            //Make the structures folder
            structs.mkdirs();            
        }
        //Generate the path to the events if it does not exist
        File eventsPath = new File(pathToEvents);
        if(!eventsPath.exists()){
            eventsPath.mkdirs();
        }
    }

}
