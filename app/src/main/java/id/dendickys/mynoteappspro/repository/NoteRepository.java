package id.dendickys.mynoteappspro.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import id.dendickys.mynoteappspro.database.Note;
import id.dendickys.mynoteappspro.database.NoteDao;
import id.dendickys.mynoteappspro.database.NoteRoomDatabase;
import id.dendickys.mynoteappspro.helper.SortUtils;

public class NoteRepository {

    private NoteDao mNotesDao;
    private ExecutorService executorService;
    public NoteRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();

        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);
        mNotesDao = db.noteDao();
    }

    public DataSource.Factory<Integer, Note> getAllNotes(String sort) {
        SimpleSQLiteQuery query = SortUtils.getSorteredQuery(sort);
        return mNotesDao.getAllNotes(query);
    }

    public void insert(final Note note) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mNotesDao.insert(note);
            }
        });
    }

    public void delete(final Note note){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mNotesDao.delete(note);
            }
        });
    }

    public void update(final Note note){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mNotesDao.update(note);
            }
        });
    }
}
