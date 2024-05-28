package com.example.touristaapp.activities;

import android.app.Application;
import android.content.Intent;
import android.os.Build;

import com.example.touristaapp.R;
import com.example.touristaapp.models.Review;
import com.example.touristaapp.models.TouristAttraction;
import com.example.touristaapp.models.User;
import com.example.touristaapp.repositories.ReviewRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
@Config(qualifiers = "port-xxhdpi")
public class CreateReviewActivityTest {

    @Mock
    CreateReviewActivity createReviewActivity;

    @Mock
    private Task<Void> mockTask;

    ReviewRepository mockReviewRepository;
    TouristAttractionRepository mockTouristAttractionRepository;
    Review mockReview;
    TouristAttraction mockTouristAttraction;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        createReviewActivity = Mockito.spy(CreateReviewActivity.class);
        createReviewActivity.progressDialog = Mockito.mock(android.app.ProgressDialog.class);
        createReviewActivity.gson = Mockito.mock(com.google.gson.Gson.class);

        // Initialize the mock repositories
        mockReviewRepository = Mockito.mock(ReviewRepository.class);
        mockTouristAttractionRepository = Mockito.mock(TouristAttractionRepository.class);

        createReviewActivity.touristAttractionRepository=mockTouristAttractionRepository;
        createReviewActivity.reviewRepository=mockReviewRepository;

        mockReview = Mockito.mock(Review.class);
        mockTouristAttraction = Mockito.mock(TouristAttraction.class);
        createReviewActivity.touristAttraction = mockTouristAttraction;

        Mockito.doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                TaskCompletionSource<DocumentReference> taskCompletionSource = new TaskCompletionSource<>();
                taskCompletionSource.setResult(Mockito.mock(DocumentReference.class));
                Task<DocumentReference> mockedTask = taskCompletionSource.getTask();
                ((OnCompleteListener<DocumentReference>) args[1]).onComplete(mockedTask);
                return null;
            }
        }).when(mockReviewRepository).addReview(ArgumentMatchers.any(Review.class), ArgumentMatchers.any(OnCompleteListener.class));

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
    public void testSaveReview() {

        Mockito.when(mockTouristAttraction.getAttractionId()).thenReturn("123");
        Mockito.doNothing().when(mockTouristAttraction).setUser(any(User.class));
        Mockito.when(mockTask.isSuccessful()).thenReturn(true);

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ViewPlaceActivity.class);
        // Mock startActivity
        doNothing().when(createReviewActivity).startActivity(any(Intent.class));
        // Mock startActivity
        doNothing().when(createReviewActivity).startActivity(any(Intent.class));
        createReviewActivity.saveReview(mockReview, 3.5F,intent);

        Mockito.verify(mockTouristAttractionRepository).updateTouristAttraction(ArgumentMatchers.anyString(), ArgumentMatchers.any(TouristAttraction.class), ArgumentMatchers.any());
        // Verify that startActivity was called with the correct Intent
        Mockito.verify(createReviewActivity).startActivity(intent);
    }

}