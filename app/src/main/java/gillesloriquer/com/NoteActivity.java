package gillesloriquer.com;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import gillesloriquer.com.models.Note;

public class NoteActivity extends AppCompatActivity {

    private static final String TAG = "NoteActivity";

    public static final String SELECTED_NOTE = "selected_note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        if (getIntent().hasExtra(SELECTED_NOTE)) {
            Note note = getIntent().getParcelableExtra(SELECTED_NOTE);
            Log.d(TAG, "onCreate: " + note.getContent());
        }
    }
}
