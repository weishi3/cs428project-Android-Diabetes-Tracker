package com.cs428.dit.diabetestracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

// Tests for MainActivity
public class MainActivityTest {
    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

//    @Test
//    public void logoutButtonShownTest() {
//        onView(withId(R.id.logoutBtn))
//                .check(matches(isDisplayed()));
//    }
}