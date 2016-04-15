package main.persistance;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//@author Michael Haertling
public class DatabaseManager {

    public static final String DATABASE_NAME = "relations.db";
    public static final String DATABASE_SETUP_FILE_PATH = "main/persistance/databasesetup.txt";
    private final String filePath;
    private final String databasePath;

    public DatabaseManager(String path) {
        filePath = path;
        databasePath = path + DATABASE_NAME;

        //Check if the database file exists
        if (!(new File(databasePath)).exists()) {
            generateDatabaseFile();
        }
    }

    public ResultSet executeQuery(String query) {
        ResultSet result = null;
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + databasePath)) {
            Statement stat = con.createStatement();
            result = stat.executeQuery(query);
            stat.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void executeInsert(String insert){
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + databasePath)) {
            Statement stat = con.createStatement();
            stat.executeUpdate(insert);
            stat.closeOnCompletion();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("empty-statement")
    private void generateDatabaseFile() {
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + databasePath)) {
            Statement stat;
            stat = con.createStatement();
            try (Scanner in = new Scanner(ClassLoader.getSystemResourceAsStream(DATABASE_SETUP_FILE_PATH))) {
                while (in.hasNextLine()) {
                    //Collect all the statements that go together
                    String command = "";
                    String line;
                    while (in.hasNextLine() && !(line = in.nextLine()).isEmpty() && !line.startsWith("//")){
                        command += line;
                    }
                    //Run the command
                    if (!command.isEmpty()) {
                        stat.executeUpdate(command);
                    }
                }
            }
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
