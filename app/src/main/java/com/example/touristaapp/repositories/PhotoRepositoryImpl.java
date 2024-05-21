package com.example.touristaapp.repositories;

import android.util.Log;

import com.example.touristaapp.models.Photo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class PhotoRepositoryImpl implements PhotoRepository {
    private static final String TAG = "PhotoRepository";
    private final FirebaseFirestore db;
    private final CollectionReference photosRef;

    public PhotoRepositoryImpl() {
        db = FirebaseFirestore.getInstance();
        photosRef = db.collection("photos");
    }

    @Override
    public void addPhoto(Photo photo, OnCompleteListener<DocumentReference> onCompleteListener) {
        photosRef
                .add(photo)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentReference docRef = task.getResult();
                        if (docRef != null) {
                            String docId = docRef.getId();
                            photo.setPhotoId(docId);
                            photosRef.document(docId).set(photo);
                        }
                    }
                    onCompleteListener.onComplete(task);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to add photo", e);
                    onCompleteListener.onComplete(null);
                });
    }

    @Override
    public void updatePhoto(int photoId, Photo photo, OnCompleteListener<Void> onCompleteListener) {

    }

    @Override
    public void deletePhoto(int photoId, OnCompleteListener<Void> onCompleteListener) {

    }

    @Override
    public void getAllPhotos(OnCompleteListener<QuerySnapshot> onCompleteListener) {

    }

    @Override
    public void getPhotoById(int photoId, OnCompleteListener<DocumentSnapshot> onCompleteListener) {

    }
}
