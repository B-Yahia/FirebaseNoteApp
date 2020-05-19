package com.example.secnoteapp.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.secnoteapp.MainPageActivity;
import com.example.secnoteapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.prefs.Preferences;

public class ConnectActivity extends AppCompatActivity {

    private Button connectBtn;
    private EditText userEmail,userPassword;
    private CheckBox mCheckBox;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;

    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        connectBtn = findViewById(R.id.connect_btn);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        progressBar = findViewById(R.id.progressBar);
        mCheckBox = findViewById(R.id.checkBox);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        spEditor = sharedPreferences.edit();
        checkSharedPreferences();


        firebaseAuth = FirebaseAuth.getInstance();
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                if (mCheckBox.isChecked()){
                    spEditor.putString(getString(R.string.checkbox), "True");
                    spEditor.commit();

                    String emailtosave = userEmail.getText().toString();
                    spEditor.putString(getString(R.string.email),emailtosave);
                    spEditor.commit();

                    String passwordtosave = userPassword.getText().toString();
                    spEditor.putString(getString(R.string.password),passwordtosave);
                    spEditor.commit();
                } else {
                    spEditor.putString(getString(R.string.checkbox), "False");
                    spEditor.commit();


                    spEditor.putString(getString(R.string.email),"");
                    spEditor.commit();


                    spEditor.putString(getString(R.string.password),"");
                    spEditor.commit();

                }

                updateUI(email,password);
            }


        });
    }
    private void updateUI(String uEmail,String uPassword){

        progressBar.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(uEmail)){
            Toast.makeText(this, "Email field is empty", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(uPassword)){
            Toast.makeText(this, "Password field is empty", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(uEmail,uPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ConnectActivity.this, "You are connected", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConnectActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(ConnectActivity.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkSharedPreferences (){
        String checkBox = sharedPreferences.getString(getString(R.string.checkbox),"false");
        String email = sharedPreferences.getString(getString(R.string.email),"");
        String password = sharedPreferences.getString(getString(R.string.password),"");

        userEmail.setText(email);
        userPassword.setText(password);
        if (checkBox.equals(true)){
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
        }

    }
}
