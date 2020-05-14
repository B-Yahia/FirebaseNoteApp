package com.example.secnoteapp.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.secnoteapp.MainPageActivity;
import com.example.secnoteapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewAccountActivity extends AppCompatActivity {
    private Button createAcc;
    private EditText newUserName,newUserEmail,newUserPassword;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference fUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        firebaseAuth = FirebaseAuth.getInstance();
        fUserDatabase= FirebaseDatabase.getInstance().getReference().child("Users");

        createAcc = findViewById(R.id.create_new_account_btn);
        newUserName = findViewById(R.id.new_user_name);
        newUserEmail = findViewById(R.id.new_user_email);
        newUserPassword = findViewById(R.id.new_user_password);
        progressBar = findViewById(R.id.progressBar);

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newUserName.getText().toString();
                String email = newUserEmail.getText().toString();
                String password = newUserPassword.getText().toString();
                updateUI(name,email,password);
            }
        });
    }
    private void updateUI(final String name , final String email, final String password){

        progressBar.setVisibility(View.VISIBLE);


        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Name field is empty", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email field is empty", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            NewUserInfo user = new NewUserInfo(name,email,password);
                            fUserDatabase.child(firebaseAuth.getCurrentUser().getUid()).child("User info")
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(NewAccountActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(NewAccountActivity.this, MainPageActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(NewAccountActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                            progressBar.setVisibility(View.GONE);
                        }else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(NewAccountActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
