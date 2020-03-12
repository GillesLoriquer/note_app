package gillesloriquer.com.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import gillesloriquer.com.models.Note;

@Dao
public interface NoteDao {

    @Insert
    long[] insert(Note... notes);      // Notes... = Note[]

    @Delete
    int delete(Note... notes);         // int : retourne le nombre de rows supprimés

    @Update
    int update(Note... notes);          // int : retourne le nombre de rows supprimés

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    // exemple avec paramètre
    //@Query("SELECT * FROM notes WHERE title LIKE :title")
    //List<Note> getNewNotes(String title);
}
