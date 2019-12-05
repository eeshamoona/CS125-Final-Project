package com.example.cs_125_project_final;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import com.android.volley.Request;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.api.services.tasks.model.TaskList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Screen from for Creating a new Goal.
 */
public class Goal extends AppCompatActivity {

    private List<GoalClass> listOfGoals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(unused -> startActivity(new Intent(this, Home.class)));

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(getApplicationContext().getFilesDir()+ "/data.txt");
            listOfGoals = objectMapper.readValue(file, new TypeReference<List<GoalClass>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

        EditText goalTitle = findViewById(R.id.GoalEntry);
        Button addButton = findViewById(R.id.addGoal);
        addButton.setOnClickListener(unused -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.create();
            if (!(goalTitle.getText().equals(""))) {
                GoalClass gc = new GoalClass();
                ObjectMapper objectMapper = new ObjectMapper();
                gc.setTitle(goalTitle.getText().toString());
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                try {
                    listOfGoals.add(gc);
                    objectMapper.writeValue(new File (getApplicationContext().getFilesDir(),"data.txt"), listOfGoals);
                    dialog.setMessage("Added Goal!");
                    dialog.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                dialog.setMessage("Please Fill out all information");
                dialog.show();
            }
        });
    }
    /**public static void createJSON(String path, String value) {
        try {
            JsonGenerator jsonGenerator = new JsonFactory()
                    .createGenerator(new FileOutputStream(path));
            jsonGenerator.writeStartObject(); // start root object
            jsonGenerator.writeStringField("data",value);

            jsonGenerator.writeObjectFieldStart("goal"); //start address object
            jsonGenerator.writeStringField("title", value);
            jsonGenerator.writeArrayFieldStart("tasks"); //start cities array
            jsonGenerator.writeEndArray(); //closing cities array
            jsonGenerator.writeEndObject(); //end address object

            jsonGenerator.writeEndObject(); //closing root object
            jsonGenerator.flush();
            jsonGenerator.close();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }**/
}
