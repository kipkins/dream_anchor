package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sean on 5/23/16.
 */
public class NoteList {
    public static List<Note> instance;

    private NoteList(){}


    public static List<Note> getInstance(){
        if(instance == null){
            instance = new ArrayList<>();
        }
       return instance;
    }

    public void addNote(Note note){
        instance.add(note);
    }

    public void set(List<Note> notes){
        for(Note note : notes){
            instance.add(note);
        }
    }
}
