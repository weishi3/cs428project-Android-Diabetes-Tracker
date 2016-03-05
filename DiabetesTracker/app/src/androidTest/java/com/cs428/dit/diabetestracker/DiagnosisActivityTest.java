package com.cs428.dit.diabetestracker;

import com.cs428.dit.diabetestracker.helpers.User;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Yuchen on 3/4/16.
 */
public class DiagnosisActivityTest {
    //float BMI, float waistline, int age, int bloodPressure, boolean gender, boolean familyHistory

    @Test
    public void testMaleHealth() {
        User user = new User(21, 72, 25, 123, true, false);
        int score = user.calcScore();

        assertEquals(user.getScoreBasedOnAge(), 4);
        assertEquals(user.getScoreBasedOnWaistlineMale(), 0);
        assertEquals(user.getScoreBasedOnBMI(), 0);
        assertEquals(user.getScoreBasedOnBloodPressure(), 3);
        assertEquals(user.getScoreBasedOnFamilyHistory(), 0);
        assertEquals(user.getScoreBasedOnGender(), 2);



        assertEquals(score, 9);
    }
//    @Test
//    public void testMaleHealthBMI() {
//        User user = new User(21, 72, 25, 123, false, true);
//        int score = user.calcScore();
//        assertEquals(score, 7);
//    }
//    @Test
//    public void testMaleHealth() {
//        User user = new User(21, 72, 25, 123, false, true);
//        int score = user.calcScore();
//        assertEquals(score, 7);
//    }
//    @Test
//    public void testMaleHealth() {
//        User user = new User(21, 72, 25, 123, false, true);
//        int score = user.calcScore();
//        assertEquals(score, 7);
//    }
}
