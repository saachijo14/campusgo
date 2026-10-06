package com.example.campusgo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campusgo.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Initialize views
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        // Login card animation
        findViewById(R.id.loginCard).startAnimation(
                AnimationUtils.loadAnimation(
                        this,
                        R.anim.slide_up
                )
        );

        // Login button
        findViewById(R.id.btnLogin).setOnClickListener(
                v -> attemptLogin()
        );

        // Continue as Guest
        findViewById(R.id.tvGuest).setOnClickListener(
                v -> openMain(
                        "Guest",
                        "guest@campusgo.com"
                )
        );
    }

    private void attemptLogin() {

        String email =
                etEmail.getText()
                        .toString()
                        .trim();

        String password =
                etPassword.getText()
                        .toString();

        // Clear previous errors
        tilEmail.setError(null);
        tilPassword.setError(null);

        // Validate email
        if (!Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()) {

            tilEmail.setError(
                    "Enter a valid email address"
            );

            return;
        }

        // Validate password
        if (password.length() < 6) {

            tilPassword.setError(
                    "Minimum 6 characters required"
            );

            return;
        }

        /*
         * Login validation is currently local.
         * We are using SharedPreferences to store
         * the logged-in user's information.
         */

        String name =
                email.substring(
                        0,
                        email.indexOf('@')
                );

        openMain(name, email);
    }

    private void openMain(
            String name,
            String email
    ) {

        // Save user information
        getSharedPreferences(
                "CampusGoPrefs",
                MODE_PRIVATE
        )
                .edit()
                .putString("name", name)
                .putString("email", email)
                .apply();

        Intent intent =
                new Intent(
                        LoginActivity.this,
                        MainActivity.class
                );

        intent.putExtra("name", name);
        intent.putExtra("email", email);

        startActivity(intent);

        finish();
    }
}