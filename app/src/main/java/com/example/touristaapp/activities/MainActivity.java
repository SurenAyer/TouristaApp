package com.example.touristaapp.activities;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.touristaapp.R;
import com.example.touristaapp.fragments.MapsFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends BaseActivity {

//    PermissionsManager permissionsManager;
//    LocationEngine locationEngine;
//    private MapView mapView;
//    private MapboxMap map;
//    private static final String TAG = "MainActivity";
    //private boolean isFirstLocationUpdate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationBarView navigation = findViewById(R.id.bottomNavigationView);
        setupNavigation(navigation, MainActivity.class, R.id.home);

        // Initialize fragment
        Fragment fragment=new MapsFragment();

        // Open fragment
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.map_view,fragment)
                .commit();

    //  isFirstLocationUpdate = true;
      /*  if (PermissionsManager.areLocationPermissionsGranted(this)) {
            Log.d(TAG, "onCreate: Permissions granted");
        } else {
            permissionsManager = new PermissionsManager(permissionsListener);
            permissionsManager.requestLocationPermissions(this);
            Toast.makeText(getApplicationContext(), "Kindly allow location permission", Toast.LENGTH_LONG).show();
        }*/

//        mapView = (MapView) findViewById(R.id.map_view);
//        map = mapView.getMapboxMap();

        /*locationEngine = LocationEngineProvider.getBestLocationEngine(this);
        LocationEngineRequest request = new LocationEngineRequest.Builder(1000L)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(5000L).build();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationEngineCallback<LocationEngineResult> callback = new LocationEngineCallback<LocationEngineResult>() {
            @Override
            public void onSuccess(LocationEngineResult result) {
                Location lastLocation = result.getLastLocation();
                if (lastLocation != null && isFirstLocationUpdate) {
                    setCamerpostion(lastLocation);
                    isFirstLocationUpdate = false;
                }
            }

            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        };
        locationEngine.requestLocationUpdates(request, callback, getMainLooper());

        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                addAnnotationToMap();
            }
        });
*/    }

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    PermissionsListener permissionsListener = new PermissionsListener() {
        @Override
        public void onExplanationNeeded(@NonNull List<String> list) {
        }

        @Override
        public void onPermissionResult(boolean granted) {
            if (granted) {
                Log.d(TAG, "onPermissionResult: Permission granted");
            } else {
                Log.d(TAG, "onPermissionResult: Permission not granted");
            }
        }
    };

    void setCamerpostion(Location camerpostion) {
        map.setCamera(new CameraOptions.Builder()
                .center(Point.fromLngLat(camerpostion.getLongitude(), camerpostion.getLatitude()))
                .zoom(2.0)
                .build());
    }



    private void addAnnotationToMap() {
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
        PointAnnotationManager pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin,mapView);
            // Set options for the resulting symbol layer.
            PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                    // Define a geographic coordinate.
                    .withPoint(Point.fromLngLat(144.9297, -37.658))
                    // Specify the bitmap you assigned to the point annotation
                    // The bitmap will be added to map style automatically.
                    .withIconImage(String.valueOf(R.drawable.ic_home_foreground));
            // Add the resulting pointAnnotation to the map.
            pointAnnotationManager.create(pointAnnotationOptions);
    }

    private Bitmap bitmapFromDrawableRes(Context context, @DrawableRes int resourceId) {
        Drawable drawable = AppCompatResources.getDrawable(context, resourceId);
        return convertDrawableToBitmap(drawable);
    }
    private Bitmap convertDrawableToBitmap(Drawable sourceDrawable) {
        if (sourceDrawable == null) {
            return null;
        }
        if (sourceDrawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) sourceDrawable).getBitmap();
        } else {
            // copying drawable object to not manipulate on the same reference
            Drawable.ConstantState constantState = sourceDrawable.getConstantState();
            if (constantState == null) {
                return null;
            }
            Drawable drawable = constantState.newDrawable().mutate();
            Bitmap bitmap = Bitmap.createBitmap(
                    drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    Bitmap.Config.ARGB_8888
            );
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }


    }

*/

}