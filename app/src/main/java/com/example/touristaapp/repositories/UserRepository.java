package com.example.touristaapp.repositories;

import com.example.touristaapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public interface UserRepository {
    void addUser(User user, OnCompleteListener<DocumentReference> onCompleteListener);
    void updateUser(String userId, User user, OnCompleteListener<Void> onCompleteListener);
    void deleteUser(String userId, OnCompleteListener<Void> onCompleteListener);
    void getAllUsers(OnCompleteListener<QuerySnapshot> onCompleteListener);
    void getUserById(String userId, OnCompleteListener<DocumentSnapshot> onCompleteListener);
    void getUserByEmail(String email, OnCompleteListener<QuerySnapshot> onCompleteListener);
}
