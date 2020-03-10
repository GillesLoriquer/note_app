package gillesloriquer.com;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import gillesloriquer.com.models.Note;

public class NoteActivity extends AppCompatActivity {

    private static final String TAG = "NoteActivity";

    public static final String SELECTED_NOTE = "selected_note";
    private static final String DEFAULT_NOTE_TITLE = "Note Title";

    // ui components
    private LinedEditText mLinedEditText;
    private EditText mEditTitle;
    private TextView mViewTitle;

    // vars
    private boolean mIsNewNote;
    private Note mInitialNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mViewTitle = findViewById(R.id.note_text_title);
        mEditTitle = findViewById(R.id.note_edit_title);
        mLinedEditText = findViewById(R.id.note_content);

        if (getIncomingIntent()) {
            setNewNoteProperties();
        } else {
            setNoteProperties();
        }
    }

    private boolean getIncomingIntent() {
        if (getIntent().hasExtra(SELECTED_NOTE)) {
            mInitialNote = getIntent().getParcelableExtra(SELECTED_NOTE);
            mIsNewNote = false;
        } else {
            mIsNewNote = true;
        }

        return mIsNewNote;
    }

    private void setNoteProperties() {
        mViewTitle.setText(mInitialNote.getTitle());
        mEditTitle.setText(mInitialNote.getTitle());
        mLinedEditText.setText(mInitialNote.getContent());
    }

    private void setNewNoteProperties() {
        mViewTitle.setText(DEFAULT_NOTE_TITLE);
        mEditTitle.setText(DEFAULT_NOTE_TITLE);
    }
}
