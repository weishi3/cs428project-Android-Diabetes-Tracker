package com.cs428.dit.diabetestracker;

import com.cs428.dit.diabetestracker.helpers.User;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by User on 4/11/2016.
 */
public class PMUDiagnosisActivityScoreTest {

    /**
     * Test wether a user with the following information will get the score according to the formula
     *
     * @param BMI
     * @param waistline
     * @param age
     * @param bloodpressure
     * @param gender
     * @param familyhistory
     * @param score
     */
    private void testScore(float BMI, float waistline, int age, int bloodpressure, boolean gender, boolean familyhistory, int score) {
        User user = new User(BMI, waistline, age, bloodpressure, gender, familyhistory);
        int s = user.calcScore();
        assertEquals(s, score);
    }

    @Test
    public void testMaleHealth() {
        testScore(21.0f, 72.0f, 25, 123, true, false, 9);
    }

    @Test
    public void testFemaleHealth() {
        testScore(21.0f, 72.0f, 25, 123, false, false, 10);
    }

    @Test
    public void testBadHealth() {
        testScore(30.0f, 89.0f, 50, 150, true, true, 41);
    }


}
