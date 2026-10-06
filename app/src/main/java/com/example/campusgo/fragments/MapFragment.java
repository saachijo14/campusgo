package com.example.campusgo.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.campusgo.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment
        implements OnMapReadyCallback {

    private GoogleMap googleMap;

    private FusedLocationProviderClient fusedLocationClient;

    private LocationCallback locationCallback;

    private final ActivityResultLauncher<String>
            locationPermissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    granted -> {

                        if (granted) {
                            startLocationUpdates();
                        } else {
                            Toast.makeText(
                                    requireContext(),
                                    "Please allow location permission",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
            );

    public MapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(
                R.layout.fragment_map,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        fusedLocationClient =
                LocationServices
                        .getFusedLocationProviderClient(
                                requireActivity()
                        );

        SupportMapFragment mapFragment =
                (SupportMapFragment)
                        getChildFragmentManager()
                                .findFragmentById(
                                        R.id.campusMap
                                );

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        view.findViewById(R.id.btnMyLocation)
                .setOnClickListener(v -> requestLocation());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {

        googleMap = map;

        // Temporary campus location
        LatLng campus =
                new LatLng(
                        19.046327,
                        72.889955
                );

        googleMap.moveCamera(
                CameraUpdateFactory
                        .newLatLngZoom(
                                campus,
                                17f
                        )
        );

        // Main Building
        LatLng mainBuilding =
                new LatLng(19.045601, 72.889057);

        googleMap.addMarker(
                new MarkerOptions()
                        .position(mainBuilding)
                        .title("🏫 Main Building")
                        .snippet("Administration")
        );

// Central Library
        LatLng library =
                new LatLng(19.045769 , 72.889057);

        googleMap.addMarker(
                new MarkerOptions()
                        .position(library)
                        .title("📚 Central Library")
                        .snippet("Study • 8 AM - 8 PM")
        );

// Canteen
        LatLng canteen =
                new LatLng(19.045287405433907 , 72.88881201858689);

        googleMap.addMarker(
                new MarkerOptions()
                        .position(canteen)
                        .title("🍔 Canteen")
                        .snippet("Food • 9 AM - 6 PM")
        );
    }

    private void requestLocation() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {

            startLocationUpdates();

        } else {

            locationPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
            );
        }
    }

    private void startLocationUpdates() {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        googleMap.setMyLocationEnabled(true);

        LocationRequest request =
                new LocationRequest.Builder(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        3000
                )
                        .setMinUpdateIntervalMillis(1500)
                        .setMaxUpdateDelayMillis(5000)
                        .build();

        locationCallback =
                new LocationCallback() {

                    @Override
                    public void onLocationResult(
                            @NonNull LocationResult result) {

                        Location location =
                                result.getLastLocation();

                        if (location == null) {
                            return;
                        }

                        double latitude =
                                location.getLatitude();

                        double longitude =
                                location.getLongitude();

                        LatLng current =
                                new LatLng(
                                        latitude,
                                        longitude
                                );

                        googleMap.animateCamera(
                                CameraUpdateFactory
                                        .newLatLngZoom(
                                                current,
                                                17f
                                        )
                        );

                        Toast.makeText(
                                requireContext(),
                                "GPS location found",
                                Toast.LENGTH_SHORT
                        ).show();

                        // Stop after getting location
                        fusedLocationClient
                                .removeLocationUpdates(
                                        locationCallback
                                );
                    }
                };

        fusedLocationClient
                .requestLocationUpdates(
                        request,
                        locationCallback,
                        Looper.getMainLooper()
                );
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        if (locationCallback != null
                && fusedLocationClient != null) {

            fusedLocationClient
                    .removeLocationUpdates(
                            locationCallback
                    );
        }
    }
}