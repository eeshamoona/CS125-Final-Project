package com.example.cs_125_project_final;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Starting screen holding all of goals.
 */
public class Home extends AppCompatActivity {
    /**
     * List of GoalClass.
     */
    private List<GoalClass> readListOfGoals = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Allows user to add a new goal
        Button addGoal = findViewById(R.id.addGoal);
        addGoal.setOnClickListener(unused -> startActivity(new Intent(this, Goal.class)));

        //Refresh the UI
        refreshUI();
    }

    /**
     * Populate the UI of the Home Screen.
     */
    public void refreshUI() {

        //read the data file into the List of Goals
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

        //if there is a list of Goals then populate the UI
        if (readListOfGoals != null) {
            LinearLayout listOfGoals = findViewById(R.id.listOfGoals);
            listOfGoals.removeAllViews();

            //go through each of the GoalClass
            for (GoalClass c : readListOfGoals) {
                View messageChunk = getLayoutInflater().inflate(R.layout.chunk_home,
                        listOfGoals, false);

                //Change the text on TextView to the title of the goal
                TextView title = messageChunk.findViewById(R.id.goalTitle);
                title.setText(c.getTitle());

                //add a setOnClickListener to the delete goal button
                Button deleteGoal = messageChunk.findViewById(R.id.DeleteGoal);
                deleteGoal.setOnClickListener(unused -> {

                    //when delete is pressed remove the goal from the list
                    //and refresh the UI
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                    try {
                        readListOfGoals.remove(c);
                        objectMapper.writeValue(new File (getApplicationContext().getFilesDir(),"data.txt"), readListOfGoals);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    refreshUI();
                });

                //add a setOnClickListener to the add goal button
                Button enterGoal = messageChunk.findViewById(R.id.EnterGoal);
                Intent intentGoal = new Intent(this, UIScreen.class);
                intentGoal.putExtra("GoalTitle", c.getTitle());
                enterGoal.setOnClickListener(unused -> startActivity(intentGoal));

                //add view to parent view
                listOfGoals.addView(messageChunk);
            }
        }

    }
}
