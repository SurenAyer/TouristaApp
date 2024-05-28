package com.example.touristaapp.activities;

import com.example.touristaapp.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(qualifiers = "port-xxhdpi")
public class RegisterActivityTest {

    @Mock
    RegisterActivity registerActivity;

    @Mock
    FirebaseAuth mockAuth;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        registerActivity = Mockito.spy(RegisterActivity.class);
        mockAuth = Mockito.mock(FirebaseAuth.class);
        registerActivity.mAuth = mockAuth;
        Task<AuthResult> mockedAuthTask = Mockito.mock(Task.class);
        Mockito.when(mockAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mockedAuthTask);
        doNothing().when(registerActivity).setContentView(anyInt());
    }

    @Test
    public void createUserWithEmailAndPassword_isCalled_whenSignUpButtonClicked() {
        registerActivity.performSignUp("anyemail@gmail.com", "anypassword");
        verify(mockAuth).createUserWithEmailAndPassword(anyString(), anyString());
    }
}