package com.e.roomdemo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NoteViewModel extends AndroidViewModel {

    private NoteDao noteDao;
    private NoteRoomDatabase noteRoomDatabase;
   //LiveData<List<Note>> listLiveData;
    List<Note>
           listLiveData;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRoomDatabase = NoteRoomDatabase.getDatabase(application);
        noteDao = noteRoomDatabase.noteDao();
       // listLiveData = noteDao.getAllData();
    }

    public void insert(Note note) {
        new InsertAsyncTask(noteDao).execute(note);
    }

    public List<Note> getAllData() throws ExecutionException, InterruptedException {
        return new getDataAsyncTask(noteDao).execute().get();
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        Log.i("Cleared", "View Model Cleared");
    }

    class InsertAsyncTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        public InsertAsyncTask(NoteDao noteDao) {

            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    class getDataAsyncTask extends AsyncTask<Void, Void, List<Note>> {
        NoteDao noteDao;

        public getDataAsyncTask(NoteDao noteDao) {

            this.noteDao = noteDao;
        }



        @Override
        protected List<Note> doInBackground(Void... voids) {
            listLiveData = noteDao.getAllData();
            return listLiveData;
        }
    }


}
