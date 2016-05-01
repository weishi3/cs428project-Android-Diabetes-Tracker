package com.cs428.dit.diabetestracker;

/**
 * Created by water on 4/19/16.
 */

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;

import com.firebase.client.Firebase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
public class monitorSettingTest {

    public static final int SLEEPTIME = 2500;
    static String fbURL;
    private static String mTestEmail = "monitorsetting@test.com";
    private static String mPassword = "123456";
    private static boolean signedInWithTestAccount = false;
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);



    @BeforeClass
    public static void setUpOnce() {
        signedInWithTestAccount = false;
    }


    @Before
    public void initialize() {
        // Log in with test account
        if (!signedInWithTestAccount) {
            try {
                openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

                onView(withText(InstrumentationRegistry.getTargetContext().getString(R.string.action_logout)))
                        .perform(click());
            } catch (Exception e) {
                // Do nothing, not logged in
            }
            try {
                Thread.sleep(SLEEPTIME);
            } catch (InterruptedException e) {
            }

            onView(withId(R.id.email))
                    .perform(typeText(mTestEmail));
            onView(withId(R.id.password))
                    .perform(typeText(mPassword));
            onView(withId(R.id.email_sign_in_button))
                    .perform(click());
            try {
                Thread.sleep(SLEEPTIME);
            } catch (InterruptedException e) {
            }
            signedInWithTestAccount = true;
        }


    }

    @Test
    public void monitorTest1(){

        onView(withText("mmol/L"))
                .check(matches(isDisplayed()));
       // go the setting page button_monitor_plan

        onView(withId(R.id.button_monitor_plan))
                .perform(scrollTo(), click());
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
        }

        //we could see initial settings(default)


        onView(withId(R.id.select_weight))
                .check(matches(isNotChecked()));
        onView(withId(R.id.select_blood_pressure))
                .check(matches(isNotChecked()));
        onView(withId(R.id.select_blood_sugar))
                .check(matches(isChecked()));

        onView(withId(R.id.blood_pressure_check_box))
                .check(matches(isNotChecked()));
        onView(withId(R.id.blood_sugar_check_box))
                .check(matches(isChecked()));
        onView(withText("3"))
                .check(matches(isDisplayed()));


        onView(withId(R.id.select_weight))
                .perform(click(), closeSoftKeyboard());

        onView(withId(R.id.blood_pressure_check_box))
                .perform(click(), closeSoftKeyboard());
        onView(withId(R.id.period))
                .perform(replaceText("1"), closeSoftKeyboard());
        onView(withId(R.id.submitSetting))
                .perform(click());

        //find reminder
        onView(withText("A KIND REMINDER"))
                .check(matches(isDisplayed()));
        onView(withText("Your Blood Sugar Stats and Blood Pressure Stats may indicate that you are in a bad health condition!"))
                .check(matches(isDisplayed()));
        onView(withText("A KIND REMINDER")).perform(pressBack());
        onView(withText("kg"))
                .check(matches(isDisplayed()));

        //set one more cases:uncheck monitor pressure
        onView(withId(R.id.button_monitor_plan))
                .perform(scrollTo(), click());
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
        }

        //display updated settings
        onView(withId(R.id.select_weight))
                .check(matches(isChecked()));
        onView(withId(R.id.select_blood_pressure))
                .check(matches(isNotChecked()));
        onView(withId(R.id.select_blood_sugar))
                .check(matches(isNotChecked()));

        onView(withId(R.id.blood_pressure_check_box))
                .check(matches(isChecked()));
        onView(withId(R.id.blood_sugar_check_box))
                .check(matches(isChecked()));
        onView(withText("1"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.blood_pressure_check_box))
                .perform(click(), closeSoftKeyboard());

        onView(withId(R.id.submitSetting))
                .perform(click());

        //find reminder
        onView(withText("A KIND REMINDER"))
                .check(matches(isDisplayed()));
        onView(withText("Your Blood Sugar Stats may indicate that you are in a bad health condition!"))
                .check(matches(isDisplayed()));
        onView(withText("A KIND REMINDER")).perform(pressBack());


        //set back after testing

        onView(withId(R.id.button_monitor_plan))
                .perform(scrollTo(), click());
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.select_blood_sugar))
                .perform(click(), closeSoftKeyboard());

        onView(withId(R.id.period))
                .perform(replaceText("3"), closeSoftKeyboard());
        onView(withId(R.id.submitSetting))
                .perform(click());






    }
}
