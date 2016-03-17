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
        User u = new User((float)21.0, (float)72.0, 25, 123, true, false);
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
        assertTrue(!sug.contains("When you are elder than 40, you've got a higher risk of diabetes and cardiovascular disease."));
        assertTrue(sug.contains("Giant infant symptom could add risk of diabetes in the future."));
        assertTrue(sug.contains("Your mother's abnormal glucose tolerance indicates higher indicates that you would bear threats of getting diabetes."));
        assertTrue(sug.contains("You blood fat looks high, which may be a signal or complication of diabetes."));
        assertTrue(sug.contains("Diabetes is mostly a life-length disease, so check your health condition and blood sugar level more often. "));
        assertTrue(sug.contains("You would probably need to see the doctor or take regular medical examinations."));
        assertTrue(!sug.contains("You looks fine!"));

        String sugD=u.generateSuggestionD();

        assertTrue(sugD.equals("you are fine based on the score! But the first two lists below are still a good choice for you to keep fit in the long run."));



    }



    @Test
    public void testCheater2() {
        User u = new User(28.0, 100.0, 50, 150, true, true);
        u.setSedentaryJob(false);
        u.setExerciseT(30);

        u.setDiagnosedD(false);
        u.setGDM(false);
        u.setWeightB(3);
        u.setHDL_C(0.99);
        u.setTG(2.3);


        //must cal score before suggestion other wise see next test case!!
        assertEquals(u.calcScore(),42);
        List<String> sug = u.generateSuggestion();
        assertEquals(u.isElder(), true);
        assertTrue(!sug.contains("Your lifestyle can be classified as sedentary, do more exercise!"));
        assertTrue(sug.contains("When you are elder than 40, you've got a higher risk of diabetes and cardiovascular disease."));
        assertTrue(!sug.contains("Giant infant symptom could add risk of diabetes in the future."));
        assertTrue(!sug.contains("Your mother's abnormal glucose tolerance indicates higher indicates that you would bear threats of getting diabetes."));
        assertTrue(!sug.contains("You blood fat looks high, which may be a signal or complication of diabetes."));
        assertTrue(!sug.contains("Diabetes is mostly a life-length disease, so check your health condition and blood sugar level more often. "));

        assertEquals(sug.size(),2);
        assertTrue(sug.contains("You would probably need to see the doctor or take regular medical examinations."));

        assertTrue(!sug.contains("You looks fine!"));

        String sugD=u.generateSuggestionD();

        assertEquals(sugD,"I would suggest you to avoid consuming high-GI food.\nAnd you should mostly focus on the recommended low-GI food with a proper amount suggested by doctor.");



    }

    /**
     * score still 42, but generate suggestion before that!
     */

    @Test
    public void testCheater2INI() {
        User u = new User(28.0, 100.0, 50, 150, true, true);
        u.setSedentaryJob(false);
        u.setExerciseT(30);

        u.setDiagnosedD(false);
        u.setGDM(false);
        u.setWeightB(3);
        u.setHDL_C(0.99);
        u.setTG(2.3);


        //must cal score before suggestion other wise see next test case!!
        List<String> sug = u.generateSuggestion();
        String sugD=u.generateSuggestionD();
        assertEquals(u.calcScore(),42);

        assertEquals(u.isElder(), true);
        assertTrue(!sug.contains("Your lifestyle can be classified as sedentary, do more exercise!"));
        assertTrue(sug.contains("When you are elder than 40, you've got a higher risk of diabetes and cardiovascular disease."));
        assertTrue(!sug.contains("Giant infant symptom could add risk of diabetes in the future."));
        assertTrue(!sug.contains("Your mother's abnormal glucose tolerance indicates higher indicates that you would bear threats of getting diabetes."));
        assertTrue(!sug.contains("You blood fat looks high, which may be a signal or complication of diabetes."));
        assertTrue(!sug.contains("Diabetes is mostly a life-length disease, so check your health condition and blood sugar level more often. "));

        assertEquals(sug.size(),1);
        assertTrue(!sug.contains("You would probably need to see the doctor or take regular medical examinations."));

        assertTrue(!sug.contains("You looks fine!"));



        assertEquals(sugD,"you are fine based on the score! But the first two lists below are still a good choice for you to keep fit in the long run.");



    }



}
