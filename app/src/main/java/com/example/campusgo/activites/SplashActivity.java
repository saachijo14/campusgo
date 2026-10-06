package com.example.campusgo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campusgo.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        // Logo animation
        Animation scaleAnimation =
                AnimationUtils.loadAnimation(
                        this,
                        R.anim.scale_pop
                );

        findViewById(R.id.logoCard)
                .startAnimation(scaleAnimation);

        // Title animation
        Animation fadeAnimation =
                AnimationUtils.loadAnimation(
                        this,
                        R.anim.fade_in
                );

        findViewById(R.id.tvTitle)
                .startAnimation(fadeAnimation);

        // Tagline animation
        Animation slideAnimation =
                AnimationUtils.loadAnimation(
                        this,
                        R.anim.slide_up
                );

        findViewById(R.id.tvTagline)
                .startAnimation(slideAnimation);

        // Move to Login screen
        new Handler(Looper.getMainLooper())
                .postDelayed(() -> {

                    Intent intent =
                            new Intent(
                                    SplashActivity.this,
                                    LoginActivity.class
                            );

                    startActivity(intent);

                    overridePendingTransition(
                            R.anim.fade_in,
                            android.R.anim.fade_out
                    );

                    finish();

                }, SPLASH_DURATION);
    }
}