package com.cs428.dit.diabetestracker;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.Firebase;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
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
    @Rule
    public ActivityTestRule<AddFoodItemActivity> addFoodItemActivityActivityTestRule =
            new ActivityTestRule<>(AddFoodItemActivity.class);

    private SessionManager sessionManager;
    String fbURL;
    @BeforeClass
    public void initialize(){
        sessionManager = new SessionManager(addFoodItemActivityActivityTestRule.getActivity().getBaseContext());
        fbURL = "https://brilliant-fire-9755.firebaseio.com/foodStats/"+(sessionManager.getUserDetails().get(SessionManager.KEY_EMAIL)+"").replace('.','!');
    }
//    String fbUrl
//    String fbURL = "https://brilliant-fire-9755.firebaseio.com/foodStats/test1@test!com";

    @Test
    public void addFoodItemSuccessfulTest1(){
        Log.d("CURRENT_USER: ", sessionManager.getUserDetails().get(SessionManager.KEY_EMAIL)+"");
        Log.d("EMAIL",fbURL);
        Date date = new Date();
        Firebase ref = new Firebase(fbURL);
        onView(withId(R.id.foodNameTextfield))
                .perform(typeText("test_food"+date), closeSoftKeyboard());
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
        onView(withText("test_food"+date))
                .check(matches(isDisplayed()));

    }

    @Test
    public void addFoodItemSuccessfulTest2(){
        Firebase ref = new Firebase(fbURL);
        Date date = new Date();
        onView(withId(R.id.foodNameTextfield))
                .perform(typeText("test_food2"+date), closeSoftKeyboard());
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
        onView(withText("test_food2"+date))
                .check(matches(isDisplayed()));
        // ref.removeValue();
    }


}
