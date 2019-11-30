package com.example.cs_125_project_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
        final TextView textView = findViewById(R.id.text);

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
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
