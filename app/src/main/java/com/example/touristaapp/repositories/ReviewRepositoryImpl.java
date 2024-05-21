package com.example.touristaapp.repositories;

import com.example.touristaapp.models.Review;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class ReviewRepositoryImpl implements ReviewRepository{
    private static final String TAG = "ReviewRepository";
    private final FirebaseFirestore db;
    private final CollectionReference reviewsRef;

    public ReviewRepositoryImpl() {
        db = FirebaseFirestore.getInstance();
        reviewsRef = db.collection("reviews");
    }
    @Override
    public void addReview(Review review, OnCompleteListener<DocumentReference> onCompleteListener) {
        reviewsRef
                .add(review)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentReference docRef = task.getResult();
                        if (docRef != null) {
                            String docId = docRef.getId();
                            review.setReviewId(docId);
                            reviewsRef.document(docId).set(review);
                        }
                    }
                    onCompleteListener.onComplete(task);
                })
                .addOnFailureListener(e -> {
                    onCompleteListener.onComplete(null);
                });
    }

    @Override
    public void updateReview(String reviewId, Review review, OnCompleteListener<Void> onCompleteListener) {

    }

    @Override
    public void deleteReview(String reviewId, OnCompleteListener<Void> onCompleteListener) {

    }

    @Override
    public void getAllReviews(OnCompleteListener<QuerySnapshot> onCompleteListener) {

    }

    @Override
    public void getReviewById(String reviewId, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
    reviewsRef
            .document(String.valueOf(reviewId))
            .get()
            .addOnCompleteListener(onCompleteListener)
            .addOnFailureListener(e -> {
                onCompleteListener.onComplete(null);
            });
    }
}
