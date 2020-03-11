package gillesloriquer.com;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import gillesloriquer.com.models.Note;

public class NoteActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private static final String TAG = "NoteActivity";

    // constants
    public static final String SELECTED_NOTE = "selected_note";
    private static final String DEFAULT_NOTE_TITLE = "Note Title";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    // ui components
    private LinedEditText mLinedEditText;
    private EditText mEditTitle;
    private TextView mViewTitle;
    private RelativeLayout mArrowContainer, mCheckContainer;

    // vars
    private boolean mIsNewNote;
    private Note mInitialNote;
    private GestureDetector mGestureDetector;
    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mViewTitle = findViewById(R.id.note_text_title);
        mEditTitle = findViewById(R.id.note_edit_title);
        mLinedEditText = findViewById(R.id.note_content);
        mArrowContainer = findViewById(R.id.arrow_container);
        mCheckContainer = findViewById(R.id.check_container);

        if (getIncomingIntent()) {
            setNewNoteProperties();
            enableEditMode();
        } else {
            setNoteProperties();
        }

        setTouchListener();
    }

    private boolean getIncomingIntent() {
        if (getIntent().hasExtra(SELECTED_NOTE)) {
            mInitialNote = getIntent().getParcelableExtra(SELECTED_NOTE);
            mIsNewNote = false;
            mMode = EDIT_MODE_DISABLED;
        } else {
            mIsNewNote = true;
            mMode = EDIT_MODE_ENABLED;
        }

        return mIsNewNote;
    }

    private void enableEditMode() {
        // containers
        mArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        // widgets
        mEditTitle.setVisibility(View.VISIBLE);
        mViewTitle.setVisibility(View.GONE);

        mMode = EDIT_MODE_ENABLED;
    }

    private void disableEditMode() {
        // Containers
        mArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        // Widgets
        mEditTitle.setVisibility(View.GONE);
        mViewTitle.setVisibility(View.VISIBLE);

        mMode = EDIT_MODE_DISABLED;
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

    private void setTouchListener() {
        mLinedEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // onTouch override suite implémentation View.OnTouchListener
        // on va ici transmettre au GestureDetectore le touch event
        // suivant le type d'event l'une des méthodes ci-dessous sera appelée
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: double tapped!");
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
