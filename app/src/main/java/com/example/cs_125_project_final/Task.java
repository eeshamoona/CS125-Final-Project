package com.example.cs_125_project_final;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Task Class used to process the data the user enters on the activity_task screen.
 */
public class Task extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        //get a list of verbs for the user to pick from??

        //includes a scrolling list of verbs
        //a text box for the user to fill in
        //another scrolling list of times
        //add button

        Spinner verbs = findViewById(R.id.Verbs);
        Spinner time = findViewById(R.id.Time);
        TextView text = findViewById(R.id.TaskEnter);
        Button addbutton = findViewById(R.id.Add);
        addbutton.setOnClickListener(unused -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.create();
            if (verbs.getSelectedItemPosition() != -1
                    && !(text.getText().equals(""))
                    && time.getSelectedItemPosition() != -1) {
            }
            else {
                dialog.setMessage("Please Fill out all information");
                dialog.show();
            }
        });
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
