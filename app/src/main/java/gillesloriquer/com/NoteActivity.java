package gillesloriquer.com;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import gillesloriquer.com.models.Note;
import gillesloriquer.com.persistence.NoteRepository;
import gillesloriquer.com.util.DateGenerator;

public class NoteActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher {

    private static final String TAG = "NoteActivity";

    // constants
    public static final String SELECTED_NOTE = "selected_note";
    private static final String DEFAULT_NOTE_TITLE = "Note Title";
    private static final String MODE = "mode";
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
    private Note mFinalNote;
    private GestureDetector mGestureDetector;
    private int mMode;
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mNoteRepository = new NoteRepository(this);

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
            disableContentInteraction();
        }

        setListeners();
    }

    private boolean getIncomingIntent() {
        if (getIntent().hasExtra(SELECTED_NOTE)) {
            mIsNewNote = false;
            mInitialNote = getIntent().getParcelableExtra(SELECTED_NOTE);
            mFinalNote = getIntent().getParcelableExtra(SELECTED_NOTE);
            mMode = EDIT_MODE_DISABLED;
        } else {
            mIsNewNote = true;
            mMode = EDIT_MODE_ENABLED;
        }

        return mIsNewNote;
    }

    private void saveChanges() {
        if (mIsNewNote) {
            saveNewNote();
        } else {
            updateNote();
        }
    }

    private void saveNewNote() {
        mNoteRepository.insertNoteTask(mFinalNote);
    }

    private void updateNote() {
        mNoteRepository.updateNote(mFinalNote);
    }

    private void setNewNoteProperties() {
        mViewTitle.setText(DEFAULT_NOTE_TITLE);
        mEditTitle.setText(DEFAULT_NOTE_TITLE);

        mInitialNote = new Note();
        mFinalNote = new Note();
        mInitialNote.setTitle(DEFAULT_NOTE_TITLE);
        mInitialNote.setContent("");
        mFinalNote.setTitle(DEFAULT_NOTE_TITLE);
        mFinalNote.setContent("");
    }

    private void setNoteProperties() {
        mViewTitle.setText(mInitialNote.getTitle());
        mEditTitle.setText(mInitialNote.getTitle());
        mLinedEditText.setText(mInitialNote.getContent());
    }

    private void hideSoftKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void showSoftKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    private void enableEditMode() {
        // containers
        mArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        // widgets
        mEditTitle.setVisibility(View.VISIBLE);
        mViewTitle.setVisibility(View.GONE);

        mMode = EDIT_MODE_ENABLED;

        enableContentInteraction();

        showSoftKeyboard(mLinedEditText);
    }

    private void disableEditMode() {
        // Containers
        mArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        // Widgets
        mEditTitle.setVisibility(View.GONE);
        mViewTitle.setVisibility(View.VISIBLE);

        mMode = EDIT_MODE_DISABLED;

        disableContentInteraction();

        String title = mEditTitle.getText().toString();
        String content = ((mLinedEditText.getText() != null) ? mLinedEditText.getText().toString() : "");
        if (content.length() > 0) {
            if (!title.toLowerCase().trim().equals(mInitialNote.getTitle().toLowerCase().trim())
                    || !content.toLowerCase().trim().equals(mInitialNote.getContent().toLowerCase().trim())) {
                mFinalNote.setTitle(title);
                mFinalNote.setContent(content);
                mFinalNote.setTimestamp(DateGenerator.getCurrentTimestamp());
                saveChanges();
            }
        }
    }

    private void enableContentInteraction() {
        mLinedEditText.setKeyListener(new EditText(this).getKeyListener());
        mLinedEditText.setFocusable(true);
        mLinedEditText.setFocusableInTouchMode(true);
        mLinedEditText.setCursorVisible(true);
        mLinedEditText.requestFocus();
    }

    private void disableContentInteraction() {
        mLinedEditText.setKeyListener(null);
        mLinedEditText.setFocusable(false);
        mLinedEditText.setFocusableInTouchMode(false);
        mLinedEditText.setCursorVisible(false);
        mLinedEditText.clearFocus();
    }

    private void setListeners() {
        mLinedEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
        mViewTitle.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mBackArrow.setOnClickListener(this);
        mEditTitle.addTextChangedListener(this);
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
                hideSoftKeyboard(mLinedEditText);
                break;
            }
            case R.id.note_text_title: {
                enableEditMode();
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.length());   // place le curseur en bout de chaine
                showSoftKeyboard(mEditTitle);
                break;
            }
            case R.id.toolbar_back_arrow: {
                finish();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MODE, mMode);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt(MODE);
        if (mMode == EDIT_MODE_ENABLED) {
            enableEditMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mViewTitle.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
