package com.example.cs_125_project_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
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
        WebApi.startRequest(this, WebApi.API_BASE + "/games", response -> {
            // Code in this handler will run when the request completes successfully
            // Do something with the response?
            setUpDisplay(response);
        }, error -> {
            // Code in this handler will run if the request fails
            // Maybe notify the user of the error?
            Toast.makeText(this, "Oh no!", Toast.LENGTH_LONG).show();
        });
    }

    public void setUpDisplay(final JsonObject result ) {
        ViewGroup taskList = findViewById(R.id.taskList);
        taskList.removeAllViews();;

        JsonArray taskArray = result.get("tasks").getAsJsonArray();
    }


}
