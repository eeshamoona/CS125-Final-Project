package com.example.cs_125_project_final;

import android.app.AlertDialog;
import android.os.Bundle;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    private List<GoalClass> readListOfGoals = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Allows user to add a new goal
        Button addGoal = findViewById(R.id.addGoal);
        addGoal.setOnClickListener(unused -> startActivity(new Intent(this, Goal.class)));
        refreshUI();
    }
    public void refreshUI() {

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
        if (readListOfGoals != null) {

            LinearLayout listOfGoals = findViewById(R.id.listOfGoals);
            listOfGoals.removeAllViews();

            for (GoalClass c : readListOfGoals) {
                View messageChunk = getLayoutInflater().inflate(R.layout.chunk_home,
                        listOfGoals, false);
                TextView title = messageChunk.findViewById(R.id.goalTitle);
                title.setText(c.getTitle());

                Button deleteGoal = messageChunk.findViewById(R.id.DeleteGoal);
                deleteGoal.setOnClickListener(unused -> {

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

                Button enterGoal = messageChunk.findViewById(R.id.EnterGoal);
                Intent intent = new Intent(this, UIScreen.class);
                intent.putExtra("Title", c.getTitle());
                intent.putExtra("Tasks", c.getTasks());
                enterGoal.setOnClickListener(ununsed -> startActivity(intent));

                listOfGoals.addView(messageChunk);
            }
        }

    }
}
