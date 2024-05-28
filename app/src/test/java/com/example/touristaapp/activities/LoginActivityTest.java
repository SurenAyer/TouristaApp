package com.example.touristaapp.activities;

import android.app.Application;
import android.content.Intent;
import android.os.Build;

import com.example.touristaapp.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(qualifiers = "port-xxhdpi")
public class LoginActivityTest {

    @Mock
    LoginActivity loginActivity;

    @Mock
    FirebaseAuth mockAuth;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        loginActivity = Mockito.spy(LoginActivity.class);
        mockAuth = Mockito.mock(FirebaseAuth.class);
        loginActivity.mAuth = mockAuth;
        Task<AuthResult> mockedAuthTask = Mockito.mock(Task.class);
        Mockito.when(mockAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(mockedAuthTask);
        doNothing().when(loginActivity).setContentView(anyInt());
    }

    @Test
    public void signInWithEmailAndPassword_isCalled_whenLoginButtonClicked() {
        loginActivity.performSignIn("anyemail@gmail.com", "anypassword");
        verify(mockAuth).signInWithEmailAndPassword(anyString(), anyString());
    }
}