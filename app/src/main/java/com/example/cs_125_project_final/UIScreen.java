package com.example.cs_125_project_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class UIScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiscreen);

        Button addTask = findViewById(R.id.addTask);
        addTask.setOnClickListener(unused -> startActivity(new Intent(this, Task.class)));
        refresh();
        //finish();
    }

    /** Use this method to refresh the tasks being displayed */
    public void refresh() {
        WebApi.startRequest(this, WebApi.API_BASE + "/lists/tasklist/tasks",
                Request.Method.GET, null, response -> {
                    setUpDisplay(response);
                }, error -> {
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    public void setUpDisplay(final JsonObject result) {
        ViewGroup taskList = findViewById(R.id.taskList);
        taskList.removeAllViews();

        JsonArray taskArray = result.get("tasks").getAsJsonArray();
        for (JsonElement taskElement : taskArray) {
            ViewGroup taskGroup = findViewById(R.id.taskGroup);
            taskGroup.setVisibility(View.GONE);
            View taskChunk = getLayoutInflater().inflate(R.layout.chunk_uiscreen,
                    taskList, false);
            JsonObject taskObj = taskElement.getAsJsonObject();

            TextView task = taskChunk.findViewById(R.id.taskToDo);
            task.setText(taskObj.getAsString());
        }
    }


}
