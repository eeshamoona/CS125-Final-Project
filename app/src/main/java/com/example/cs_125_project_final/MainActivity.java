package com.example.cs_125_project_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

/**
 * Main Activity class.
 */
public class MainActivity extends AppCompatActivity {

    /** RadioButton Instance Variable. */
    //private int RC_SIGN_IN = 0;
    /**
     * onCreate called at the start Login Screen of the game.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) { // see below discussion
            startActivity(new Intent(this, UIScreen.class)); // launch MainActivity
            finish();
        } else {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build());
            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    0); // start login activity for result - see below discussion

            Button loginIn = findViewById(R.id.goLogin);
            loginIn.setOnClickListener(unused -> startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    0));
        }
    }

    /**
     * onActivityResult checks the code.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            System.out.println(resultCode == RESULT_OK);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                startActivity(new Intent(this, UIScreen.class)); // launch MainActivity
                finish();
            }
        }
    }

}
