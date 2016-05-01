package com.cs428.dit.diabetestracker;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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
import java.util.UUID;
// Tests for RegistrationActivity
public class RegistrationActivityTest {
    //valid strings
    private String validEmail, validPassword, validAge, validWaistline, validBMI, validFamHistory, validBldPressure, validGender;
    //firebase Friendly emails
    private String friendlyValidEmail, friendlyInvalidEmail;
    //invalid strings
    private String invalidEmail, invalidPassword, invalidAge, invalidWaistline, invalidBMI, invalidFamHistory, invalidBldPressure, invalidGender;
    //the DataSnapShot of the database to verify if user was created.
    DataSnapshot usersSnapShot;
    //firebase reference to users
    Firebase users;

    /*
        Sets values for the strings we use in the test
        and makes sure that we are logged out when
        we start the test
     */
    @Before
    public void setUp() {
        try {
            openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
            onView(withText("Logout"))
                    .perform(click());
        } catch (Exception e) {
            //do nothing, not logged in
        }
        String rand = UUID.randomUUID().toString().replace('-', 'd');
        //naming: v = valid, i = invalid, f = fixed(fixed to work with firebase)
        validEmail = rand + "@testing.com";
        invalidEmail = "rodda2@illinois.edu";
        friendlyValidEmail = validEmail.replace('.','!');
        friendlyInvalidEmail = invalidEmail.replace('.','!');
        validPassword = "password";
        invalidPassword = "pas";
        validAge = "21";
        invalidAge ="200";
        validWaistline = "32.0";
        invalidWaistline = "twenty-two";
        validBMI = "20.0";
        invalidBMI = "twenny-one";
        validBldPressure = "120";
        invalidBldPressure = "abcde";
        users = new Firebase("https://brilliant-fire-9755.firebaseio.com")
                .child("users");
    }
    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);
    /*
        Makes sure that we are on the login page and the
        register button is shown
     */
    @Test
    public void registerButtonShownTest() {
        onView(withId(R.id.registerBtn))
                .perform(scrollTo(), closeSoftKeyboard())
                .check(matches(isDisplayed()));
    }
    /*
        Performs a valid registration with the strings from setUp,
        verifies that the user was created in firebase,
        and checks that the inputs match on the user's profile page
     */
    @Test
    public void validTest() {
        onView(withId(R.id.registerBtn)).perform(click());
        onView(withId(R.id.registrationEmail))
                .perform(typeText(validEmail), closeSoftKeyboard());
        onView(withId(R.id.registrationPW))
                .perform(typeText(validPassword), closeSoftKeyboard());
        onView(withId(R.id.registrationConfirmPW))
                .perform(typeText(validPassword), closeSoftKeyboard());
        onView(withId(R.id.age))
                .perform(typeText(validAge), closeSoftKeyboard());
        onView(withId(R.id.waistline))
                .perform(typeText(validWaistline), closeSoftKeyboard());
        onView(withId(R.id.BMI))
                .perform(typeText(validBMI), closeSoftKeyboard());
        onView(withId(R.id.genderFalse))
                .perform(click());
        onView(withId(R.id.bloodPressure))
                .perform(typeText(validBldPressure), closeSoftKeyboard());
        onView(withId(R.id.familyHistoryTrue))
                .perform(click());
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
        assert(usersSnapShot.hasChild(friendlyValidEmail));

        //try to log in as the newly created user to check
        //that all the values entered in the registration match
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.email))
                .perform(typeText(validEmail), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(validPassword), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button))
                .perform(click());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.profileAvatar))
                .perform(click());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.age))
                .check(matches(withText("21")));
        onView(withId(R.id.waistline))
                .check(matches(withText("32.0")));
        onView(withId(R.id.bloodPressure))
                .check(matches(withText("120")));
        onView(withId(R.id.BMI))
                .check(matches(withText("20.0")));
        onView(withId(R.id.familyHistory))
                .check(matches(withText("true")));
        onView(withId(R.id.gender))
                .check(matches(withText("Female")));

        Firebase createdUser = users.child(friendlyValidEmail);
        createdUser.removeValue();
    }

    /*
        Attempts a registration with incomplete/invalid information
        and verifies that the registration doesn't go through
     */
    @Test
    public void invalidTest() {
        onView(withId(R.id.registerBtn)).perform(click());
        onView(withId(R.id.registrationEmail))
                .perform(typeText(invalidEmail), closeSoftKeyboard());
        onView(withId(R.id.registrationPW))
                .perform(typeText(invalidPassword), closeSoftKeyboard());
        onView(withId(R.id.registrationConfirmPW))
                .perform(typeText(invalidPassword), closeSoftKeyboard());
        onView(withId(R.id.age))
                .perform(typeText(invalidAge), closeSoftKeyboard());
        onView(withId(R.id.waistline))
                .perform(typeText(validWaistline), closeSoftKeyboard());
        onView(withId(R.id.BMI))
                .perform(typeText(invalidBMI), closeSoftKeyboard());
        onView(withId(R.id.genderTrue))
                .perform(click());
        onView(withId(R.id.bloodPressure))
                .perform(typeText(invalidBldPressure), closeSoftKeyboard());
        onView(withId(R.id.familyHistoryFalse))
                .perform(click());
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
        assert(!usersSnapShot.hasChild(friendlyInvalidEmail));
    }
}