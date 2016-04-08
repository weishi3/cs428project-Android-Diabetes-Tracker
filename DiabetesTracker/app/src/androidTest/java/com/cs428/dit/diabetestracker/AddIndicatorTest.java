package com.cs428.dit.diabetestracker;


import android.support.test.rule.ActivityTestRule;

import com.firebase.client.Firebase;

import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
/**
 * Created by water on 3/18/16.
 */
public class AddIndicatorTest {


    @Rule
    public ActivityTestRule<AddIndicatorActivity> AddIndicatorActivityTestRule =
            new ActivityTestRule<>(AddIndicatorActivity.class);
    String fbURL = "https://brilliant-fire-9755.firebaseio.com/userstats/test@test!com";

    @Test
    public void addIndicatorActivityTest1(){
        Firebase ref = new Firebase(fbURL);
        onView(withId(R.id.weightTextfield))
                .perform(typeText("120"), closeSoftKeyboard());
        onView(withId(R.id.bloodPressureTextfield))
                .perform(typeText("70"), closeSoftKeyboard());
        onView(withId(R.id.bloodSugarTextfield))
                .perform(typeText("20"), closeSoftKeyboard());
        onView(withId(R.id.btn_save_indicator))
                .perform(click());
        onView(withText("120.0 kg"))
                .check(matches(isDisplayed()));
        onView(withText("70.0 mmHg"))
                .check(matches(isDisplayed()));
        onView(withText("20.0 mmol/L"))
                .check(matches(isDisplayed()));

        //test for date
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        final String day = dateformat.format(new Date());
        onView(withText(day))
                .check(matches(isDisplayed()));
        onView(withText("1000.0 kg"))
                .check(doesNotExist());

        ref.removeValue();
    }

    //input invalid value then replace
    @Test
    public void addIndicatorActivityTest2(){
        Firebase ref = new Firebase(fbURL);
        onView(withId(R.id.weightTextfield))
                .perform(typeText("1000"), closeSoftKeyboard());
        onView(withId(R.id.bloodPressureTextfield))
                .perform(typeText("80"), closeSoftKeyboard());
        onView(withId(R.id.bloodSugarTextfield))
                .perform(typeText("30"), closeSoftKeyboard());
        onView(withId(R.id.btn_save_indicator))
                .perform(click());
        //invalid weight
        onView(withText("1000.0 kg"))
                .check(doesNotExist());
        onView(withText("80.0 mmHg"))
                .check(doesNotExist());

        //fix it
        onView(withId(R.id.weightTextfield))
                .perform(typeText("200"), closeSoftKeyboard());
        //but invalid blood sugar
        onView(withId(R.id.bloodSugarTextfield))
                .perform(typeText("100"), closeSoftKeyboard());
        onView(withId(R.id.btn_save_indicator))
                .perform(click());
        onView(withText("100.0 mmol/L"))
                .check(doesNotExist());
        onView(withText("200.0 kg"))
                .check(doesNotExist());

        onView(withId(R.id.bloodSugarTextfield))
                .perform(typeText("30"), closeSoftKeyboard());
        onView(withId(R.id.btn_save_indicator))
                .perform(click());
        onView(withText("30.0 mmol/L"))
                .check(matches(isDisplayed()));
        onView(withText("80.0 mmHg"))
                .check(matches(isDisplayed()));
        onView(withText("200.0 kg"))
                .check(matches(isDisplayed()));






        ref.removeValue();
    }


}
