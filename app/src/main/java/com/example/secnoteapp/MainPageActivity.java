package com.example.secnoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.secnoteapp.create_new_notes.NewNote;
import com.example.secnoteapp.create_new_notes.NewNoteActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {
    private Button newNote;
    RecyclerView recyclerView;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference ;

    List<NewNote> listNote = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        newNote = findViewById(R.id.create_new_note_btn);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        final NotesAdapter notesAdapter=new NotesAdapter(listNote,this);
        recyclerView.setAdapter(notesAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().
                child("Users").child(firebaseAuth.getCurrentUser().getUid()).
                child("User Notes");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    NewNote newNote= dataSnapshot1.getValue(NewNote.class);
                    listNote.add(newNote);

                }
                notesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, NewNoteActivity.class);
                startActivity(intent);
            }
        });



    }


}
