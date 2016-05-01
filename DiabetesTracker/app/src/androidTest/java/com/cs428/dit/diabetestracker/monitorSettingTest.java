package com.cs428.dit.diabetestracker;

/**
 * Created by water on 4/19/16.
 */

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;

import com.firebase.client.Firebase;

import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
public class monitorSettingTest {

    @Rule
    public ActivityTestRule<SetMonitorPlan> monitorRule =
            new ActivityTestRule<>(SetMonitorPlan.class);
            String fbURL = "https://brilliant-fire-9755.firebaseio.com/userstats/test@test!com";


    @Test
    public void monitorTest1(){
        onView(withId(R.id.select_weight))
                .perform(click(), closeSoftKeyboard());
        onView(withId(R.id.blood_sugar_check_box))
                .perform(click(), closeSoftKeyboard());
        onView(withId(R.id.blood_pressure_check_box))
                .perform(click(), closeSoftKeyboard());
        onView(withId(R.id.period))
                .perform(replaceText("1"), closeSoftKeyboard());
        onView(withId(R.id.submitSetting))
                .perform(click());
        onView(withText("A KIND REMINDER"))
                .check(matches(isDisplayed()));
        onView(withText("Your Blood Sugar Stats may indicate that you are in a bad health condition!"))
                .check(matches(isDisplayed()));
        onView(withText("A KIND REMINDER")).perform(pressBack());
//        onView(withText("210.0"))
//                .check(matches(isDisplayed()));
        onView(withText("kg"))
                .check(matches(isDisplayed()));

    }
}
