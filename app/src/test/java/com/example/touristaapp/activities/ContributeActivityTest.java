package com.example.touristaapp.activities;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

import android.content.Intent;
import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;

import com.example.touristaapp.R;
import com.example.touristaapp.models.Photo;
import com.example.touristaapp.models.TouristAttraction;
import com.example.touristaapp.models.User;
import com.example.touristaapp.repositories.PhotoRepository;
import com.example.touristaapp.repositories.TouristAttractionRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@RunWith(RobolectricTestRunner.class)
@Config(qualifiers = "port-xxhdpi")
public class ContributeActivityTest {

    @Mock
    private Task<Void> mockTask;

    @InjectMocks
    private ContributeActivity contributeActivity;

    TouristAttractionRepository mockTouristAttractionRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockTouristAttractionRepository = Mockito.mock(TouristAttractionRepository.class);
        contributeActivity = Mockito.spy(ContributeActivity.class);
        contributeActivity.progressDialog = Mockito.mock(android.app.ProgressDialog.class);
        contributeActivity.gson = Mockito.mock(com.google.gson.Gson.class);
        contributeActivity.touristAttractionRepository=mockTouristAttractionRepository;
        Task<AuthResult> mockedAuthTask = Mockito.mock(Task.class);
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
    public void testUpdateAttraction() {
        TouristAttraction mockTouristAttraction = Mockito.mock(TouristAttraction.class);
        Mockito.when(mockTouristAttraction.getAttractionId()).thenReturn("123");
        Mockito.doNothing().when(mockTouristAttraction).setUser(any(User.class));
        Mockito.when(mockTask.isSuccessful()).thenReturn(true);

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ViewPlaceActivity.class);
        // Mock startActivity
        doNothing().when(contributeActivity).startActivity(any(Intent.class));

        contributeActivity.updateAttraction(mockTouristAttraction, intent);

        Mockito.verify(mockTouristAttractionRepository).updateTouristAttraction(ArgumentMatchers.anyString(), ArgumentMatchers.any(TouristAttraction.class), ArgumentMatchers.any());
        // Verify that startActivity was called with the correct Intent
        Mockito.verify(contributeActivity).startActivity(intent);
    }

}