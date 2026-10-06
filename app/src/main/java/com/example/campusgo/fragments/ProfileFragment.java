package com.example.campusgo.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.campusgo.R;
import com.example.campusgo.activities.LoginActivity;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_profile,
                container,
                false
        );

        // Find profile fields
        TextView profileName =
                view.findViewById(R.id.profileName);

        TextView profileEmail =
                view.findViewById(R.id.profileEmail);

        // Get saved user information
        SharedPreferences prefs =
                requireActivity().getSharedPreferences(
                        "CampusGoPrefs",
                        requireActivity().MODE_PRIVATE
                );

        String name =
                prefs.getString("name", "Campus User");

        String email =
                prefs.getString(
                        "email",
                        "No email available"
                );

        // Display user information
        profileName.setText(name);
        profileEmail.setText(email);

        // Logout
        View btnLogout =
                view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {

            // Clear saved login information
            prefs.edit().clear().apply();

            Intent intent =
                    new Intent(
                            requireContext(),
                            LoginActivity.class
                    );

            // Remove previous screens
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
        });

        return view;
    }
}