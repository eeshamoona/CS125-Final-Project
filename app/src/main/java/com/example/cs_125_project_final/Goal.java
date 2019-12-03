package com.example.cs_125_project_final;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.google.api.services.tasks.model.TaskList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Screen from for Creating a new Goal.
 */
public class Goal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        EditText goalTitle = findViewById(R.id.GoalEntry);
        Button addButton = findViewById(R.id.addGoal);
        addButton.setOnClickListener(unused -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.create();
            if (!(goalTitle.getText().equals(""))) {
                String post = "/users/@me/lists";
                WebApi.startRequest(this, WebApi.API_BASE + post,
                        Request.Method.POST, null, response -> {
                        }, error -> {
                            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                        });
            } else {
                dialog.setMessage("Please Fill out all information");
                dialog.show();
            }
        });
    }
}
