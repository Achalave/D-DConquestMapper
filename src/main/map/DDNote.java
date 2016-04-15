
package main.map;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public class DDNote {

    DateFormat format;
    Date date;
    String note;
    
    public DDNote(){
        if(format == null){
            format = new SimpleDateFormat("dd/MM/yy");
        }
    }
    
    public DDNote(String note){
        date = new Date();
        this.note = note;
    }
    
    public DDNote(String note, String date){
        try {
            this.date = format.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(DDNote.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.note = note;
    }
    
    public void setNote(String note){
        this.note = note;
    }
    
}
