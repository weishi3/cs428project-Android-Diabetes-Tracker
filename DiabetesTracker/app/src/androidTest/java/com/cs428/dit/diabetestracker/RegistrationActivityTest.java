package com.cs428.dit.diabetestracker;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import java.util.UUID;

// Tests for RegistrationActivity
public class RegistrationActivityTest {
    //valid strings
    private String vEmail, vPassword, vAge, vWaistline, vBMI, vFamHistory, vBldPressure, vGender;
    //firebase Friendly emails
    private String fvEmail, fiEmail;
    //invalid strings
    private String iEmail, iPassword, iAge, iWaistline, iBMI, iFamHistory, iBldPressure, iGender;
    //the DataSnapShot of the database to verify if user was created.
    DataSnapshot usersSnapShot;
    //firebase reference to users
    Firebase users;

    @Before
    public void setUp() {
        String rand = UUID.randomUUID().toString().replace('-', 'd');
        vEmail = rand + "@testing.com";
        iEmail = "rodda2@illinois.edu";
        fvEmail = vEmail.replace('.','!');
        fiEmail = iEmail.replace('.','!');
        vPassword = "password";
        iPassword = "pas";
        vAge = "21";
        iAge ="200";
        vWaistline = "32.0";
        iWaistline = "twenty-two";
        vBMI = "20.0";
        iBMI = "twenny-one";
        vFamHistory = "true";
        iFamHistory = "what";
        vBldPressure = "120";
        iBldPressure = "abcde";
        vGender = "male";
        iGender = "something else";
        users = new Firebase("https://brilliant-fire-9755.firebaseio.com")
                .child("users");
    }

    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<RegistrationActivity> activityTestRule =
            new ActivityTestRule<>(RegistrationActivity.class);

    @Test
    public void registerButtonShownTest() {
        onView(withId(R.id.registerBtn))
                .check(matches(isDisplayed()));
    }

    @Test
    public void validTest() {
        onView(withId(R.id.registrationEmail))
                .perform(typeText(vEmail), closeSoftKeyboard());
        onView(withId(R.id.registrationPW))
                .perform(typeText(vPassword), closeSoftKeyboard());
        onView(withId(R.id.registrationConfirmPW))
                .perform(typeText(vPassword), closeSoftKeyboard());
        onView(withId(R.id.age))
                .perform(typeText(vAge), closeSoftKeyboard());
        onView(withId(R.id.waistline))
                .perform(typeText(vWaistline), closeSoftKeyboard());
        onView(withId(R.id.BMI))
                .perform(typeText(vBMI), closeSoftKeyboard());
        onView(withId(R.id.gender))
                .perform(typeText(vGender), closeSoftKeyboard());
        onView(withId(R.id.bloodPressure))
                .perform(typeText(vBldPressure), closeSoftKeyboard());
        onView(withId(R.id.familyHistory))
                .perform(typeText(vFamHistory), closeSoftKeyboard());
        onView(withId(R.id.registerBtn))
                .perform(click());
        //check for completion
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
        assert(usersSnapShot.hasChild(fvEmail));
        Firebase createdUser = users.child(fvEmail);
        createdUser.removeValue();

    }

    @Test
    public void invalidTest() {
        onView(withId(R.id.registrationEmail))
                .perform(typeText(iEmail), closeSoftKeyboard());
        onView(withId(R.id.registrationPW))
                .perform(typeText(iPassword), closeSoftKeyboard());
        onView(withId(R.id.registrationConfirmPW))
                .perform(typeText(iPassword), closeSoftKeyboard());
        onView(withId(R.id.age))
                .perform(typeText(iAge), closeSoftKeyboard());
        onView(withId(R.id.waistline))
                .perform(typeText(vWaistline), closeSoftKeyboard());
        onView(withId(R.id.BMI))
                .perform(typeText(iBMI), closeSoftKeyboard());
        onView(withId(R.id.gender))
                .perform(typeText(iGender), closeSoftKeyboard());
        onView(withId(R.id.bloodPressure))
                .perform(typeText(iBldPressure), closeSoftKeyboard());
        onView(withId(R.id.familyHistory))
                .perform(typeText(iFamHistory), closeSoftKeyboard());
        onView(withId(R.id.registerBtn))
                .perform(click());
        //check for completion
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        assert(!usersSnapShot.hasChild(fiEmail));
    }
}