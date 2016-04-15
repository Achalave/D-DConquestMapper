package main.relationPersistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public class DatabaseManager {

    public static final String TABLE_FILE_EXTENTION = ".mdbt";
    public static final String SCHEMA_FILE_EXTENTION = "mschema";
    public static final char HEADER_END = '+';
    public static final String RELATION_DELIMITER = "relate";

    private final String dbPath;
    private Scanner reader;
    private final HashMap<String, RelationalTable> tables;

    public DatabaseManager(String path) {
        dbPath = path;
        tables = new HashMap<>();
    }

    public void loadTables() throws DatabaseNotFoundException {
        File db = new File(dbPath);
        if (!db.exists()) {
            throw new DatabaseNotFoundException();
        }

        //Get a list of tables (.mdbt files) 
        String[] tableNames = db.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(TABLE_FILE_EXTENTION);
            }
        });

        //Create Relational Tables for each file
        for (String tableName : tableNames) {
            RelationalTable table = new RelationalTable(dbPath + tableName);
            tables.put(tableName.substring(0, tableName.length() - TABLE_FILE_EXTENTION.length()), table);
        }
    }

    public void test(){
        Object[] tuple = {"test1","test2"};
        tables.get("EVENTS").addTuple(tuple);
    }
    
    public static void generateTableFiles(String dbPath, String schematicPath) {
        Scanner scan = new Scanner((ClassLoader.getSystemResourceAsStream(schematicPath)));
        while (scan.hasNextLine()) {
            String tableName = null;
            String schema = null;
            String varTypes = null;
            FileWriter tableFile = null;
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                //Skip comments
                if (line.startsWith("//")) {
                    continue;
                }
                //Break for end of line
                if (line.isEmpty()) {
                    break;
                }

                //The first line is a name
                if (tableName == null) {
                    tableName = line.toUpperCase();
                } //The second is the schema
                else if (schema == null) {
                    schema = line;
                } else if (varTypes == null) {
                    varTypes = line;
                    try {
                        File file = new File(dbPath + tableName + TABLE_FILE_EXTENTION);
                        if (!file.exists()) {
                            file.getParentFile().mkdirs();
                            file.createNewFile();
                        }
                        tableFile = new FileWriter(file);
                        tableFile.write(schema + "\n");
                        tableFile.write(varTypes+"\n");
                    } catch (IOException ex) {
                        Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } //The rest are modifiers
                else {
                    try {
                        tableFile.write(line + "\n");
                    } catch (IOException ex) {
                        Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            //Close the file if it was opened
            if (tableFile != null) {
                try {
                    tableFile.write(HEADER_END+"\n");
                    tableFile.close();
                } catch (IOException ex) {
                    Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
