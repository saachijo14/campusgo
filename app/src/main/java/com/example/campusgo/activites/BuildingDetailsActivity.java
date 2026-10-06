package com.example.campusgo.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campusgo.R;
import com.example.campusgo.models.Building;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BuildingDetailsActivity extends AppCompatActivity {

    private MediaPlayer player;

    private FloatingActionButton fabPlay;
    private TextView tvAudioStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_building_details
        );

        Building building =
                (Building) getIntent()
                        .getSerializableExtra("building");

        if (building == null) {
            finish();
            return;
        }

        TextView tvEmoji =
                findViewById(R.id.tvEmoji);

        TextView tvName =
                findViewById(R.id.tvName);

        TextView tvDesc =
                findViewById(R.id.tvDesc);

        Chip chipCategory =
                findViewById(R.id.chipCategory);

        Chip chipTiming =
                findViewById(R.id.chipTiming);

        tvEmoji.setText(building.emoji);
        tvName.setText(building.name);
        tvDesc.setText(building.description);

        chipCategory.setText(building.category);

        chipTiming.setText(
                "🕒 " + building.timing
        );

        fabPlay =
                findViewById(R.id.fabPlay);

        tvAudioStatus =
                findViewById(R.id.tvAudioStatus);

        // Back
        findViewById(R.id.tvBack)
                .setOnClickListener(
                        v -> finish()
                );

        // Navigate
        findViewById(R.id.btnNavigate)
                .setOnClickListener(v -> {

                    Uri uri =
                            Uri.parse(
                                    "google.navigation:q="
                                            + building.lat
                                            + ","
                                            + building.lng
                                            + "&mode=w"
                            );

                    Intent intent =
                            new Intent(
                                    Intent.ACTION_VIEW,
                                    uri
                            );

                    intent.setPackage(
                            "com.google.android.apps.maps"
                    );

                    if (intent.resolveActivity(
                            getPackageManager()
                    ) != null) {

                        startActivity(intent);

                    } else {

                        Toast.makeText(
                                this,
                                "Google Maps not installed",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

        fabPlay.setOnClickListener(
                v -> toggleAudio()
        );
    }

    private void toggleAudio() {

        int audioId =
                getResources().getIdentifier(
                        "campus_audio",
                        "raw",
                        getPackageName()
                );

        if (audioId == 0) {

            Toast.makeText(
                    this,
                    "Add campus_audio.mp3 to res/raw",
                    Toast.LENGTH_LONG
            ).show();

            return;
        }

        if (player == null) {

            player =
                    MediaPlayer.create(
                            this,
                            audioId
                    );

            player.setOnCompletionListener(
                    mp -> {

                        fabPlay.setImageResource(
                                R.drawable.ic_play
                        );

                        tvAudioStatus.setText(
                                "Finished"
                        );
                    }
            );
        }

        if (player.isPlaying()) {

            player.pause();

            fabPlay.setImageResource(
                    R.drawable.ic_play
            );

            tvAudioStatus.setText(
                    "Paused"
            );

        } else {

            player.start();

            fabPlay.setImageResource(
                    R.drawable.ic_pause
            );

            tvAudioStatus.setText(
                    "Playing..."
            );
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (player != null) {

            player.release();
            player = null;
        }
    }
}