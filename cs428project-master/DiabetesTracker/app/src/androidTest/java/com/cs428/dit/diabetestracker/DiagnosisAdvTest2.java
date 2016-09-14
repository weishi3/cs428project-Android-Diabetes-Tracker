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
 * Created by water on 5/1/16.
 */
public class DiagnosisAdvTest2 {
    public static final int SLEEPTIME = 2500;
    static String fbURL;
    private static String mTestEmail = "test@test.com";
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
    public void diagnosisADV2(){

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

        onView(withText("The score is: 44"))
                .check(matches(isDisplayed()));
        onView(withText("You would probably need to consult a doctor for more detailed information with following points."))
                .check(matches(isDisplayed()));
        onView(withText("Your health condition STATS in your profile indicates that You have pretty high risk of getting diabetes. LOW_GI food is your good friend, take body examination regularly and consult a doctor."))
                .check(matches(isDisplayed()));


        onView(allOf(withText("You would probably need to consult a doctor for more detailed information with following points."))).perform(click());
        onView(withText("Your lifestyle can be classified as sedentary, do more exercise!"))
                .check(matches(isDisplayed()));
        onView(withText("Giant infant symptom means you have higher risk of getting diabetes than average people in the future."))
                .check(matches(isDisplayed()));

        onView(withText("Your lifestyle can be classified as sedentary, do more exercise!"))
                .check(matches(isDisplayed()));

        onView(withText("Chronic cerebrovascular disease is a notable killer, especially for middle-aged and elder people with diabetes."))
                .check(matches(isDisplayed()));

        onView(withText("Since you are elder than 40, you've got a higher risk of diabetes and cardiovascular disease. Take physical examination more often!"))
                .check(matches(isDisplayed()));



    }


}
