package com.cs428.dit.diabetestracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class DiagnosisActivityTest {
    @Rule
    public ActivityTestRule<DiagnosisActivity> activityTestRule =
            new ActivityTestRule<>(DiagnosisActivity.class);

    @Test
    public void expandableListTest() {
        onView(withId(R.id.lvExp))
                .check(matches(isDisplayed()));
    }
}
