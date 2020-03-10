package gillesloriquer.com;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import gillesloriquer.com.models.Note;

public class NoteActivity extends AppCompatActivity {

    private static final String TAG = "NoteActivity";

    public static final String SELECTED_NOTE = "selected_note";

    // ui components
    private LinedEditText mLinedEditText;
    private EditText mEditTitle;
    private TextView mViewTitle;

    // vars
    private boolean mIsNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        if (getIncomingIntent()) {
            // this is a new note, (EDIT MODE)
        } else {
            // this is not a new note, (VIEW MODE)
        }
    }

    private boolean getIncomingIntent() {
        if (getIntent().hasExtra(SELECTED_NOTE)) {
            Note incomingNote = getIntent().getParcelableExtra(SELECTED_NOTE);
            Log.d(TAG, "getIncomingIntent: " + incomingNote.toString());

            mIsNewNote = false;
        } else {
            mIsNewNote = true;
        }

        return mIsNewNote;
    }
}
