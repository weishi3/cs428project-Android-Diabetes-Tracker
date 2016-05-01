package com.cs428.dit.diabetestracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by water on 4/27/16.
 */
public class DiagnosisAdvTest {


    @Rule
    public ActivityTestRule<DiagnosisActivity> DiagRule =
            new ActivityTestRule<>(DiagnosisActivity.class);
    String fbURL = "https://brilliant-fire-9755.firebaseio.com/userstats/test@test!com";


    @Test
    public void monitorTest1(){
        onView(withText("Diagnosis"))
                .check(matches(isDisplayed()));
        onView(withText("N/A Before Running Diagnosis!"))
                .check(matches(isDisplayed()));
        onView(withText("Generally Suggested Diets"))
                .check(matches(isDisplayed()));


        onView(withId(R.id.button))
                .perform(click(), closeSoftKeyboard());

        onView(withText("The score is: 16"))
                .check(matches(isDisplayed()));
        onView(withText("We have several helpful information based on your optional description in profile"))
                .check(matches(isDisplayed()));

        onView(allOf(withText("We have several helpful information based on your optional description in profile"))).perform(click());


        onView(withText("Your lifestyle can be classified as sedentary, do more exercise!"))
                .check(matches(isDisplayed()));
    }
}
