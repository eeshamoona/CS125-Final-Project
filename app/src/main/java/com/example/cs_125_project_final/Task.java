package com.example.cs_125_project_final;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Task Class used to process the data the user enters on the activity_task screen.
 */
public class Task extends AppCompatActivity {
    /**
     *  List of GoalClass.
     */
    private List<GoalClass> readListOfGoals = new ArrayList<>();
    /**
     * GoalClass to add the task to.
     */
    private GoalClass toEdit;

    /**
     * onCreate called at the start Task Screen.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        //Scrolling list of verbs
        Spinner verbs = findViewById(R.id.Verbs);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.verbs_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        verbs.setAdapter(adapter);

        //Text box for the user to fill in
        TextView text = findViewById(R.id.TaskEnter);

        //Scrolling list of times
        Spinner time = findViewById(R.id.Time);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        time.setAdapter(adapter2);

        //call findGoalClass()
        if (findGoalClass() != null) {
            toEdit = findGoalClass();
        }

        //set a setOnClickListener on backButton to redirect to UIScreen
        Button backButton = findViewById(R.id.backButton);
        Intent intentTask = new Intent(this, UIScreen.class);
        intentTask.putExtra("GoalTitle", toEdit.getTitle());
        backButton.setOnClickListener(unused -> {
            System.out.println(toEdit.getTitle());
            startActivity(intentTask);
        });

        //set a setOnClickListener on addButton to add the task to the GoalClass
        Button addButton = findViewById(R.id.Add);
        addButton.setOnClickListener(unused -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.create();
            //if the user has filled out all the fields
            if (!verbs.getSelectedItem().toString().equals("-") && !(text.getText().equals("")) && !time.getSelectedItem().toString().equals("-")) {
                String creatingTask = verbs.getSelectedItem().toString() + " " +
                        text.getText().toString() + " for " + time.getSelectedItem().toString() + " minutes";
                //add the task to GoalClass
                postInfo(creatingTask);
                dialog.setMessage("Task Created Successfully");
                dialog.show();
                verbs.setSelection(0);
                text.setText("");
                time.setSelection(0);
            //otherwise show a dialog to fill out the information
            } else {
                dialog.setMessage("Please Fill out all information");
                dialog.show();
            }
        });
    }

    /**
     * add task and write the value on the data.txt
     * @param toPost String task
     */
    public void postInfo (String toPost) {
        toEdit.addTask(toPost);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            objectMapper.writeValue(new File (getApplicationContext().getFilesDir(),"data.txt"), readListOfGoals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * find the GoalClass using the title
     * @return found GoalClass
     */
    public GoalClass findGoalClass() {
        String gcTitle = getIntent().getStringExtra("GoalTitleTask");
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            File file = new File(getApplicationContext().getFilesDir()+ "/data.txt");
            readListOfGoals = mapper.readValue(file, new TypeReference<List<GoalClass>>() {});
        } catch (JsonGenerationException e)
        {
            e.printStackTrace();
        } catch (JsonMappingException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        for (GoalClass gc : readListOfGoals) {
            if (gc.getTitle().equals(gcTitle)) {
                return gc;
            }
        }
        return null;
    }
}
