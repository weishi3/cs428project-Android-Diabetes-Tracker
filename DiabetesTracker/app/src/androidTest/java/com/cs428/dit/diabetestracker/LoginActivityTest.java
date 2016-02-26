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

// Tests for LoginActivity
public class LoginActivityTest {
    private String mCorrectEmail1;

    @Before
    public void setUp() {
        mCorrectEmail1 = "qizhang4@illinois.edu";
        String mCorrectPassword1 = "123456";
    }

    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void signInButtonShownTest() {
       onView(withId(R.id.email_sign_in_button))
               .check(matches(isDisplayed()));

    }

    @Test
    public void registerButtonShownTest() {
        onView(withId(R.id.registerBtn))
                .check(matches(isDisplayed()));
    }

    @Test
    public void invalidEmailTest1() {
        onView(withId(R.id.email))
                .perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button))
                .perform(click());
        String errorText = activityTestRule.getActivity().getResources().getString(R.string.error_invalid_email);
        onView(withId(R.id.email))
                .check(matches(ErrorMatcher.withError(errorText)));
    }

    @Test
    public void invalidEmailTest2() {
        onView(withId(R.id.email))
                .perform(typeText("qi@"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button))
                .perform(click());
        String errorText = activityTestRule.getActivity().getResources().getString(R.string.error_invalid_email);
        onView(withId(R.id.email))
                .check(matches(ErrorMatcher.withError(errorText)));
    }

    @Test
    public void shortPasswordTest1() {
        onView(withId(R.id.email))
                .perform(typeText(mCorrectEmail1), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button))
                .perform(click());
        String errorText = activityTestRule.getActivity().getResources().getString(R.string.error_invalid_password);
        onView(withId(R.id.password))
                .check(matches(ErrorMatcher.withError(errorText)));
    }
}