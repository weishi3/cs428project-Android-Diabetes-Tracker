package com.cs428.dit.diabetestracker;

import com.cs428.dit.diabetestracker.helpers.FoodList;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;

/**
 * Created by User on 4/26/2016.
 */
@RunWith(AndroidJUnit4.class)
public class FoodListTest {
    Firebase users;
    DataSnapshot usersSnapShot;

    /**
     * sets up a firebase connection used for testing
     */
    @Before
    public void setUp() {
        Firebase base = new Firebase("https://brilliant-fire-9755.firebaseio.com");
        users = base.child("users").child("yiyuli9@gmail!com");
    }

    @Rule
    public ActivityTestRule<SaveLowGIDietActivity> saveDietActivityRule =
            new ActivityTestRule<>(SaveLowGIDietActivity.class);

    /**
     * The parameterized test function,the boolean inputs specifies wether a specified food is added to the food list
     * then the food list is saved to the firebase location with the preset firebase connection
     * then we assert that the foodlist element is there then remove it
     *
     * @param riceNoodles
     * @param lentils
     * @param sweetCorn
     * @param beans
     * @param yogurt
     * @param greekYogurt
     * @param oranges
     * @param plums
     */
    private void testFoodList(boolean riceNoodles, boolean lentils, boolean sweetCorn, boolean beans, boolean yogurt, boolean greekYogurt,
                              boolean oranges, boolean plums) {
        String s = "";
        if (riceNoodles) {
            s += "riceNoodles";
        }
        if (lentils) {
            s += "lentils";
        }
        if (sweetCorn) {
            s += "sweetCorn";
        }
        if (beans) {
            s += "beans";
        }
        if (yogurt) {
            s += "yogurt";
        }
        if (greekYogurt) {
            s += "greekYogurt";
        }
        if (oranges) {
            s += "oranges";
        }
        if (plums) {
            s += "plums";
        }
        FoodList.saveFoodList(saveDietActivityRule.getActivity(), "yiyuli9@gmail!com", s);
        /**
         * Adds a listener to read the data in the firebase location we write to
         */
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                usersSnapShot = snapshot;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        assert (usersSnapShot.hasChild("foodlist"));
        Firebase foodlist = users.child("foodlist");
        foodlist.removeValue();
    }

    @Test
    public void FoodListTest1() {
        testFoodList(true, true, false, true, false, true, true, true);
    }


    @Test
    public void FoodListTest2() {
        testFoodList(false, true, false, true, false, true, true, true);
    }

}
