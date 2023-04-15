package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import android.util.Log;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    private View decorView;

    @Before
    public void loadDecorView() {
        activityRule.getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());
    }

   @Test
public void testCorrectCredentials() {
    Log.i("TEST", "Starting testCorrectCredentials()");
    Espresso.onView(withId(R.id.login_email)).perform(ViewActions.typeText("correct@example.com"));
    Log.i("TEST", "Typed email");
    Espresso.pressBack();
    onView(withId(R.id.login_password)).perform(ViewActions.typeText("password"));
    Log.i("TEST", "Typed password");
    Espresso.pressBack();
    onView(withId(R.id.login_button)).perform(ViewActions.click());
    Log.i("TEST", "Clicked login button");

    // Check if user info is saved to shared preferences
    try {
        Thread.sleep(10000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    

    // Assert that the HomeActivity is started
    ActivityScenario<HomeActivity> scenario = ActivityScenario.launch(HomeActivity.class);
    scenario.onActivity(activity -> {
        Log.i("TEST", "Started HomeActivity");
        // Assert that the user information is saved to SharedPreferences
        SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(activity);
        assertEquals("correct", preferences.getString("family_name", ""));
        assertEquals("example", preferences.getString("first_name", ""));
        assertEquals("correct@example.com", preferences.getString("email", ""));
        assertEquals(30, preferences.getInt("age", 0));
        assertEquals("123 Main St", preferences.getString("address", ""));
    });
}


    @Test
    public void testIncorrectCredentials() {
        onView(withId(R.id.login_email)).perform(ViewActions.typeText("incorrect@example.com"));
        Espresso.pressBack();
        onView(withId(R.id.login_password)).perform(ViewActions.typeText("another_password"));

        Espresso.pressBack();
        onView(withId(R.id.login_button)).perform(ViewActions.click());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Check if error message is displayed after unsuccessful login
        activityRule.getScenario().onActivity(activity -> {
            assertEquals("error", activity.getLastStatus());
        });
    }

}

