package com.cs428.dit.diabetestracker;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
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
        try {
            openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
            onView(withText("Logout"))
                    .perform(click());
        } catch (Exception e) {
            //do nothing, not logged in
        }
        String rand = UUID.randomUUID().toString().replace('-', 'd');
        //naming: v = valid, i = invalid, f = fixed(fixed to work with firebase)
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
        vBldPressure = "120";
        iBldPressure = "abcde";
        users = new Firebase("https://brilliant-fire-9755.firebaseio.com")
                .child("users");
    }
    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);
    @Test
    public void registerButtonShownTest() {
        onView(withId(R.id.registerBtn))
                .perform(scrollTo(), closeSoftKeyboard())
                .check(matches(isDisplayed()));
    }
    @Test
    public void validTest() {
        onView(withId(R.id.registerBtn)).perform(click());
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
        onView(withId(R.id.genderFalse))
                .perform(click());
        onView(withId(R.id.bloodPressure))
                .perform(typeText(vBldPressure), closeSoftKeyboard());
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
        assert(usersSnapShot.hasChild(fvEmail));

        //try to log in as the newly created user to check
        //that all the values entered in the registration match
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.email))
                .perform(typeText(vEmail), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(vPassword), closeSoftKeyboard());
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

        Firebase createdUser = users.child(fvEmail);
        createdUser.removeValue();
    }


    @Test
    public void invalidTest() {
        onView(withId(R.id.registerBtn)).perform(click());
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
        onView(withId(R.id.genderTrue))
                .perform(click());
        onView(withId(R.id.bloodPressure))
                .perform(typeText(iBldPressure), closeSoftKeyboard());
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
        assert(!usersSnapShot.hasChild(fiEmail));
    }
}