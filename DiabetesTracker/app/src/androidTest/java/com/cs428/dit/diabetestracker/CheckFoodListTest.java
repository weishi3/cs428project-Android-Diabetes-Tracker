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
public class CheckFoodListTest {
    @Rule
    public ActivityTestRule<CheckSavedDietActivity> activityTestRule =
            new ActivityTestRule<>(CheckSavedDietActivity.class);

    /*
     * test if check saved food list page shows up
     */
    @Test
    public void SaveFoodListPageTest() {
        onView(withId(R.id.lvExp))
                .check(matches(isDisplayed()));
    }

}
