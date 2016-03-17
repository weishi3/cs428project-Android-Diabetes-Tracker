package com.cs428.dit.diabetestracker;

import android.support.test.rule.ActivityTestRule;

import com.firebase.client.Firebase;

import org.junit.Rule;
import org.junit.Test;

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
    String fbURL = "https://brilliant-fire-9755.firebaseio.com/foodStats/test1@test!com";

    @Test
    public void addFoodItemSuccessfulTest1(){
        Firebase ref = new Firebase(fbURL);
        onView(withId(R.id.foodNameTextfield))
                .perform(typeText("test_food"), closeSoftKeyboard());
        onView(withId(R.id.foodCaloriesTextfield))
                .perform(typeText("250"), closeSoftKeyboard());
        onView(withId(R.id.sugarInGramsTextfield))
                .perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.btn_save_food_item))
                .perform(click());
        onView(withText("test_food"))
                .check(matches(isDisplayed()));
        ref.removeValue();
    }

    @Test
    public void addFoodItemSuccessfulTest2(){
        Firebase ref = new Firebase(fbURL);
        onView(withId(R.id.foodNameTextfield))
                .perform(typeText("test_food2"), closeSoftKeyboard());
        onView(withId(R.id.foodCaloriesTextfield))
                .perform(typeText("250"), closeSoftKeyboard());
        onView(withId(R.id.sugarInGramsTextfield))
                .perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.btn_save_food_item))
                .perform(click());
        onView(withText("test_food2"))
                .check(matches(isDisplayed()));
        ref.removeValue();
    }


}
