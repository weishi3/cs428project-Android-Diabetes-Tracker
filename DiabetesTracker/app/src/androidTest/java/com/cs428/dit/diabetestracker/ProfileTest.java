package com.cs428.dit.diabetestracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Tommy on 3/6/2016.
 */
public class ProfileTest {


    @Before
    public void setUp() {

    }

    @Rule
    public ActivityTestRule<ProfileActivity> activityTestRule =
            new ActivityTestRule<>(ProfileActivity.class);

    @Test
    public void showButtonTest() {
        onView(withId(R.id.editBtn))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkAge() {
        onView(withId(R.id.age))
                .check(matches(isDisplayed()));

    }

    @Test
    public void goToEditPage() {
        onView(withId(R.id.editBtn))
                .perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.submitBtn))
                .check(matches(isDisplayed()));

    }

    @Test
    public void changeAge() {
        onView(withId(R.id.editBtn))
                .perform(click());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.age))
                .perform(replaceText("25"), closeSoftKeyboard());

        onView(withId(R.id.submitBtn))
                .perform(click());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.age))
                .check(matches(withText("25")));
    }

    @Test
    public void changeUserData() {
        onView(withId(R.id.editBtn))
                .perform(click());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.familyHistory))
                .perform(scrollTo(), replaceText("false"), closeSoftKeyboard());

        onView(withId(R.id.waistline))
                .perform(scrollTo(), replaceText("30"), closeSoftKeyboard());

        onView(withId(R.id.GDM))
                .perform(scrollTo(), replaceText("true"), closeSoftKeyboard());

        onView(withId(R.id.weightB))
                .perform(scrollTo(), replaceText("10"), closeSoftKeyboard());

        onView(withId(R.id.TG))
                .perform(scrollTo(), replaceText("10.3"), closeSoftKeyboard());

        onView(withId(R.id.psychotropic))
                .perform(scrollTo(), replaceText("false"), closeSoftKeyboard());

        onView(withId(R.id.submitBtn))
                .perform(click());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.familyHistory))
                .check(matches(withText("false")));

        onView(withId(R.id.waistline))
                .check(matches(withText("30.0")));

        onView(withId(R.id.GDM))
                .check(matches(withText("true")));

        onView(withId(R.id.TG))
                .check(matches(withText("10.3")));

        onView(withId(R.id.weightB))
                .check(matches(withText("10")));

        onView(withId(R.id.psychotropic))
                .check(matches(withText("false")));

        onView(withId(R.id.CCVD))
                .check(matches(withText("N/A")));


    }

    @Test
    public void invalidInputs() {
        onView(withId(R.id.editBtn))
                .perform(click());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.age))
                .perform(replaceText("not a good age"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn))
                .perform(click());
        String errorText = activityTestRule.getActivity().getResources().getString(R.string.error_num_format);
        onView(withId(R.id.age))
                .check(matches(ErrorMatcher.withError(errorText)));


        onView(withId(R.id.waistline))
                .perform(replaceText("blah"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn))
                .perform(click());
        errorText = activityTestRule.getActivity().getResources().getString(R.string.error_num_format);
        onView(withId(R.id.waistline))
                .check(matches(ErrorMatcher.withError(errorText)));

        onView(withId(R.id.waistline))
                .perform(scrollTo(), replaceText("-20"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn))
                .perform(click());
        errorText = activityTestRule.getActivity().getResources().getString(R.string.error_waistline);
        onView(withId(R.id.waistline))
                .check(matches(ErrorMatcher.withError(errorText)));

        onView(withId(R.id.gender))
                .perform(scrollTo(), replaceText("10"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn))
                .perform(click());
        errorText = activityTestRule.getActivity().getResources().getString(R.string.error_gender);
        onView(withId(R.id.gender))
                .check(matches(ErrorMatcher.withError(errorText)));

        onView(withId(R.id.BMI))
                .perform(scrollTo(), replaceText("not a valid bmi"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn))
                .perform(click());
        errorText = activityTestRule.getActivity().getResources().getString(R.string.error_num_format);
        onView(withId(R.id.BMI))
                .check(matches(ErrorMatcher.withError(errorText)));

        onView(withId(R.id.bloodPressure))
                .perform(scrollTo(), replaceText("not a number"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn))
                .perform(click());
        errorText = activityTestRule.getActivity().getResources().getString(R.string.error_num_format);
        onView(withId(R.id.bloodPressure))
                .check(matches(ErrorMatcher.withError(errorText)));

        onView(withId(R.id.exerciseT))
                .perform(scrollTo(), replaceText("not a number"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn))
                .perform(click());
        errorText = activityTestRule.getActivity().getResources().getString(R.string.error_num_format);
        onView(withId(R.id.exerciseT))
                .check(matches(ErrorMatcher.withError(errorText)));

        onView(withId(R.id.weightB))
                .perform(scrollTo(), replaceText("not a number"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn))
                .perform(click());
        errorText = activityTestRule.getActivity().getResources().getString(R.string.error_num_format);
        onView(withId(R.id.weightB))
                .check(matches(ErrorMatcher.withError(errorText)));

        onView(withId(R.id.HDL_C))
                .perform(scrollTo(), replaceText("not a number"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn))
                .perform(click());
        errorText = activityTestRule.getActivity().getResources().getString(R.string.error_num_format);
        onView(withId(R.id.HDL_C))
                .check(matches(ErrorMatcher.withError(errorText)));

        onView(withId(R.id.TG))
                .perform(scrollTo(), replaceText("not a number"), closeSoftKeyboard());
        onView(withId(R.id.submitBtn))
                .perform(click());
        errorText = activityTestRule.getActivity().getResources().getString(R.string.error_num_format);
        onView(withId(R.id.TG))
                .check(matches(ErrorMatcher.withError(errorText)));




    }

}