package com.loliwe.lostfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button createBtn, displayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createBtn = findViewById(R.id.button1);
        displayBtn = findViewById(R.id.button2);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent newIntent = new Intent(MainActivity.this, NewAdActivity.class);
                //startActivity(newIntent);
                startdbapp();
            }
        });

        displayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(newIntent);
            }
        });

    }
    public void startdbapp() {
        new DbManager(this);
        startActivity(new Intent(this, NewAdActivity.class));
    }
}