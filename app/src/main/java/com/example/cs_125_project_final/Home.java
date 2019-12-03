package com.example.cs_125_project_final;

import android.os.Bundle;

import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button addGoal = findViewById(R.id.addGoal);
        addGoal.setOnClickListener(unused -> startActivity(new Intent(this, Goal.class)));



    }
}
