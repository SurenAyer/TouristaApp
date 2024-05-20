package com.example.touristaapp.repositories;

import com.example.touristaapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserRepositoryImpl implements UserRepository {
    private static final String TAG = "UserRepositoryImpl";
    private final FirebaseFirestore db;
    private final CollectionReference usersRef;

    public UserRepositoryImpl() {
        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("users");
    }

    @Override
    public void addUser(User user, OnCompleteListener<DocumentReference> onCompleteListener) {
        usersRef
                .add(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentReference docRef = task.getResult();
                        if (docRef != null) {
                            String docId = docRef.getId();
                            user.setUserId(docId);
                            usersRef.document(docId).set(user);
                        }
                    }
                    onCompleteListener.onComplete(task);
                })
                .addOnFailureListener(e -> {
                    onCompleteListener.onComplete(null);
                });
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
        usersRef.document(String.valueOf(userId))
                .get()
                .addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void getUserByEmail(String email, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        usersRef.whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }
}
