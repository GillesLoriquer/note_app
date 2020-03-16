package gillesloriquer.com.async;

import android.os.AsyncTask;

import gillesloriquer.com.models.Note;
import gillesloriquer.com.persistence.NoteDao;

public class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDao mNoteDao;

    public InsertAsyncTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.insert(notes);
        return null;
    }
}
