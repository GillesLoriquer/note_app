package gillesloriquer.com.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import gillesloriquer.com.async.DeleteAsyncTask;
import gillesloriquer.com.async.InsertAsyncTask;
import gillesloriquer.com.async.UpdateAsyncTask;
import gillesloriquer.com.models.Note;

public class NoteRepository {

    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note) {
        new InsertAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public void updateNote(Note note) {
        new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public LiveData<List<Note>> retriveNotesTask() {
        return mNoteDatabase.getNoteDao().getNotes();
    }

    public void deleteNote(Note note) {
        new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }
}
