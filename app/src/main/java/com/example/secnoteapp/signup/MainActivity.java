package com.example.secnoteapp.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.secnoteapp.R;

public class MainActivity extends AppCompatActivity {

    private Button connectBtn,createBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectBtn = findViewById(R.id.connect_btn);
        createBtn = findViewById(R.id.new_account_btn);

        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent connectIntent = new Intent(MainActivity.this, ConnectActivity.class);
                startActivity(connectIntent);
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newtIntent = new Intent(MainActivity.this, NewAccountActivity.class);
                startActivity(newtIntent);
            }
        });
    }
}
