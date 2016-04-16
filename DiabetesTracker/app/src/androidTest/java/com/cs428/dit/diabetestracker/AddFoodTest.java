package com.cs428.dit.diabetestracker;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.firebase.client.Firebase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by QiZhang on 3/16/16.
 * Test with account test1@test.com
 */
public class AddFoodTest {
//    @Rule
//    public ActivityTestRule<AddFoodItemActivity> addFoodItemActivityActivityTestRule =
//            new ActivityTestRule<>(AddFoodItemActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    static String fbURL;
    private static String mTestEmail = "addfood@test.com";
    private static String mPassword = "123456";

    @Before
    public void initialize() {

        try {
            openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
            onView(withText("Logout"))
                    .perform(click());
        } catch (Exception e) {
            //do nothing, not logged in
        }
        onView(withId(R.id.email))
                .perform(typeText(mTestEmail));
        onView(withId(R.id.password))
                .perform(typeText(mPassword));
        onView(withId(R.id.email_sign_in_button))
                .perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        fbURL = "https://brilliant-fire-9755.firebaseio.com/foodStats/" + "addfood@test!com";
        //(sessionManager.getUserDetails().get(SessionManager.KEY_EMAIL)+"").replace('.','!')


    }
//    String fbUrl
//    String fbURL = "https://brilliant-fire-9755.firebaseio.com/foodStats/test1@test!com";

    @Test
    public void addFoodItemSuccessfulTest1() {
        Log.d("EMAIL", fbURL);
        Date date = new Date();
        Firebase ref = new Firebase(fbURL);
        //

        // Go to add food page.
        onView(withId(R.id.button_log_food))
                .perform(scrollTo(), click());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }
        // On Add food item page, add food item
        onView(withId(R.id.foodNameTextfield))
                .perform(typeText("test_food" + date), closeSoftKeyboard());
        onView(withId(R.id.foodCaloriesTextfield))
                .perform(typeText("250"), closeSoftKeyboard());
        onView(withId(R.id.sugarInGramsTextfield))
                .perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.btn_save_food_item))
                .perform(click());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }
        onView(withText("test_food" + date))
                .check(matches(isDisplayed()));
        ref.removeValue();
    }

    @Test
    public void addFoodItemSuccessfulTest2() {
        Log.d("EMAIL", fbURL);
        Date date = new Date();
        Firebase ref = new Firebase(fbURL);
        //

        // Go to add food page.
        onView(withId(R.id.button_log_food))
                .perform(scrollTo(), click());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }
        // On Add food item page, add food item
        onView(withId(R.id.foodNameTextfield))
                .perform(typeText("test_food2" + date), closeSoftKeyboard());
        onView(withId(R.id.foodCaloriesTextfield))
                .perform(typeText("250"), closeSoftKeyboard());
        onView(withId(R.id.sugarInGramsTextfield))
                .perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.btn_save_food_item))
                .perform(click());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }
        onView(withText("test_food2" + date))
                .check(matches(isDisplayed()));
        ref.removeValue();
    }


}
