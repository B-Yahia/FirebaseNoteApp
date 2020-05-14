package com.example.secnoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.secnoteapp.create_new_notes.NewNote;
import com.example.secnoteapp.create_new_notes.NewNoteActivity;
import com.example.secnoteapp.view_holder.NotesViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {
    private Button newNote;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<NewNote> options;
    FirebaseRecyclerAdapter<NewNote, NotesViewHolder> adapter;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        newNote = findViewById(R.id.create_new_note_btn);
        recyclerView = findViewById(R.id.recyclerview);
       // recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().
                child("Users").child(firebaseAuth.getCurrentUser().getUid()).
                child("User Notes");
       // String key = databaseReference.push().getKey();

        options = new FirebaseRecyclerOptions.Builder<NewNote>().setQuery(databaseReference,
                new SnapshotParser<NewNote>() {
                    @NonNull
                    @Override
                    public NewNote parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new NewNote(snapshot.child("Note id").getValue().toString(),
                                snapshot.child("title").getValue().toString(),
                                snapshot.child("content").getValue().toString());
                    }
                }).build();
        adapter = new FirebaseRecyclerAdapter<NewNote, NotesViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull NotesViewHolder holder, int position, @NonNull NewNote model) {
                holder.vContent.setText(model.getContent());
                holder.vTitle.setText(model.getTitle());
            }
            @NonNull
            @Override
            public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_view,parent,false);
                return new NotesViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, NewNoteActivity.class);
                startActivity(intent);
            }
        });



    }


}
