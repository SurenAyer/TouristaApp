package com.example.touristaapp.repositories;

import com.example.touristaapp.models.TouristAttraction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public interface TouristAttractionRepository {
    void addTouristAttraction(TouristAttraction touristAttraction, OnCompleteListener<DocumentReference> onCompleteListener);
    void updateTouristAttraction(String attractionId, TouristAttraction attraction, OnCompleteListener<Void> onCompleteListener);
    void deleteTouristAttraction(String attractionId, OnCompleteListener<Void> onCompleteListener);
    void getAllTouristAttractions(OnCompleteListener<QuerySnapshot> onCompleteListener);
    void getTouristAttractionById(String attractionId, OnCompleteListener<DocumentSnapshot> onCompleteListener);
}
