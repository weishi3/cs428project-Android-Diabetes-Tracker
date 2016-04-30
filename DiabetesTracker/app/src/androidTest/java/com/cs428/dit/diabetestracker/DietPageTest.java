package com.cs428.dit.diabetestracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class DietPageTest {
    @Rule
    public ActivityTestRule<DietHigh> activityTestRule =
            new ActivityTestRule<>(DietHigh.class);

    @Test
    public void DietPageTest() {
        onView(withId(R.id.lvExp2))
                .check(matches(isDisplayed()));
    }
}
