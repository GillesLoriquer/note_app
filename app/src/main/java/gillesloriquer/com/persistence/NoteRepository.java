package gillesloriquer.com.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import gillesloriquer.com.models.Note;

public class NoteRepository {

    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note) {
    }

    public void updateNote(Note note) {
    }

    public LiveData<List<Note>> retriveNotesTask() {
        return mNoteDatabase.getNoteDao().getNotes();
    }

    public void deleteNote(Note note) {
    }
}
