package com.example.secnoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.secnoteapp.create_new_notes.NewNote;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends AppCompatActivity {
    Button updateBtn, deleteBtn;
    EditText noteTitle , noteContent;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateBtn = findViewById(R.id.update_note_btn);
        deleteBtn  = findViewById(R.id.delete_note);
        noteContent = findViewById(R.id.note_desc);
        noteTitle = findViewById(R.id.note_title);

        Intent i = getIntent();
        String selectedTitle = i.getStringExtra("title");
        String selectedContent = i.getStringExtra("desc");
        final String selectedId = i.getStringExtra("id");
        final NewNote noteToUpdate = new NewNote(selectedTitle,selectedContent,selectedId);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().
                child("Users").child(firebaseAuth.getCurrentUser().getUid()).
                child("User Notes");


        getSelectedNote (noteToUpdate);





        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewNote finalNote = new NewNote(noteTitle.getText().toString(),noteContent.getText().toString(),selectedId);
                updateNote(finalNote);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(selectedId);
            }
        });


    }
    public void getSelectedNote (NewNote newNote){
        noteTitle.setText(newNote.getTitle());
        noteContent.setText(newNote.getContent());

    }
    void updateNote (NewNote newNote){

        databaseReference.child(newNote.getId()).setValue(newNote);
        Intent intent = new Intent(UpdateActivity.this,MainPageActivity.class);
        startActivity(intent);
    }
    void deleteNote (String id){
        databaseReference.child(id).removeValue();
        Intent intent = new Intent(UpdateActivity.this,MainPageActivity.class);
        startActivity(intent);
    }
}
