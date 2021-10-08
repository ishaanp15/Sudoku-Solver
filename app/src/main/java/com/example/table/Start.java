package com.example.table;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_start);
    }

    public void start(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void solve(View v){
        Intent intent = new Intent(this, SolveSudoku.class);
        startActivity(intent);
    }

    public void settings(View v){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}