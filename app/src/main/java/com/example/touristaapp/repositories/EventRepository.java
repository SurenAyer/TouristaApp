package com.example.touristaapp.repositories;

import com.example.touristaapp.models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public interface EventRepository {
    void addEvent(Event event, OnCompleteListener<DocumentReference> onCompleteListener);
    void updateEvent(String eventId, Event event, OnCompleteListener<Void> onCompleteListener);
    void deleteEvent(String eventId, OnCompleteListener<Void> onCompleteListener);
    void getAllEvents(OnCompleteListener<QuerySnapshot> onCompleteListener);
    void getEventById(String eventId, OnCompleteListener<DocumentSnapshot> onCompleteListener);
}
