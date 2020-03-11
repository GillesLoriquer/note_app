package gillesloriquer.com;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import gillesloriquer.com.models.Note;

public class NoteActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener {

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
    private ImageButton mBackArrow, mCheck;

    // vars
    private boolean mIsNewNote;
    private Note mInitialNote;
    private GestureDetector mGestureDetector;
    private int mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // view mode
        mArrowContainer = findViewById(R.id.arrow_container);
        mBackArrow = findViewById(R.id.toolbar_back_arrow);
        mViewTitle = findViewById(R.id.note_text_title);
        // edit mode
        mCheckContainer = findViewById(R.id.check_container);
        mEditTitle = findViewById(R.id.note_edit_title);
        mCheck = findViewById(R.id.toolbar_check);
        // common
        mLinedEditText = findViewById(R.id.note_content);

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
        mViewTitle.setOnClickListener(this);
        mCheck.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // onTouch override suite implémentation View.OnTouchListener
        // on va ici transmettre au GestureDetectore le touch event
        // suivant le type d'event l'une des méthodes ci-dessous sera appelée
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_check: {
                disableEditMode();
                break;
            }
            case R.id.note_text_title: {
                enableEditMode();
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.length());   // place le curseur en bout de chaine
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mMode == EDIT_MODE_ENABLED) {
            onClick(mCheck);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: double tapped!");
        enableEditMode();
        return false;
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
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
