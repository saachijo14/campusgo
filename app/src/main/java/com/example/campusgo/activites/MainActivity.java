package com.example.campusgo.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.campusgo.R;
import com.example.campusgo.fragments.ExploreFragment;
import com.example.campusgo.fragments.HomeFragment;
import com.example.campusgo.fragments.MapFragment;
import com.example.campusgo.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            Fragment fragment;

            if (id == R.id.nav_home) {

                fragment = new HomeFragment();

            } else if (id == R.id.nav_map) {

                fragment = new MapFragment();

            } else if (id == R.id.nav_explore) {

                fragment = new ExploreFragment();

            } else {

                fragment = new ProfileFragment();
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.fade_in,
                            android.R.anim.fade_out
                    )
                    .replace(
                            R.id.fragmentContainer,
                            fragment
                    )
                    .commit();

            return true;
        });

        // Open Home when the app starts
        if (savedInstanceState == null) {

            bottomNav.setSelectedItemId(
                    R.id.nav_home
            );
        }
    }

    /**
     * Allows fragments to switch
     * to another bottom-navigation tab.
     */
    public void openTab(int menuId) {

        bottomNav.setSelectedItemId(menuId);
    }
}