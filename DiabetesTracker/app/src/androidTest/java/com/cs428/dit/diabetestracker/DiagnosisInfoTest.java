package com.cs428.dit.diabetestracker;


import com.cs428.dit.diabetestracker.helpers.User;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by water on 3/6/16.
 */
public class DiagnosisInfoTest {
    @Test
    public void testCheater1() {

        User u = new User(21.0f, 72.0f, 25, 123, true, false);
        u.setSedentaryJob(true);
        u.setExerciseT(30);

        u.setDiagnosedD(true);
        u.setGDM(true);
        u.setWeightB(5);
        u.setHDL_C(0.88);
        u.setTG(2.3);

        List<String> sug = u.generateSuggestion();
        assertEquals(u.isElder(), false);
        assertTrue(sug.contains("Your lifestyle can be classified as sedentary, do more exercise!"));
        assertTrue(!sug.contains("Since you are elder than 40, you've got a higher risk of diabetes and cardiovascular disease. Take physical examination more often!"));
        assertTrue(sug.contains("Giant infant symptom means you have higher risk of getting diabetes than average people in the future."));
        assertTrue(sug.contains("Since your mother have gestational diabetes mellitus, you would bear more threats of getting diabetes."));
        assertTrue(sug.contains("You blood fat looks high, which may be a signal or complication of diabetes."));
        assertTrue(sug.contains("Diabetes is mostly a life-length disease, so keep checking your health condition and monitor blood sugar level."));

        String sum=u.getSuggestionsSummary();
        assertTrue(sum.equals("You would probably need to consult a doctor for more detailed information with following points."));


        String sugD=u.generateSuggestionD();

        assertTrue(sugD.equals("Based on your profile, It looks you are far from getting diabetes! Though a good lifestyle is important and MEDIUM-GI and LOW-GI food is helpful in the long run."));



    }







}
