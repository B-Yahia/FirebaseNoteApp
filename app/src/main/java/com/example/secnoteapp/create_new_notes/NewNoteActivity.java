package com.example.secnoteapp.create_new_notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.secnoteapp.MainPageActivity;

import com.example.secnoteapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewNoteActivity extends AppCompatActivity {

    private EditText noteTitle,noteDesc;
    private Button createBtn,deleteBtn;

    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("User Notes");

        noteDesc = findViewById(R.id.note_desc);
        noteTitle = findViewById(R.id.note_title);
        createBtn = findViewById(R.id.create_note_btn);
        deleteBtn = findViewById(R.id.delete_note);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewNoteActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = noteTitle.getText().toString();
                String content = noteDesc.getText().toString();
                saveNote(title,content);

            }
        });
    }
    public void saveNote (String nTitle,String nContent){
        if (firebaseAuth.getCurrentUser() != null){
            if (TextUtils.isEmpty(nTitle) && TextUtils.isEmpty(nContent)){
                Toast.makeText(this, "Ooops you forgot to type your note!", Toast.LENGTH_SHORT).show();
            }






            //final DatabaseReference noteReference = databaseReference.push();
            final Map noteMap = new HashMap();
            //noteMap.put("Note id",noteReference.getKey() );
            noteMap.put("title",nTitle);
            noteMap.put("content",nContent);
            noteMap.put("time", ServerValue.TIMESTAMP);

                    databaseReference.push().setValue(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(NewNoteActivity.this, "The note has been saved", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NewNoteActivity.this,MainPageActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(NewNoteActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }else {
            Toast.makeText(this, "You are not connected", Toast.LENGTH_SHORT).show();
        }


    }
}
