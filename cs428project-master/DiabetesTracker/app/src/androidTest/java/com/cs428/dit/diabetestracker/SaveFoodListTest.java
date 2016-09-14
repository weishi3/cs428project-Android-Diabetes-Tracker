package com.cs428.dit.diabetestracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;


public class SaveFoodListTest {
    @Rule
    public ActivityTestRule<SaveLowGIDietActivity> activityTestRule =
            new ActivityTestRule<>(SaveLowGIDietActivity.class);

    /**
     * Checks if the header and food items are displayed
     */
    @Test
    public void SaveFoodListPageTest() {
        onView(withId(R.id.topInfo))
                .check(matches(isDisplayed()));
        onView(withId(R.id.attributes))
                .check(matches(isDisplayed()));
    }

    /**
     * Checks if the food radio buttons are initially checked as selected
     */
    @Test
    public void checkButtonTest() {
        onView(withId(R.id.riceNoodlesTrue))
                .check(matches(isChecked()));

        onView(withId(R.id.lentilsFalse))
                .check(matches(isNotChecked()));

        onView(withId(R.id.lentilsFalse))
                .perform(click());

        onView(withId(R.id.lentilsFalse))
                .check(matches(isChecked()));

    }

}
