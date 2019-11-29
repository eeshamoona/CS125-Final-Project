package com.example.cs_125_project_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class UIScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiscreen);
    }

    /** Use this method to refresh the tasks being displayed */
    public void refresh() {

    }

    public void setUpDisplay(final JsonObject result) {
        ViewGroup taskList = findViewById(R.id.taskList);
        taskList.removeAllViews();;

        JsonArray taskArray = result.get("tasks").getAsJsonArray();
        for (JsonElement taskElement : taskArray) {
            ViewGroup taskGroup = findViewById(R.id.taskGroup);
            View taskChunk = getLayoutInflater().inflate(R.layout.chunk_uiscreen,
                    taskList, false);
            JsonObject taskObj = taskElement.getAsJsonObject();

            TextView task = taskChunk.findViewById(R.id.taskToDo);
            task.setText(taskObj.getAsString());
        }
    }


}
