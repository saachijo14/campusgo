package com.example.campusgo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.campusgo.R;
import com.example.campusgo.activities.AnnouncementActivity;
import com.example.campusgo.activities.MainActivity;

public class HomeFragment extends Fragment {

    private TextView tvWelcomeName;

    public HomeFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(
                R.layout.fragment_home,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        tvWelcomeName = view.findViewById(R.id.tvWelcomeName);

        // Get user's name from MainActivity
        if (getActivity() != null) {

            String name = getActivity()
                    .getIntent()
                    .getStringExtra("name");

            if (name != null && !name.isEmpty()) {

                String formattedName =
                        name.substring(0, 1).toUpperCase()
                                + name.substring(1);

                tvWelcomeName.setText(
                        "Good to see you, " + formattedName + " 👋"
                );
            }
        }

        // Open Map
        view.findViewById(R.id.cardMap)
                .setOnClickListener(v -> {

                    if (getActivity() instanceof MainActivity) {

                        ((MainActivity) getActivity())
                                .openTab(R.id.nav_map);
                    }
                });

        // Open Explore
        view.findViewById(R.id.cardExplore)
                .setOnClickListener(v -> {

                    if (getActivity() instanceof MainActivity) {

                        ((MainActivity) getActivity())
                                .openTab(R.id.nav_explore);
                    }
                });

        // Announcements
        view.findViewById(R.id.cardAnnouncements)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(
                            requireContext(),
                            AnnouncementActivity.class
                    );

                    startActivity(intent);
                });

        // Favorites
        view.findViewById(R.id.cardFavorites)
                .setOnClickListener(v -> {

                    if (getActivity() instanceof MainActivity) {

                        ((MainActivity) getActivity())
                                .openTab(R.id.nav_profile);
                    }
                });

        // Search
        view.findViewById(R.id.cardSearch)
                .setOnClickListener(v -> {

                    if (getActivity() instanceof MainActivity) {

                        ((MainActivity) getActivity())
                                .openTab(R.id.nav_explore);
                    }
                });

        // Nearby
        view.findViewById(R.id.cardNearby)
                .setOnClickListener(v -> {

                    if (getActivity() instanceof MainActivity) {

                        ((MainActivity) getActivity())
                                .openTab(R.id.nav_map);
                    }
                });
    }
}