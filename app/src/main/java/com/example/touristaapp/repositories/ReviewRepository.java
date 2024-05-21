package com.example.touristaapp.repositories;

import com.example.touristaapp.models.Review;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public interface ReviewRepository {
    void addReview(Review review, OnCompleteListener<DocumentReference> onCompleteListener);
    void updateReview(String reviewId, Review review, OnCompleteListener<Void> onCompleteListener);
    void deleteReview(String reviewId, OnCompleteListener<Void> onCompleteListener);
    void getAllReviews(OnCompleteListener<QuerySnapshot> onCompleteListener);
    void getReviewById(String reviewId, OnCompleteListener<DocumentSnapshot> onCompleteListener);
}
