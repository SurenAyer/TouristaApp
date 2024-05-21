package com.example.touristaapp.repositories;

import com.example.touristaapp.models.Photo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public interface PhotoRepository {
    void addPhoto(Photo photo, OnCompleteListener<DocumentReference> onCompleteListener);
    void updatePhoto(int photoId, Photo photo, OnCompleteListener<Void> onCompleteListener);
    void deletePhoto(int photoId, OnCompleteListener<Void> onCompleteListener);
    void getAllPhotos(OnCompleteListener<QuerySnapshot> onCompleteListener);
    void getPhotoById(int photoId, OnCompleteListener<DocumentSnapshot> onCompleteListener);
}
