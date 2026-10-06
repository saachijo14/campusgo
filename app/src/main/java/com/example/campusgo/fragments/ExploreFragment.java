package com.example.campusgo.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.campusgo.R;
import com.example.campusgo.activities.BuildingDetailsActivity;
import com.example.campusgo.models.Building;
import com.example.campusgo.models.CampusDatabaseHelper;

import android.content.Intent;

public class ExploreFragment extends Fragment {

    private LinearLayout buildingContainer;
    private CampusDatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        buildingContainer = view.findViewById(R.id.buildingContainer);

        databaseHelper = new CampusDatabaseHelper(requireContext());

        loadBuildings();

        // Fade-in animation
        Animation animation = AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.fade_in
        );

        view.startAnimation(animation);

        return view;
    }

    private void loadBuildings() {

        Cursor cursor = databaseHelper.getBuildings();

        if (cursor == null) {
            return;
        }

        while (cursor.moveToNext()) {

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow("name"));

            String category = cursor.getString(
                    cursor.getColumnIndexOrThrow("category"));

            String timing = cursor.getString(
                    cursor.getColumnIndexOrThrow("timing"));

            String description = cursor.getString(
                    cursor.getColumnIndexOrThrow("description"));

            // Create Building object
            Building building = createBuilding(
                    name,
                    category,
                    timing,
                    description
            );

            // Create beautiful card
            LinearLayout card = createBuildingCard(building);

            buildingContainer.addView(card);
        }

        cursor.close();
    }

    private Building createBuilding(String name,
                                    String category,
                                    String timing,
                                    String description) {

        double lat = 19.045601;
        double lng = 72.889057;
        String emoji = "🏫";

        if (name.equals("Central Library")) {

            lat = 19.045769;
            lng = 72.889057;
            emoji = "📚";

        } else if (name.equals("Main Building")) {

            lat = 19.045601;
            lng = 72.889057;
            emoji = "🏫";

        } else if (name.equals("Campus Canteen")) {

            lat = 19.045287405433907;
            lng = 72.88881201858689;
            emoji = "🍔";
        }

        return new Building(
                name,
                description,
                category,
                timing,
                lat,
                lng,
                emoji
        );
    }

    private LinearLayout createBuildingCard(Building building) {

        LinearLayout card = new LinearLayout(requireContext());

        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(30, 25, 30, 25);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

        cardParams.setMargins(0, 0, 0, 20);

        card.setLayoutParams(cardParams);

        // Card background
        card.setBackgroundResource(R.drawable.bg_icon_teal);

        // Building name
        TextView name = new TextView(requireContext());

        name.setText(
                building.emoji + "  " + building.name
        );

        name.setTextSize(20);
        name.setTextColor(
                getResources().getColor(R.color.text_primary)
        );
        name.setTypeface(null, android.graphics.Typeface.BOLD);

        // Category
        TextView category = new TextView(requireContext());

        category.setText(
                "Category: " + building.category
        );

        category.setTextSize(14);
        category.setTextColor(
                getResources().getColor(R.color.text_primary)
        );

        category.setPadding(0, 10, 0, 0);

        // Timing
        TextView timing = new TextView(requireContext());

        timing.setText(
                "🕒 " + building.timing
        );

        timing.setTextSize(14);
        timing.setTextColor(
                getResources().getColor(R.color.text_primary)
        );

        timing.setPadding(0, 5, 0, 0);

        // Description
        TextView description = new TextView(requireContext());

        description.setText(building.description);

        description.setTextSize(14);
        description.setTextColor(
                getResources().getColor(R.color.text_primary)
        );

        description.setPadding(0, 8, 0, 0);

        // Add views
        card.addView(name);
        card.addView(category);
        card.addView(timing);
        card.addView(description);

        // Click → Building Details
        card.setOnClickListener(v -> {

            Intent intent = new Intent(
                    requireContext(),
                    BuildingDetailsActivity.class
            );

            intent.putExtra("building", building);

            startActivity(intent);
        });

        // Scale animation when card appears
        Animation animation = AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.scale_pop
        );

        card.startAnimation(animation);

        return card;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}