package com.example.touristaapp.fragments;

import android.Manifest.permission;
import android.annotation.SuppressLint;

import com.example.touristaapp.R;
import com.example.touristaapp.models.TouristAttraction;
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

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
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

    private List<TouristAttraction> touristAttractionList;
    private String category;

    private OnMapReadyListener onMapReadyListener;

    public void setOnMapReadyListener(OnMapReadyListener onMapReadyListener) {
        this.onMapReadyListener = onMapReadyListener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // ...
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableMyLocation();
        //Initial Camera Focus
        LatLng newLatLng = new LatLng(-37.65, 144.92);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 13));
        //addMarker(getContext());
        if (onMapReadyListener != null) {
            onMapReadyListener.onMapReady();
        }
        // Set an OnMarkerClickListener
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getActivity(), "Marker Clicked", Toast.LENGTH_SHORT).show();
                return true; // Return true to indicate that we have handled the event and that we do not wish for the default behavior to occur (which is for the camera to move such that the marker is centered and for the marker's info window to open, if it has one).
            }
        });
    }

    public void addMarker(Context context, List<TouristAttraction> touristAttractionList, String category) {
    if (map != null) {
        map.clear(); // Clear old markers
        for (TouristAttraction attraction : touristAttractionList) {
            // Inflate the custom marker layout
            View markerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
            TextView markerTitle = markerView.findViewById(R.id.marker_title);
            markerTitle.setText(attraction.getName()); // Set the title
            ImageView imageView = markerView.findViewById(R.id.marker_icon);
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
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap)); // Set the icon to the custom view
            map.addMarker(markerOptions);
        }
    }
}

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            return;
        }
        else{
            Toast.makeText(getActivity(), "Kindly allow location permission", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getActivity(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (permissionDenied) {
            permissionDenied = false;
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        // Getting latitude of the current location
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
       LatLng latLng = new LatLng(latitude, longitude);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

//        float speed = location.getSpeed();
//
//        // Creating a LatLng object for the current location
//        LatLng latLng = new LatLng(latitude, longitude);
//
//        // Showing the current location in Google Map
//        CameraPosition camPos = new CameraPosition.Builder()
//                .target(new LatLng(latitude, longitude))
//                .zoom(18)
//                .bearing(location.getBearing())
//                .tilt(70)
//                .build();
//        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
//        map.animateCamera(camUpd3);
    }

    private BitmapDescriptor
    BitmapFromVector(Context context, int vectorResId)
    {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(
                context, vectorResId);

        // below line is use to set bounds to our vector
        // drawable.
        vectorDrawable.setBounds(
                0, 0, vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(
                vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our
        // bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public List<TouristAttraction> getTouristAttractionList() {
        return touristAttractionList;
    }

    public String getCategory() {
        return category;
    }

    public void setTouristAttractionList(List<TouristAttraction> touristAttractionList) {
        this.touristAttractionList = touristAttractionList;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public interface OnMapReadyListener {
        void onMapReady();
    }
}