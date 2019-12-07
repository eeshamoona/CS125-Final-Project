package com.example.cs_125_project_final;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Screen from for Creating a new Goal.
 */
public class Goal extends AppCompatActivity {
    /**
     * List of GoalClass.
     */
    private List<GoalClass> listOfGoals = new ArrayList<>();

    /**
     * onCreate for the Goal Screen
     * @param savedInstanceState savedInstanceState of the screen.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        //set a setOnClickListener for the back Button that directs to the Home
        Button back = findViewById(R.id.back);
        back.setOnClickListener(unused -> startActivity(new Intent(this, Home.class)));

        //reading the data file and filling the listOfGoals Array
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(getApplicationContext().getFilesDir()+ "/data.txt");
            listOfGoals = objectMapper.readValue(file, new TypeReference<List<GoalClass>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

        //populate the UI with information
        EditText goalTitle = findViewById(R.id.GoalEntry);
        //set a setOnClickListener for the addGoal
        Button addButton = findViewById(R.id.addGoal);
        addButton.setOnClickListener(unused -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.create();
            //if the goalTitle Text has a string in it
            if (!(goalTitle.getText().equals(""))) {
                GoalClass gc = new GoalClass();
                ObjectMapper objectMapper = new ObjectMapper();
                gc.setTitle(goalTitle.getText().toString());
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                //write the goal to data.txt
                try {
                    listOfGoals.add(gc);
                    objectMapper.writeValue(new File (getApplicationContext().getFilesDir(),"data.txt"), listOfGoals);
                    goalTitle.setText("");
                    dialog.setMessage("Added Goal!");
                    dialog.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            //otherwise show a dialog to fill out the information
            } else {
                dialog.setMessage("Please Fill out all information");
                dialog.show();
            }
        });
    }
}
