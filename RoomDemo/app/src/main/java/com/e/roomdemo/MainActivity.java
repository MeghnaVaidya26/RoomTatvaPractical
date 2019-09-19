package com.e.roomdemo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    NoteViewModel noteViewModel;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<Note> notes;
    ArrayList<Note> noteArrayList;
    NoteListAdapter noteListAdapter;
    boolean isLoaded = false;
    ContactViewModel contactViewModel;
    List<Contact.COntactDetail> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        notes = new ArrayList<>();
        contactList = new ArrayList<>();
        noteArrayList = new ArrayList<>();
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.init();
        contactViewModel.getContactRepo().observe(this, new Observer<Contact>() {
            @Override
            public void onChanged(@Nullable Contact contact) {
                List<Contact.COntactDetail> contacts = contact.getcOntactDetails();
                Log.v("Name", "-" + contacts.get(1).getName());
                contactList.removeAll(contacts);
                contactList.addAll(contacts);
                if (contactList.size() > 0) {
                    Log.v("notes", "----" + notes.size());
                    populateData();
                    initAdapter();
                    initScrollListener();
                }

            }
        });
        /*noteViewModel.getAllData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> noteslist) {
                Log.v("Notes", "-" + noteslist.size());
                notes.addAll(noteslist);

                 if (notes.size() > 0) {
                    Log.v("notes", "----" + notes.size());
                    populateData();
                    initAdapter();
                    initScrollListener();
                }
            }
        });*/


    }

    @Override
    protected void onStart() {
        super.onStart();
       /* try {
            Log.v("Alldata", "-----" + noteViewModel.getAllData());
            notes.clear();
            notes.addAll(noteViewModel.getAllData());
            if (notes.size() > 0) {
                Log.v("notes", "----" + notes.size());
                populateData();
                initAdapter();
                initScrollListener();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    private void populateData() {
        int i = 0;
        if (notes.size() > 10) {
            while (i < 10) {
                noteArrayList.add(notes.get(i));
                i++;

            }
        } else {
            noteArrayList.addAll(notes);
        }
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteListAdapter = new NoteListAdapter(noteArrayList, this);
        recyclerView.setAdapter(noteListAdapter);
    }

    private void initScrollListener() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoaded) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == noteArrayList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoaded = true;
                    }
                }
            }
        });

    }

    private void loadMore() {
        noteArrayList.add(null);
        // noteListAdapter.notifyItemInserted(noteArrayList.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                noteArrayList.remove(noteArrayList.size() - 1);
                int scrollPosition = noteArrayList.size();
                noteListAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                while (currentSize - 1 < nextLimit) {
                    if (notes.size() > currentSize) {
                        noteArrayList.add(notes.get(currentSize));
                    }
                    currentSize++;
                }

                noteListAdapter.notifyDataSetChanged();
                isLoaded = false;
            }
        }, 2000);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == 1) {
            final String id = UUID.randomUUID().toString();
            Note note = new Note(id, data.getStringExtra("note"));

            noteViewModel.insert(note);
            Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
