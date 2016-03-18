package com.cs428.dit.diabetestracker;

import com.cs428.dit.diabetestracker.helpers.User;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Yuchen on 3/4/16.
 */
public class DiagnosisActivityScoreTest {
    //Double BMI, Double waistline, int age, int blood_pressure, boolean gender, boolean familyHistory

    @Test
    public void testMaleHealth() {
        User user = new User(21.0f, 72.0f, 25, 123, true, false);
        int score = user.calcScore();

        assertEquals(user.getScoreBasedOnAge(), 4);
        assertEquals(user.getScoreBasedOnWaistlineMale(), 0);
        assertEquals(user.getScoreBasedOnBMI(), 0);
        assertEquals(user.getScoreBasedOnBloodPressure(), 3);
        assertEquals(user.getScoreBasedOnFamilyHistory(), 0);
        assertEquals(user.getScoreBasedOnGender(), 2);



        assertEquals(score, 9);
    }

    @Test
    public void testFeMaleHealth() {


        User user = new User(21.0f, 72.0f, 25, 123, false, false);
        int score = user.calcScore();

        assertEquals(user.getScoreBasedOnAge(), 4);
        assertEquals(user.getScoreBasedOnWaistlineFemale(), 3);
        assertEquals(user.getScoreBasedOnBMI(), 0);
        assertEquals(user.getScoreBasedOnBloodPressure(), 3);
        assertEquals(user.getScoreBasedOnFamilyHistory(), 0);
        assertEquals(user.getScoreBasedOnGender(), 0);



        assertEquals(score, 10);
    }



    @Test
    public void testBadHealth() {

        User user = new User(30.0f, 89.0f, 50, 150, true, true);
        int score = user.calcScore();
        assertEquals(user.getScoreBasedOnAge(), 13);
        assertEquals(user.getScoreBasedOnWaistlineMale(), 7);
        assertEquals(user.getScoreBasedOnBMI(), 5);
        assertEquals(user.getScoreBasedOnBloodPressure(), 8);
        assertEquals(user.getScoreBasedOnFamilyHistory(), 6);
        assertEquals(user.getScoreBasedOnGender(), 2);



        assertEquals(score, 41);
    }



}
