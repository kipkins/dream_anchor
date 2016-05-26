package data;

import java.util.Date;

/**
 * Created by sean on 5/23/16.
 */
public class Note {
    public Date date;
    public String title;
    public String entry;


    public Note(Date date, String title, String entry){
        this.date = date;
        this.title = title;
        this.entry = entry;
    }

    public boolean saveNote(Note note){
        return true;
    }
}
