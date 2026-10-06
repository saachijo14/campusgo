package com.example.campusgo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campusgo.R;
import com.example.campusgo.api.ApiService;
import com.example.campusgo.api.RetrofitClient;
import com.example.campusgo.models.Announcement;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnnouncementActivity extends AppCompatActivity {

    private LinearLayout announcementContainer;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_announcement
        );

        announcementContainer =
                findViewById(
                        R.id.announcementContainer
                );

        progressBar =
                findViewById(
                        R.id.progressBar
                );

        findViewById(R.id.tvBack)
                .setOnClickListener(
                        v -> finish()
                );

        loadAnnouncements();
    }

    private void loadAnnouncements() {

        progressBar.setVisibility(View.VISIBLE);

        ApiService api =
                RetrofitClient
                        .getInstance()
                        .create(ApiService.class);

        api.getAnnouncements()
                .enqueue(
                        new Callback<List<Announcement>>() {

                            @Override
                            public void onResponse(
                                    Call<List<Announcement>> call,
                                    Response<List<Announcement>> response) {

                                progressBar.setVisibility(
                                        View.GONE
                                );

                                if (response.isSuccessful()
                                        && response.body() != null) {

                                    showAnnouncements(
                                            response.body()
                                    );

                                } else {

                                    Toast.makeText(
                                            AnnouncementActivity.this,
                                            "Unable to load announcements",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }

                            @Override
                            public void onFailure(
                                    Call<List<Announcement>> call,
                                    Throwable t) {

                                progressBar.setVisibility(
                                        View.GONE
                                );

                                Toast.makeText(
                                        AnnouncementActivity.this,
                                        "Internet connection required",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                );
    }

    private void showAnnouncements(
            List<Announcement> announcements) {

        String[] titles = {
                "Placement Drive",
                "Annual Sports Day",
                "Campus Cleanliness Drive",
                "Technical Workshop",
                "Library Timings Updated"
        };

        String[] descriptions = {
                "Campus placement drive will be conducted this week. Students are requested to check the placement cell for details.",
                "The annual sports day will be conducted on Friday. Students can register through the sports committee.",
                "A campus cleanliness drive will be conducted this Saturday. Students are encouraged to participate.",
                "A technical workshop on modern software development will be conducted this week.",
                "The library will remain open from 8:00 AM to 8:00 PM on working days."
        };

        for (int i = 0; i < titles.length; i++) {

            TextView textView =
                    new TextView(this);

            textView.setText(
                    "📢  " + titles[i]
                            + "\n\n"
                            + descriptions[i]
            );

            textView.setTextSize(15);

            textView.setTextColor(
                    getResources().getColor(
                            R.color.text_primary
                    )
            );

            textView.setPadding(
                    30,
                    30,
                    30,
                    30
            );

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            params.setMargins(
                    0,
                    0,
                    0,
                    25
            );

            textView.setLayoutParams(params);

            textView.setBackgroundColor(
                    getResources().getColor(
                            R.color.surface
                    )
            );

            announcementContainer.addView(
                    textView
            );
        }
    }
}

