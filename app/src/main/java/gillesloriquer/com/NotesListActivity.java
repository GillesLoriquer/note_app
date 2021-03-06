package gillesloriquer.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import gillesloriquer.com.adapters.NotesRecyclerAdapter;
import gillesloriquer.com.models.Note;
import gillesloriquer.com.persistence.NoteRepository;
import gillesloriquer.com.util.VerticalSpacingItemDecorator;

public class NotesListActivity extends AppCompatActivity
        implements NotesRecyclerAdapter.OnNoteListener,
        FloatingActionButton.OnClickListener {

    private static final String TAG = "NotesListActivity";

    // ui components
    private RecyclerView mRecyclerView;

    // vars
    private List<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNotesRecyclerAdapter;
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        mRecyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.fab).setOnClickListener(this);

        mNoteRepository = new NoteRepository(this);

        initRecyclerView();

        retrieveNotes();

        setSupportActionBar((Toolbar) findViewById(R.id.notes_toolbar));
        setTitle("Notes");
    }

    private void retrieveNotes() {
        mNoteRepository.retriveNotesTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (mNotes.size() > 0) {
                    mNotes.clear();
                }

                mNotes.addAll(notes);

                mNotesRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        mNotesRecyclerAdapter = new NotesRecyclerAdapter(mNotes, this);
        mRecyclerView.setAdapter(mNotesRecyclerAdapter);
        new ItemTouchHelper(mItemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra(NoteActivity.SELECTED_NOTE, mNotes.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, NoteActivity.class);
        startActivity(i);
    }

    // callback pour l'ItemTouchHelper attaché au RecyclerView
    private ItemTouchHelper.SimpleCallback mItemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
    };

    private void deleteNote(Note n) {
        mNoteRepository.deleteNote(n);
        mNotesRecyclerAdapter.notifyDataSetChanged();
    }
}
