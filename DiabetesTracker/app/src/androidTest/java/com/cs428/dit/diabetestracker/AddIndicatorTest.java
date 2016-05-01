package com.cs428.dit.diabetestracker;


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
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by water on 3/18/16.
 */


public class AddIndicatorTest {



    public static final int SLEEPTIME = 2500;
    static String fbURL;
    private static String mTestEmail = "addIndicator@test.com";
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

        fbURL = "https://brilliant-fire-9755.firebaseio.com/userstats/" + "addIndicator@test!com";
    }



    @Test
    public void addIndicatorSuccessfulTest1() {

        Firebase ref = new Firebase(fbURL);

        // Go to add indicator page.
        onView(withId(R.id.button_log_indicator))
                .perform(scrollTo(), click());
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
        }
        // add indicator data
        onView(withId(R.id.weightTextfield))
                .perform(typeText("120"), closeSoftKeyboard());
        onView(withId(R.id.bloodPressureTextfield))
                .perform(typeText("100"), closeSoftKeyboard());
        onView(withId(R.id.bloodSugarTextfield))
                .perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.save_indicator_button))
                .perform(click());
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
        }

        //check data match
        onView(withText("120.0 kg"))
                .check(matches(isDisplayed()));
        onView(withText("100.0 mmHg"))
                .check(matches(isDisplayed()));

        //check time match
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        final String day = dateformat.format(new Date());
        onView(withText(day)).check(ViewAssertions.matches(isDisplayed()));

        ref.removeValue();
    }

    @Test
    public void addIndicatorSuccessfulBoundaryTest() {

        Firebase ref = new Firebase(fbURL);

        // Go to add indicator page.
        onView(withId(R.id.button_log_indicator))
                .perform(scrollTo(), click());
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
        }
        // add indicator data
        onView(withId(R.id.weightTextfield))
                .perform(typeText("625"), closeSoftKeyboard());
        onView(withId(R.id.bloodPressureTextfield))
                .perform(typeText("200"), closeSoftKeyboard());
        onView(withId(R.id.bloodSugarTextfield))
                .perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.save_indicator_button))
                .perform(click());
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
        }

        //check data match
        onView(withText("625.0 kg"))
                .check(matches(isDisplayed()));
        onView(withText("200.0 mmHg"))
                .check(matches(isDisplayed()));
        onView(withText("50.0 mmol/L"))
                .check(matches(isDisplayed()));

        //check time match
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        final String day = dateformat.format(new Date());
        onView(withText(day)).check(ViewAssertions.matches(isDisplayed()));

        ref.removeValue();
    }



    @Test
    public void addIndicatorFailFixTest() {

        Firebase ref = new Firebase(fbURL);

        // Go to add indicator page.
        onView(withId(R.id.button_log_indicator))
                .perform(scrollTo(), click());
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
        }
        // add indicator data
        onView(withId(R.id.weightTextfield))
                .perform(typeText("626"), closeSoftKeyboard());
        onView(withId(R.id.bloodPressureTextfield))
                .perform(typeText("201"), closeSoftKeyboard());
        onView(withId(R.id.bloodSugarTextfield))
                .perform(typeText("51"), closeSoftKeyboard());
        onView(withId(R.id.save_indicator_button))
                .perform(click());
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
        }

        //check data match

        onView(withText("626.0 kg")).check(doesNotExist());

        onView(withText("201.0 mmHg"))
                .check(doesNotExist());
        onView(withText("51.0 mmol/L"))
                .check(doesNotExist());

        //check time match
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        final String day = dateformat.format(new Date());
        onView(withText(day)).check(doesNotExist());


        onView(withHint("World Record is 625kg!"))
                .check(matches(isDisplayed()));


        //now start to fix but still fail
        onView(withId(R.id.weightTextfield))
                .perform(replaceText("624"), closeSoftKeyboard());
        onView(withId(R.id.bloodPressureTextfield))
                .perform(replaceText("200"), closeSoftKeyboard());

        onView(withId(R.id.save_indicator_button))
                .perform(click());
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
        }

        onView(withText("624.0 kg")).check(doesNotExist());

        onView(withText("200.0 mmHg"))
                .check(doesNotExist());

        onView(withHint("Be Honest or Leave Empty!"))
                .check(matches(isDisplayed()));


        //now fix in correct way
        onView(withId(R.id.weightTextfield))
                .perform(replaceText("624"), closeSoftKeyboard());
        onView(withId(R.id.bloodPressureTextfield))
                .perform(replaceText("200"), closeSoftKeyboard());
        onView(withId(R.id.bloodSugarTextfield))
                .perform(replaceText("49"), closeSoftKeyboard());
        onView(withId(R.id.save_indicator_button))
                .perform(click());
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
        }

        onView(withText("624.0 kg"))
                .check(matches(isDisplayed()));
        onView(withText("200.0 mmHg"))
                .check(matches(isDisplayed()));
        onView(withText("49.0 mmol/L"))
                .check(matches(isDisplayed()));




        ref.removeValue();
    }




//
//    @Test
//    public void addIndicatorActivityTest1(){
//        Firebase ref = new Firebase(fbURL);
//        onView(withId(R.id.weightTextfield))
//                .perform(typeText("120"), closeSoftKeyboard());
//        onView(withId(R.id.bloodPressureTextfield))
//                .perform(typeText("70"), closeSoftKeyboard());
//        onView(withId(R.id.bloodSugarTextfield))
//                .perform(typeText("20"), closeSoftKeyboard());
//        onView(withId(R.id.save_indicator_button))
//                .perform(click());
//
//        onView(withText("120.0 kg"))
//                .check(matches(isDisplayed()));
//        onView(withText("70.0 mmHg"))
//                .check(matches(isDisplayed()));
//
//        //test for date
//        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
//        final String day = dateformat.format(new Date());
//        onView(withText(day)).check(ViewAssertions.matches(isDisplayed()));
//
//        onView(withText("1000.0 kg")).check(doesNotExist());
//
//        ref.removeValue();
//
//    }
//
//    //input invalid value then replace
//    @Test
//    public void addIndicatorActivityTest2(){
//        Firebase ref = new Firebase(fbURL);
//        onView(withId(R.id.weightTextfield))
//                .perform(typeText("1000"), closeSoftKeyboard());
//        onView(withId(R.id.bloodPressureTextfield))
//                .perform(typeText("90"), closeSoftKeyboard());
//        onView(withId(R.id.bloodSugarTextfield))
//                .perform(typeText("40"), closeSoftKeyboard());
//        onView(withId(R.id.save_indicator_button))
//                .perform(click());
//        //invalid weight
//        onView(withText("1000.0 kg"))
//                .check(doesNotExist());
//        onView(withText("90.0 mmHg"))
//                .check(doesNotExist());
//
//        //fix it
//        onView(withId(R.id.weightTextfield))
//                .perform(typeText("210"), closeSoftKeyboard());
//        //but invalid blood sugar
//        onView(withId(R.id.bloodSugarTextfield))
//                .perform(replaceText("100"), closeSoftKeyboard());
//        onView(withId(R.id.save_indicator_button))
//                .perform(click());
//        onView(withText("100.0 mmol/L"))
//                .check(doesNotExist());
//        onView(withText("210.0 kg"))
//                .check(doesNotExist());
//
//        onView(withId(R.id.bloodSugarTextfield))
//                .perform(replaceText("40"), closeSoftKeyboard());
//        onView(withId(R.id.save_indicator_button))
//                .perform(click());
//        onView(withText("40.0 mmol/L"))
//                .check(matches(isDisplayed()));
//        onView(withText("90.0 mmHg"))
//                .check(matches(isDisplayed()));
//        onView(withText("210.0 kg"))
//                .check(matches(isDisplayed()));
//
//
//
//
//
//
//        ref.removeValue();
//    }


}
