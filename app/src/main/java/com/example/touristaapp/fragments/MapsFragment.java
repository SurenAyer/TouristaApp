package com.example.touristaapp.fragments;

import android.Manifest.permission;
import android.annotation.SuppressLint;

import com.example.touristaapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
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
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
        addMarker(googleMap);

    }

    private void addMarker(GoogleMap googleMap) {
        MarkerOptions markerOptions=new MarkerOptions();
        // Set position of marker
        //if latlong is used then marker is created at clicked position
        LatLng newLatLng = new LatLng(-37.65, 144.92);
        markerOptions.position(newLatLng);
        // Set title of marker
        //markerOptions.title(latLng.latitude+" : "+latLng.longitude);
        markerOptions.title("Hello World");
        markerOptions.contentDescription("This is a marker");
        markerOptions.icon(BitmapFromVector(
                getActivity().getApplicationContext(),
                R.drawable.hindu));
        // Remove all marker
        googleMap.clear();
        // Animating to zoom the marker
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng,13));
        // Add marker on map
        googleMap.addMarker(markerOptions);
        /*googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // When clicked on map
                // Initialize marker options
                MarkerOptions markerOptions=new MarkerOptions();
                // Set position of marker
                //if latlong is used then marker is created at clicked position
                LatLng newLatLng = new LatLng(-37.65, 144.92);
                markerOptions.position(newLatLng);
                // Set title of marker
                //markerOptions.title(latLng.latitude+" : "+latLng.longitude);
                markerOptions.title("Hello World");
                markerOptions.contentDescription("This is a marker");
                markerOptions.icon(BitmapFromVector(
                        getActivity().getApplicationContext(),
                        R.drawable.hindu));
                // Remove all marker
                googleMap.clear();
                // Animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
                // Add marker on map
                googleMap.addMarker(markerOptions);
            }
        });*/
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
}