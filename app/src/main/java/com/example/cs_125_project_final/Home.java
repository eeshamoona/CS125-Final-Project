package com.example.cs_125_project_final;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Allows user to add a new goal
        Button addGoal = findViewById(R.id.addGoal);
        addGoal.setOnClickListener(unused -> startActivity(new Intent(this, Goal.class)));

        //displays goals in progress
        //clicking a goal will take you to the UIScreen for it
        ViewGroup goalList = findViewById(R.id.goalList);
        goalList.removeAllViews();
        View homeChunk = getLayoutInflater().inflate(R.layout.chunk_home,
                goalList, false);

    }
}
