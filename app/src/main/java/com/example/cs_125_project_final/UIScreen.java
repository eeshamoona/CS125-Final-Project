package com.example.cs_125_project_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

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
 * The UIScreen that displays all the tasks.
 */
public class UIScreen extends AppCompatActivity {
    /**
     *  List of GoalClass.
     */
    private List<GoalClass> readListOfGoals = new ArrayList<>();
    /**
     * GoalClass to add the task to.
     */
    private GoalClass printingClass;

    /**
     *  onCreate called at the start UI Screen.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String toPrint = getIntent().getStringExtra("GoalTitle");
        setTitle("Goal: " + toPrint);
        setContentView(R.layout.activity_uiscreen);

        //set a setOnClickListener to the addTask to redirect to Task screen
        Button addTask = findViewById(R.id.addTask);
        Intent intent = new Intent(this, Task.class);
        intent.putExtra("GoalTitleTask", toPrint);
        addTask.setOnClickListener(unused -> startActivity(intent));

        //set a setOnClickListener to the backButton to redirect to Home screen
        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(unused -> startActivity(new Intent(this, Home.class)));

        //call findGoalClass()
        printingClass = findGoalClass();

        //call setUpUI()
        setUpUI();

    }

    /**
     * load the UI with all the information
     */
    public void setUpUI() {
        //if there is a printing class
        if (printingClass != null) {
            LinearLayout taskList = findViewById(R.id.taskGroup);
            taskList.removeAllViews();

            //iterate through all the strings in the task array
            for (String s : printingClass.getTasks()) {
                View messageChunk = getLayoutInflater().inflate(R.layout.chunk_uiscreen,
                        taskList, false);
                CheckBox title = messageChunk.findViewById(R.id.taskToDo);
                title.setText(s);
                title.setOnClickListener(unused -> {
                    //Add to completed list
                    printingClass.addCompletedTask(s);
                    //Remove from tasks
                    printingClass.removeTask(s);
                    //rewrite the task array
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                    try {
                        objectMapper.writeValue(new File (getApplicationContext().getFilesDir(),"data.txt"), readListOfGoals);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // set up UI again
                    setUpUI();
                });
                //add the View to the Layout
                taskList.addView(messageChunk);
            }
        }
    }

    /**
     * find the GoalClass using the title
     * @return found GoalClass
     */
    public GoalClass findGoalClass() {
        String gcTitle = getIntent().getStringExtra("GoalTitle");
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
