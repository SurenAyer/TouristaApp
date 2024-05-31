package com.example.touristaapp.activities;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.touristaapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Rule
    public ActivityScenarioRule<RegisterActivity> activityRule =
            new ActivityScenarioRule<>(RegisterActivity.class);
    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }
    @Test
    public void testSignUp() {
        // Enter first name
        Espresso.onView(ViewMatchers.withId(R.id.firstName))
                .perform(ViewActions.typeText("New"), ViewActions.closeSoftKeyboard());

        // Enter last name
        Espresso.onView(ViewMatchers.withId(R.id.lastName))
                .perform(ViewActions.typeText("User"), ViewActions.closeSoftKeyboard());

        // Enter email
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("newuser2@gmail.com"), ViewActions.closeSoftKeyboard());

        // Enter phone
        Espresso.onView(ViewMatchers.withId(R.id.phone))
                .perform(ViewActions.typeText("12345678"), ViewActions.closeSoftKeyboard());

        // Enter password
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("newuser123"), ViewActions.closeSoftKeyboard());

        // Click on the sign up button
        Espresso.onView(ViewMatchers.withId(R.id.signupBtn)).perform(ViewActions.click());
    }

}