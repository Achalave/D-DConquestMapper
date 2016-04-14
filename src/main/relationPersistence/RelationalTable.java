package main.relationPersistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
    private FileReader reader;
    private int tableBegin;
    private RowParser parser;
    private int numKeys;
    private boolean autoGenFirstKey;

    public RelationalTable(String path) {
        tablePath = path;
        sem = new Semaphore(1);
        tableBegin = 0;

        parser = new RowParser(varTypes);
        
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
    }

    public boolean addTuple(Object[] tuple) {
        try {
            sem.acquire();
            FileInputStream stream = getInputStream();
            byte[] bytes = new byte[10];
            try {
                System.out.println(stream.available());
            } catch (IOException ex) {
                Logger.getLogger(RelationalTable.class.getName()).log(Level.SEVERE, null, ex);
            }
            sem.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(RelationalTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private Scanner createTableScanner() {
        Scanner scan = new Scanner(tablePath);
        //Skip everything that isnt the header end
        scan.skip("[^" + DatabaseManager.HEADER_END + "]");
        scan.nextLine();
        return scan;
    }

    private FileReader getFileReader() {
        try {
            if (reader == null) {

                reader = new FileReader(tablePath);
                reader.mark(tableBegin);

            }
            reader.reset();
            return reader;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RelationalTable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelationalTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

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
