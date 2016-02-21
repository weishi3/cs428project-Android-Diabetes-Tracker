package com.cs428.dit.diabetestracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


// Tests for MainActivity
public class LoginActivityTest {
    private String mCorrectEmail1;
    private String mCorrectPassword1;

    @Before
    public void initTest() {
        mCorrectEmail1 = "qizhang4@illinois.edu";
        mCorrectPassword1 = "123456";
    }

    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void validateEditText() {
        onView(withId(R.id.email)).perform(typeText("qizhang45"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.email_sign_in_button)).check(matches(isDisplayed()));

    }
}