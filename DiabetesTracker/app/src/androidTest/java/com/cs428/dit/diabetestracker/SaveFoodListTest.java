package com.cs428.dit.diabetestracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by liyiyu1 on 4/26/16.
 */
public class SaveFoodListTest {
    @Rule
    public ActivityTestRule<SaveLowGIDietActivity> activityTestRule =
            new ActivityTestRule<>(SaveLowGIDietActivity.class);

    /*
     * test if save food list page shows up
     */
    @Test
    public void SaveFoodListPageTest() {
        onView(withId(R.id.topInfo))
                .check(matches(isDisplayed()));
        onView(withId(R.id.attributes))
                .check(matches(isDisplayed()));
        onView(withId(R.id.lentilsTrue))
                .check(matches(isDisplayed()));
        onView(withId(R.id.lentilsFalse))
                .check(matches(isDisplayed()));
    }

}
