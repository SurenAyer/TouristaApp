package com.example.touristaapp.fragments;

import android.Manifest.permission;
import android.annotation.SuppressLint;

import com.example.touristaapp.R;
import com.example.touristaapp.activities.ContributeActivity;
import com.example.touristaapp.activities.MainActivity;
import com.example.touristaapp.activities.ViewPlaceActivity;
import com.example.touristaapp.models.TouristAttraction;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.List;

public class MapsFragment extends Fragment
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private GoogleMap map;

    private Gson gson;

    private OnMapReadyListener onMapReadyListener;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng defaultLocation;

    private LatLng markerPosition;
    private Location userLocation;
    private List<TouristAttraction> touristAttractionList;

    private String TAG = "MapsFragmentTAG";

    public void setOnMapReadyListener(OnMapReadyListener onMapReadyListener) {
        this.onMapReadyListener = onMapReadyListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        // Initialize the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        return view;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        gson = new Gson();
        //Initial Camera Focus
        defaultLocation = new LatLng(-37.65, 144.92);
        enableMyLocation();

        //addMarker(getContext());
        if (onMapReadyListener != null) {
            onMapReadyListener.onMapReady();
        }
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (getActivity().getClass() == ContributeActivity.class) {
                    //Clear all existing markers
                    map.clear();
                    // Create a new MarkerOptions object
                    MarkerOptions markerOptions = new MarkerOptions();
                    // Set the position of the MarkerOptions object to the clicked location
                    markerOptions.position(latLng);
                    // Add the MarkerOptions object to the GoogleMap object
                    map.addMarker(markerOptions);
                    markerPosition = latLng;
                }

            }
        });
    }

    @SuppressLint("PotentialBehaviorOverride")
    public void addMarker(Context context, List<TouristAttraction> touristAttractions, String category) {
        touristAttractionList = touristAttractions;
        if (map != null) {
            map.clear(); // Clear old markers
            for (TouristAttraction attraction : touristAttractionList) {
                // Inflate the custom marker layout
                View markerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
                TextView markerTitle = markerView.findViewById(R.id.marker_title);
                markerTitle.setText(attraction.getName()); // Set the title
                ImageView imageView = markerView.findViewById(R.id.marker_icon);
                Log.d(TAG, "addMarker: " + category);
                int resourceId = context.getResources().getIdentifier("pin_" + category.toLowerCase(), "drawable", context.getPackageName());
                imageView.setImageResource(resourceId);

                // Convert the view to a bitmap
                markerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                markerView.layout(0, 0, markerView.getMeasuredWidth(), markerView.getMeasuredHeight());
                Bitmap bitmap = Bitmap.createBitmap(markerView.getMeasuredWidth(), markerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                markerView.draw(canvas);
                // Create the marker
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng latLng = new LatLng(attraction.getLatitude(), attraction.getLongitude());
                markerOptions.position(latLng);
                markerOptions.title(String.valueOf(attraction.getAttractionId()));
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap)); // Set the icon to the custom view
                map.addMarker(markerOptions);
            }
        } else {
            Log.d(TAG, "addMarker: map is null");
        }
        //Focus on Marker
        // Set an OnMarkerClickListener
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (getActivity().getClass() == MainActivity.class) {
                    Intent markerIntent = new Intent(getActivity(), ViewPlaceActivity.class);
                    String attractionId = marker.getTitle();
                    TouristAttraction attraction = null;
                    for (TouristAttraction ta : touristAttractionList) {
                        if (ta.getAttractionId().equals(attractionId)) {
                            attraction = ta;
                            break;
                        }
                    }
                    String placeJson = gson.toJson(attraction);
                    markerIntent.putExtra("touristAttraction", placeJson);
                    startActivity(markerIntent);
                } else {
                    //Add address here
                    marker.setTitle(touristAttractionList.get(0).getName());
                    marker.showInfoWindow();
                    marker.showInfoWindow();
                }
                // Update the camera focus to the marker's position
                LatLng markerPosition = marker.getPosition();
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 10));

                return true; // Return true to indicate that we have handled the event and that we do not wish for the default behavior to occur (which is for the camera to move such that the marker is centered and for the marker's info window to open, if it has one).
            }
        });
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                userLocation = task.getResult();
                                //Focus on User Location
                                if (getActivity().getClass() == MainActivity.class) {
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 13));
                                } else if (getActivity().getClass() == ContributeActivity.class) {
                                    focusOnUserLocation();
                                }
                                //Focus on Marker
                                else {
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(touristAttractionList.get(0).getLatitude(), touristAttractionList.get(0).getLongitude()), 14));
                                }
                            } else {
                                Log.d(TAG, "Failed to get location.");
                            }
                        }
                    });
            return;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            focusOnUserLocation();
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (permissionDenied) {
            permissionDenied = false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // Getting latitude of the current location
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

    }


    public void focusOnUserLocation() {
        // Get the current user location
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);

            fusedLocationClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                userLocation = task.getResult();
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 13));
                            } else {
                                Log.d("MapsFragment", "Failed to get location.");
                            }
                        }
                    });
            return;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            focusOnUserLocation();
        }


    }


    public LatLng getLastMarkerPosition() {
        return markerPosition;
    }

    public interface OnMapReadyListener {
        void onMapReady();
    }

    public GoogleMap getGoogleMap() {
        return map;
    }
}