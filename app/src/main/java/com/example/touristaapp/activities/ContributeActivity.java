package com.example.touristaapp.activities;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristaapp.R;
import com.example.touristaapp.fragments.MapsFragment;
import com.example.touristaapp.models.Photo;
import com.example.touristaapp.models.TouristAttraction;
import com.example.touristaapp.repositories.PhotoRepository;
import com.example.touristaapp.repositories.PhotoRepositoryImpl;
import com.example.touristaapp.repositories.TouristAttractionRepository;
import com.example.touristaapp.repositories.TouristAttractionRepositoryImpl;
import com.example.touristaapp.repositories.UserRepository;
import com.example.touristaapp.repositories.UserRepositoryImpl;
import com.example.touristaapp.utils.ImageAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ContributeActivity extends BaseActivity {
    private MapsFragment mapFragment;
    private TextInputEditText placeName;
    private TextInputEditText address;
    private TextInputEditText contactNumber;
    private TextInputEditText openHours;
    private TextInputEditText description;
    private AppCompatAutoCompleteTextView placeCategory;
    private Button btnCreatePlace, btnSelectImages;
    private final int REQUEST_CODE_PICK_IMAGES = 100;
    private final int REQUEST_CODE_TAKE_PHOTO = 101;
    private List<String> selectedImages = new ArrayList<>();
    private List<String> imageUrls = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private FirebaseStorage firebaseStorage;
    private TouristAttractionRepository touristAttractionRepository;
    private UserRepository userRepository;
    private PhotoRepository photoRepository;
    public static final String TAG = "ContributeActivity";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

  public static int generateRandomUniqueInteger() {
    UUID uuid = UUID.randomUUID();
    long mostSignificantBits = uuid.getMostSignificantBits();
    long leastSignificantBits = uuid.getLeastSignificantBits();
    return Math.abs((int) (mostSignificantBits ^ leastSignificantBits));
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Intent intent = new Intent(ContributeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        // Retrieve the login state
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        // If the user is not logged in, redirect to LoginActivity
        if (!isLoggedIn) {
            Intent intent = new Intent(ContributeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }


        NavigationBarView navigation = findViewById(R.id.bottomNavigationView);
        setupNavigation(navigation, ViewPlaceActivity.class, R.id.contribute);
        placeName = findViewById(R.id.placeName);
        address = findViewById(R.id.address);
        contactNumber = findViewById(R.id.contactNumber);
        openHours = findViewById(R.id.openHours);
        description = findViewById(R.id.description);
        placeCategory = findViewById(R.id.placeCategory);
        btnCreatePlace = findViewById(R.id.createPlaceBtn);
        mapFragment = new MapsFragment();

        btnSelectImages = findViewById(R.id.selectImageBtn);
        recyclerView = findViewById(R.id.recyclerView);
        imageAdapter = new ImageAdapter(selectedImages);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(numberOfColumns, spacingInPixels, true));
        recyclerView.setAdapter(imageAdapter);
        // Open fragment
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.map_view, mapFragment)
                .commit();


        String[] items = getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, items);
        TextInputLayout textField = findViewById(R.id.textField);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) textField.getEditText();
        if (autoCompleteTextView != null) {
            autoCompleteTextView.setAdapter(adapter);
        }
        touristAttractionRepository = new TouristAttractionRepositoryImpl();
        userRepository = new UserRepositoryImpl();

        btnSelectImages.setOnClickListener(v -> pickMultipleImages());
        btnCreatePlace.setOnClickListener(v -> {
            LatLng lastMarkerPosition = mapFragment.getLastMarkerPosition();
            if (lastMarkerPosition != null) {

                firebaseStorage = FirebaseStorage.getInstance();
                StorageReference storageRef = firebaseStorage.getReference();

                int attractionId = generateRandomUniqueInteger();
                double latitude = lastMarkerPosition.latitude;
                double longitude = lastMarkerPosition.longitude;
                String name = placeName.getText().toString();
                String descriptionText = description.getText().toString();
                String addressText = address.getText().toString();
                int contact = Integer.parseInt(contactNumber.getText().toString());
                String openHoursText = openHours.getText().toString();
                String categoryText = placeCategory.getText().toString();

                TouristAttraction touristAttraction = new TouristAttraction();
                touristAttraction.setAttractionId(attractionId);
                touristAttraction.setLatitude((float) latitude);
                touristAttraction.setLongitude((float) longitude);
                touristAttraction.setName(name);
                touristAttraction.setDescription(descriptionText);
                touristAttraction.setAddress(addressText);
                touristAttraction.setPhoneNumber(contact);
                touristAttraction.setOpenHours(openHoursText);
                touristAttraction.setCategory(categoryText);

                touristAttractionRepository.addTouristAttraction(touristAttraction, task -> {
                    if (task.isSuccessful()) {
                        DocumentReference documentReference = task.getResult();
                        if (documentReference != null) {
                            String attraction_id = documentReference.getId();
                            documentReference.get().addOnCompleteListener(documentTask -> {
                                if (documentTask.isSuccessful() && documentTask.getResult() != null) {
                                    TouristAttraction addedAttraction = documentTask.getResult().toObject(TouristAttraction.class);
                                    if (addedAttraction != null) {
                                        Log.d("CreatePlace", "DocumentSnapshot successfully written: " + addedAttraction);
                                        List<Photo> addedPhotos=new ArrayList<>();
                                        for (String imageUri: selectedImages) {
                                            Uri fileUri = Uri.parse(imageUri);
                                            String fileName = UUID.randomUUID().toString();
                                            StorageReference imageRef = storageRef.child("attractions/" + fileName);

                                            imageRef.putFile(fileUri)
                                                    .addOnSuccessListener(taskSnapshot -> {
                                                        Log.d("ImageUpload", "Image uploaded successfully: " + imageUri);
                                                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            Log.d("ImageDownloadUrl", "Download URL: " + imageUrl);
                                                            photoRepository = new PhotoRepositoryImpl();
                                                            Photo photo = new Photo();
                                                            photo.setPhotoId(generateRandomUniqueInteger());
                                                            photo.setPhotoUrl(imageUrl);
                                                            //photo.setTouristAttraction(addedAttraction);
                                                            photoRepository.addPhoto(photo, photoTask -> {
                                                                if (photoTask.isSuccessful()) {
                                                                    addedPhotos.add(photo);
                                                                    Log.d("CreatePlace", "Photo added successfully: " + photo);
                                                                } else {
                                                                    Log.e("CreatePlace", "Failed to add photo: " + photoTask.getException());
                                                                }
                                                            });
                                                        }).addOnFailureListener(e -> {
                                                            Log.e("ImageDownloadUrl", "Failed to get download URL: " + e.getMessage());
                                                        });
                                                    });
                                        }
                                        //Update the attraction with the added photos
                                        addedAttraction.setPhotos(addedPhotos);
                                        touristAttractionRepository.updateTouristAttraction(attraction_id, addedAttraction, updateTask -> {
                                            if (updateTask.isSuccessful()) {
                                                Log.d("CreatePlace", "Attraction updated successfully: " + addedAttraction);
                                            } else {
                                                Log.e("CreatePlace", "Failed to update attraction: " + updateTask.getException());
                                            }
                                        });
                                    }
                                } else {
                                    Log.w("CreatePlace", "Error getting document: ", documentTask.getException());
                                }
                            });
                            Log.d(TAG, "Attraction ID: " + attraction_id);
                        }
                    }
                });
            }
            else {
                Log.d("CreatePlace", "Marker Position is null");
            }
        });
    }

    private void getUserById(int userId) {
        userRepository.getUserById(userId, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "User: " + task.getResult().getData());
            }
        });
    }

    private void pickMultipleImages() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Action");
        builder.setItems(new CharSequence[]{"Take Photo", "Pick from Gallery"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
                    }
                    break;
                case 1:
                    Intent pickPhotoIntent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhotoIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(pickPhotoIntent, REQUEST_CODE_PICK_IMAGES);
                    break;
            }
        });
        builder.show();
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_PICK_IMAGES) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        selectedImages.add(imageUri.toString());
                    }
                } else if (data.getData() != null) {
                    Uri imageUri = data.getData();
                    selectedImages.add(imageUri.toString());
                }
            } else if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
                Uri imageUri = data.getData();
                selectedImages.add(imageUri.toString());
            }
            imageAdapter.notifyDataSetChanged();
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private final int spanCount;
        private final int spacing;
        private final boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}