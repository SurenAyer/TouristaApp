package com.example.touristaapp.repositories;

import android.util.Log;

import com.example.touristaapp.models.TouristAttraction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class TouristAttractionRepositoryImpl implements TouristAttractionRepository {
    private static final String TAG = "TouristAttractionRepo";
    private final FirebaseFirestore db;
    private final CollectionReference attractionsRef;

    public TouristAttractionRepositoryImpl() {
        db = FirebaseFirestore.getInstance();
        attractionsRef = db.collection("tourist_attractions");
    }

    @Override
    public void addTouristAttraction(TouristAttraction touristAttraction, OnCompleteListener<DocumentReference> onCompleteListener) {
        attractionsRef
                .add(touristAttraction)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to add tourist attraction", e);
                    onCompleteListener.onComplete(null);
                });
    }

    @Override
    public void updateTouristAttraction(String attractionId, TouristAttraction attraction, OnCompleteListener<Void> onCompleteListener) {

    }

    @Override
    public void deleteTouristAttraction(String attractionId, OnCompleteListener<Void> onCompleteListener) {

    }

    @Override
    public void getAllTouristAttractions(OnCompleteListener<QuerySnapshot> onCompleteListener) {

    }

    @Override
    public void getTouristAttractionById(String attractionId, OnCompleteListener<DocumentSnapshot> onCompleteListener) {

    }
}
