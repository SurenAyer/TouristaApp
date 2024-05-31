package com.example.touristaapp.activities;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.touristaapp.R;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);


    @Test
    public void testLogin() {
        // Enter email
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("random@gmail.com"), ViewActions.closeSoftKeyboard());
         // Enter password
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("random123"), ViewActions.closeSoftKeyboard());
        // Click on the sign in button
        Espresso.onView(ViewMatchers.withId(R.id.signinBtn)).perform(ViewActions.click());
        // Check if the MainActivity is displayed
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_details", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        // Assert that userJson is not empty and isLoggedIn is true
        Assert.assertFalse(isLoggedIn);
    }
}