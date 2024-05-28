package com.example.touristaapp.activities;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;

import com.example.touristaapp.models.Event;
import com.example.touristaapp.models.TouristAttraction;
import com.example.touristaapp.models.User;
import com.example.touristaapp.repositories.EventRepository;
import com.example.touristaapp.repositories.TouristAttractionRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.DocumentReference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(qualifiers = "port-xxhdpi")
public class CreateEventActivityTest {

    @Mock
    CreateEventActivity createEventActivity;

    @Mock
    private Task<Void> mockTask;

    EventRepository mockEventRepository;
    TouristAttractionRepository mockTouristAttractionRepository;
    Event mockEvent;
    TouristAttraction mockTouristAttraction;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        createEventActivity = Mockito.spy(CreateEventActivity.class);
        createEventActivity.progressDialog = Mockito.mock(android.app.ProgressDialog.class);
        createEventActivity.gson = Mockito.mock(com.google.gson.Gson.class);

        // Initialize the mock repositories
        mockEventRepository = Mockito.mock(EventRepository.class);
        mockTouristAttractionRepository = Mockito.mock(TouristAttractionRepository.class);

        createEventActivity.touristAttractionRepository=mockTouristAttractionRepository;
        createEventActivity.eventRepository=mockEventRepository;

        mockEvent = Mockito.mock(Event.class);
        mockTouristAttraction = Mockito.mock(TouristAttraction.class);
        createEventActivity.touristAttraction = mockTouristAttraction;

        Mockito.doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                TaskCompletionSource<DocumentReference> taskCompletionSource = new TaskCompletionSource<>();
                taskCompletionSource.setResult(Mockito.mock(DocumentReference.class));
                Task<DocumentReference> mockedTask = taskCompletionSource.getTask();
                ((OnCompleteListener<DocumentReference>) args[1]).onComplete(mockedTask);
                return null;
            }
        }).when(mockEventRepository).addEvent(ArgumentMatchers.any(Event.class), ArgumentMatchers.any(OnCompleteListener.class));

        Mockito.doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                String attractionId = (String) args[0];
                TouristAttraction attraction = (TouristAttraction) args[1];
                TaskCompletionSource<Void> taskCompletionSource = new TaskCompletionSource<>();
                taskCompletionSource.setResult(null);
                Task<Void> mockedTask = taskCompletionSource.getTask();
                ((OnCompleteListener<Void>) args[2]).onComplete(mockedTask);
                return null;
            }
        }).when(mockTouristAttractionRepository).updateTouristAttraction(anyString(), ArgumentMatchers.any(TouristAttraction.class), ArgumentMatchers.any());
    }

    @Test
    public void testSaveEvent() {

        Mockito.when(mockTouristAttraction.getAttractionId()).thenReturn("123");
        Mockito.doNothing().when(mockTouristAttraction).setUser(any(User.class));
        Mockito.when(mockTask.isSuccessful()).thenReturn(true);

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ViewPlaceActivity.class);
        // Mock startActivity
        doNothing().when(createEventActivity).startActivity(any(Intent.class));
        // Mock startActivity
        doNothing().when(createEventActivity).startActivity(any(Intent.class));
        createEventActivity.saveEvent(mockEvent, intent);

        Mockito.verify(mockTouristAttractionRepository).updateTouristAttraction(ArgumentMatchers.anyString(), ArgumentMatchers.any(TouristAttraction.class), ArgumentMatchers.any());
        // Verify that startActivity was called with the correct Intent
        Mockito.verify(createEventActivity).startActivity(intent);
    }

}