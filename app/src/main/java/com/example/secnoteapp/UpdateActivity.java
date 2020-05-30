package com.example.secnoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.secnoteapp.create_new_notes.NewNote;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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


        Intent i = getIntent();
        String selectedTitle = i.getStringExtra("title");
        String selectedContent = i.getStringExtra("desc");
        final String selectedId = i.getStringExtra("id");
        Toast.makeText(UpdateActivity.this, "The noote id" +selectedId, Toast.LENGTH_SHORT).show();
        noteContent = findViewById(R.id.note_desc);
        noteTitle = findViewById(R.id.note_title);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().
                child("Users").child(firebaseAuth.getCurrentUser().getUid()).
                child("User Notes");

        noteTitle.setText(selectedTitle);
        noteContent.setText(selectedContent);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateNote(selectedId);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(selectedId);
            }
        });


    }

    void updateNote (String id){
        NewNote newNote = new NewNote(noteTitle.getText().toString(),noteContent.getText().toString(),id);
        databaseReference.child(id).setValue(newNote).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(UpdateActivity.this, "Note updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Intent intent = new Intent(UpdateActivity.this,MainPageActivity.class);
        startActivity(intent);
    }
    void deleteNote (String id){
        databaseReference.child(id).removeValue();
        Intent intent = new Intent(UpdateActivity.this,MainPageActivity.class);
        startActivity(intent);
    }
}
