package com.cs428.dit.diabetestracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.firebase.client.Firebase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by water on 4/27/16.
 */
public class DiagnosisAdvTest {


    public static final int SLEEPTIME = 2500;
    static String fbURL;
    private static String mTestEmail = "test@testtest.com";
    private static String mPassword = "testtest";
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





        // Go to add indicator page.
        onView(withId(R.id.diagnosis_preview))
                .perform(scrollTo(), click());
        try {
            Thread.sleep(SLEEPTIME);
        } catch (InterruptedException e) {
        }

        onView(withText("Diagnosis"))
                .check(matches(isDisplayed()));
        onView(withText("N/A Before Running Diagnosis!"))
                .check(matches(isDisplayed()));
        onView(withText("Generally Suggested Diets"))
                .check(matches(isDisplayed()));


        onView(withId(R.id.button))
                .perform(click(), closeSoftKeyboard());

        onView(withText("The score is: 6"))
                .check(matches(isDisplayed()));
        onView(withText("You looks perfectly fine based on your optional description in profile!"))
                .check(matches(isDisplayed()));
        onView(withText("Based on your profile, It looks you are far from getting diabetes! Though a good lifestyle is important and MEDIUM-GI and LOW-GI food is helpful in the long run."))
                .check(matches(isDisplayed()));


    }
}
