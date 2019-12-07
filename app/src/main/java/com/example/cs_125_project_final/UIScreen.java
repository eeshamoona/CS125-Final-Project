package com.example.cs_125_project_final;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UIScreen extends AppCompatActivity {
    private List<GoalClass> readListOfGoals = new ArrayList<>();
    private GoalClass printingClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String toPrint = getIntent().getStringExtra("GoalTitle");
        System.out.println(toPrint);
        setTitle("Goal: " + toPrint);
        setContentView(R.layout.activity_uiscreen);

        Button addTask = findViewById(R.id.addTask);
        Intent intent = new Intent(this, Task.class);
        intent.putExtra("GoalTitleTask", toPrint);
        addTask.setOnClickListener(unused -> startActivity(intent));

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(unused -> startActivity(new Intent(this, Home.class)));

        printingClass = findGoalClass();
        setUpUI();

        //finish();
    }

    public void setUpUI() {
        if (printingClass != null && printingClass.getTasks().length != 0) {
            LinearLayout taskList = findViewById(R.id.taskGroup);
            taskList.removeAllViews();

            for (String s : printingClass.getTasks()) {
                View messageChunk = getLayoutInflater().inflate(R.layout.chunk_uiscreen,
                        taskList, false);
                CheckBox title = messageChunk.findViewById(R.id.taskToDo);
                title.setText(s);
                title.setOnClickListener(unused -> {
                    printingClass.addCompletedTask(s);//Add to completed list
                    printingClass.removeTask(s);//Remove from tasks
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                    try {
                        objectMapper.writeValue(new File (getApplicationContext().getFilesDir(),"data.txt"), readListOfGoals);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setUpUI();// set up UI again
                });
                taskList.addView(messageChunk);
                System.out.println(s);
            }
        }
    }

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
