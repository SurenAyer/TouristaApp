package com.example.touristaapp.repositories;

import com.example.touristaapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserRepositoryImpl implements UserRepository{
    private static final String TAG = "UserRepositoryImpl";
    private final FirebaseFirestore db;
    private final CollectionReference usersRef;

    public UserRepositoryImpl() {
        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("users");
    }

    @Override
    public void addUser(User user, OnCompleteListener<DocumentReference> onCompleteListener) {

    }

    @Override
    public void updateUser(String userId, User user, OnCompleteListener<Void> onCompleteListener) {

    }

    @Override
    public void deleteUser(String userId, OnCompleteListener<Void> onCompleteListener) {

    }

    @Override
    public void getAllUsers(OnCompleteListener<QuerySnapshot> onCompleteListener) {

    }

    @Override
    public void getUserById(String userId, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
        usersRef.document(userId)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }
}
