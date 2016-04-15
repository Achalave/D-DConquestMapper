package main.relationPersistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public class RelationalTable {

    private String tablePath;
    private Semaphore sem;
    private String[] schema;
    private String[] varTypes;
    private String[] dependancies;
    private int tableBegin;
    private final RowParser parser;
    private int numKeys;
    private boolean autoGenFirstKey;

    public RelationalTable(String path) {
        tablePath = path;
        sem = new Semaphore(1);
        tableBegin = 0;
        
        try (Scanner in = new Scanner(new File(path))) {
            String line = in.nextLine();
            //increment tableBegin
            tableBegin += line.length() + 1;
            schema = line.split(" ");

            //Check the schema for the number of keys and if it should autogen keys
            numKeys = 0;
            for(int i=0; i<schema.length; i++){
                if(schema[i].startsWith("#_")){
                    schema[i] = schema[i].substring(2);
                    this.autoGenFirstKey = true;
                    this.numKeys++;
                }else if(schema[i].startsWith("_")){
                    schema[i] = schema[i].substring(1);
                    this.numKeys++;
                }else{
                    break;
                }
            }
            
            dependancies = new String[schema.length];

            line = in.nextLine();
            //increment tableBegin
            tableBegin += line.length() + 1;
            varTypes = line.split(" ");

            //Do a check on the lengths
            if (varTypes.length != schema.length) {
                System.err.println("Relational Table: " + path + " has unmatching var type and schema lengths.");
            }

            //Get all the dependancies
            while (!((line = in.nextLine()).equals(DatabaseManager.HEADER_END + ""))) {
                //Increment tableBegin
                tableBegin += line.length() + 1;
                //Skip comments and empty lines
                if (line.startsWith("//") || line.isEmpty()) {
                    continue;
                }

                //Check the delimiter
                String[] split = line.split(" ");
                if (split[0].equals(DatabaseManager.RELATION_DELIMITER)) {
                    for (int i = 0; i < schema.length; i++) {
                        if (schema[i].equals(split[1])) {
                            dependancies[i] = split[2];
                        }
                    }
                }
            }
            //Increment the tableBegin value to account for the header_end and newline
            tableBegin+=2;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RelationalTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        parser = new RowParser(schema, varTypes);
    }

    public boolean addTuple(Object[] tuple) {
        try {
            sem.acquire();
            
            sem.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(RelationalTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

//    public boolean deleteTupleWhere(String condition){
//        
//    }
//    
//    private boolean deleteTupleWhere(TupleCondition con){
//        
//    }
    
    private FileInputStream getInputStream() {
        try {
            FileInputStream inStream = new FileInputStream(tablePath);
            inStream.skip(tableBegin);
            return inStream;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RelationalTable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelationalTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
