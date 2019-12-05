package com.example.cs_125_project_final;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.google.gson.JsonObject;

/**
 * Task Class used to process the data the user enters on the activity_task screen.
 */
public class Task extends AppCompatActivity {
    private GoalClass gc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        gc = (GoalClass) getIntent().getParcelableExtra("Goal");
        //includes a scrolling list of verbs
        Spinner verbs = findViewById(R.id.Verbs);
            // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.verbs_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
        verbs.setAdapter(adapter);

        //a text box for the user to fill in
        TextView text = findViewById(R.id.TaskEnter);

        //another scrolling list of times
        Spinner time = findViewById(R.id.Time);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        time.setAdapter(adapter2);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(unused -> startActivity(new Intent(this, UIScreen.class)));

        //add button
        Button addButton = findViewById(R.id.Add);
        addButton.setOnClickListener(unused -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.create();
            if (verbs.getSelectedItemPosition() != -1 && !(text.getText().equals("")) && time.getSelectedItemPosition() != -1) {
                String creatingTask = verbs.getSelectedItem().toString() + " " +
                        text.getText().toString() + " for " + time.getSelectedItem().toString() + " minutes";
                int returnInt = postInfo(creatingTask);
                if (returnInt == -1) {
                    dialog.setMessage("Error in Building Your Task");
                    dialog.show();
                } else {
                    dialog.setMessage("Task Created Successfully");
                    dialog.show();
                }
                verbs.setSelection(-1);
                text.setText("");
                time.setSelection(-1);
            } else {
                dialog.setMessage("Please Fill out all information");
                dialog.show();
            }
        });
    }

    public int postInfo (String toPost) {
        gc.addTask(toPost);
        return 1;
    }

    //onPress of the add button
        //make sure that there has been a selection of verbs/ text/ time
        //if yes,
            //compile all the information from the components into a String
            //addTask to JSON file using the taskAPI
            //tell user that it has been added --> notification? Alert Dialog?
            //clear all the information so the user can add another.
        //if no,
            //Alert Dialog? Somehow tell the user to fill out the info

    //post the JSON file to the Task Web API for the UI screen to use
}
