package com.example.touristaapp.repositories;

import com.example.touristaapp.models.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class EventRepositoryImpl implements EventRepository{
    private static final String TAG = "EventRepository";
    private final FirebaseFirestore db;
    private final CollectionReference eventRef;

    public EventRepositoryImpl() {
        db = FirebaseFirestore.getInstance();
        eventRef = db.collection("events");
    }
    @Override
    public void addEvent(Event event, OnCompleteListener<DocumentReference> onCompleteListener) {
        eventRef
                .add(event)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentReference docRef = task.getResult();
                        if (docRef != null) {
                            String docId = docRef.getId();
                            event.setEventId(docId);
                            eventRef.document(docId).set(event);
                        }
                    }
                    onCompleteListener.onComplete(task);
                })
                .addOnFailureListener(e -> {
                    onCompleteListener.onComplete(null);
                });
    }

    @Override
    public void updateEvent(String eventId, Event event, OnCompleteListener<Void> onCompleteListener) {

    }

    @Override
    public void deleteEvent(String eventId, OnCompleteListener<Void> onCompleteListener) {

    }

    @Override
    public void getAllEvents(OnCompleteListener<QuerySnapshot> onCompleteListener) {

    }

    @Override
    public void getEventById(String eventId, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
    eventRef
            .document(String.valueOf(eventId))
            .get()
            .addOnCompleteListener(onCompleteListener);
    }
}
