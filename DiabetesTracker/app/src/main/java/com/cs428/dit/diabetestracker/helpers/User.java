package com.cs428.dit.diabetestracker.helpers;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuchen on 3/4/16.
 * Edited by Weishi on 3/5/16
 * It would generate suggestions based on the running of first-time diagnosis based on profile
 */
public class User {

    private float BMI;
    private float waistline;
    private int age;
    private int bloodPressure;
    private boolean familyHistory;
    private boolean gender;
    private List<String> suggestions=new ArrayList<String>();
    private String sugD="";


    private boolean isElder=false;



    //start of optionals
    private boolean sedentaryJob =false;
    //exercise
    private int exerciseT =30;

    // have ever be diagnosed as diabetes or IGR history?Sex steroids diabetes?
    private boolean diagnosedD=false;

    //is user's mother get diabetes when she is pregnant?
    private boolean GDM=false;

    //weight when born
    private int weightB=0;

    //Atherosclerotic CCVd: stands for chronic cerebrovascular disease
    private boolean CCVD=false;

    //does a female user has PCOS? stands for Polycystic ovary syndrome
    private boolean PCOS=false;

    // does user have psychotropic problems.
    private boolean psychotropic=false;



    //the following are indicators for blood fat
    private Double HDL_C=0.92;
    private Double TG = 2.21;


    //end of optionals



    private int score = 0;



    public User(float BMI, float waistline, int age, int bloodPressure, boolean gender, boolean familyHistory) {
        this.BMI = BMI;
        this.waistline = waistline;
        this.age = age;

        this.bloodPressure = bloodPressure;
        this.gender = gender;
        this.familyHistory = familyHistory;
        this.isElder= (age>=40)?true:false;
    }

    public boolean isGender() {
        return gender;
    }

    public boolean isElder() {

        return isElder;
    }

    public void setElder(boolean elder) {
        isElder = elder;
    }

    public boolean isSedentaryJob() {
        return sedentaryJob;
    }

    public void setSedentaryJob(boolean sedentaryJob) {
        this.sedentaryJob = sedentaryJob;
    }

    public int getExerciseT() {
        return exerciseT;
    }

    public void setExerciseT(int exerciseT) {
        this.exerciseT = exerciseT;
    }

    public boolean isDiagnosedD() {
        return diagnosedD;
    }

    public void setDiagnosedD(boolean diagnosedD) {
        this.diagnosedD = diagnosedD;
    }

    public boolean isGDM() {
        return GDM;
    }

    public void setGDM(boolean GDM) {
        this.GDM = GDM;
    }

    public int getWeightB() {
        return weightB;
    }

    public void setWeightB(int weightB) {
        this.weightB = weightB;
    }

    public Double getHDL_C() {
        return HDL_C;
    }

    public void setHDL_C(Double HDL_C) {
        this.HDL_C = HDL_C;
    }

    public Double getTG() {
        return TG;
    }

    public void setTG(Double TG) {
        this.TG = TG;
    }

    public float getBMI() {
        return BMI;
    }

    public void setBMI(float BMI) {
        this.BMI = BMI;
    }

    public float getWaistline() {
        return waistline;
    }

    public void setWaistline(float waistline) {
        this.waistline = waistline;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(int bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public boolean isFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(boolean familyHistory) {
        this.familyHistory = familyHistory;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }






    //debug info : += =
    public String generateSuggestionD(){


        if (score > 25) sugD= "I would suggest you to avoid consuming high-GI food.";
        if (score > 30) sugD+="\nYou should mostly focus on the recommended low-GI food with a proper amount suggested by doctor.";
        if (score<=25 && score > 20)  sugD="You should intentionally take food from list one and list two to avoid possible risk of diabetes. ";
        if (score <= 20 ) sugD="You are fine based on the score! But the first two lists below are still a good choice for you to keep fit in the long run.";
        return sugD;
    }

    //setter and getter of suggestion
    public List<String> generateSuggestion(){
        if (CCVD) suggestions.add("Chronic cerebrovascular disease is a notable killer, especially for middle-aged and elder people with diabetes.");
        if (isElder) suggestions.add("When you are elder than 40, you've got a higher risk of diabetes and cardiovascular disease.");
        if ((sedentaryJob && exerciseT <60) || (!sedentaryJob && exerciseT<30) ) suggestions.add("Your lifestyle can be classified as sedentary, do more exercise!");
        if (weightB>=4 ) suggestions.add("Giant infant symptom could add risk of diabetes in the future.");
        if (GDM) suggestions.add("Your mother's abnormal glucose tolerance indicates higher indicates that you would bear threats of getting diabetes.");
        if (HDL_C<=0.91 && TG>=2.22) suggestions.add("You blood fat looks high, which may be a signal or complication of diabetes.");
        if (diagnosedD) suggestions.add("Diabetes is mostly a life-length disease, so check your health condition and blood sugarInGram level more often. ");
        if (score>25 || (suggestions.size()>=4)) suggestions.add("You would probably need to see the doctor or take regular medical examinations.");
        if (suggestions.size()==0) suggestions.add("You looks fine!");

        return suggestions;
    }




    //below are score parts
    public int getScoreBasedOnAge(){
        int score = 0;
        if(age <= 24){
            score = 0;
        }
        else if (age >= 25 && age <= 34){
            score = 4;
        }
        else if (age >= 35 && age <= 39){
            score = 8;
        }
        else if (age >= 40 && age <= 44){
            score = 11;
        }
        else if (age >= 45 && age <= 49){
            score = 12;
        }
        else if (age >= 50 && age <= 54){
            score = 13;
        }
        else if (age >= 55 && age <= 59){
            score = 15;
        }
        else if (age >= 60 && age <= 64){
            score = 16;
        }
        else {
            score = 18;
        }
        return score;
    }

    public int getScoreBasedOnBMI(){
        int score = 0;
        if(BMI < 22){
            score = 0;
        }
        else if (BMI >= 22 && BMI < 24){
            score = 1;
        }
        else if (BMI >= 24 && BMI < 30){
            score = 3;
        }
        else {
            score = 5;
        }
        return score;
    }

    public int getScoreBasedOnWaistlineMale(){
        int score = 0;
        if(waistline < 75){
            score = 0;
        }
        else if (waistline >= 75 && waistline < 80){
            score = 3;
        }
        else if (waistline >= 80 && waistline < 85){
            score = 5;
        }
        else if (waistline >= 85 && waistline < 90){
            score = 7;
        }
        else if (waistline >= 90 && waistline < 95){
            score = 8;
        }
        else {
            score = 10;
        }
        return score;
    }

    public int getScoreBasedOnWaistlineFemale(){
        int score = 0;
        if(waistline < 70){
            score = 0;
        }
        else if (waistline >= 70 && waistline < 75){
            score = 3;
        }
        else if (waistline >= 75 && waistline < 80){
            score = 5;
        }
        else if (waistline >= 80 && waistline < 85){
            score = 7;
        }
        else if (waistline >= 85 && waistline < 90){
            score = 8;
        }
        else {
            score = 10;
        }
        return score;
    }

    public int getScoreBasedOnBloodPressure(){
        int score = 0;
        if(bloodPressure < 110){
            score = 0;
        }
        else if (bloodPressure >= 110 && bloodPressure < 120){
            score = 1;
        }
        else if (bloodPressure >= 120 && bloodPressure < 130){
            score = 3;
        }
        else if (bloodPressure >= 130 && bloodPressure < 140){
            score = 6;
        }
        else if (bloodPressure >= 140 && bloodPressure < 150){
            score = 7;
        }
        else if (bloodPressure >= 150 && bloodPressure < 160){
            score = 8;
        }
        else {
            score = 10;
        }
        return score;
    }

    public int getScoreBasedOnFamilyHistory(){
        int score = 0;
        if(familyHistory){
            score = 6;
        }
        return score;
    }

    public int getScoreBasedOnGender(){
        int score = 0;
        if(gender){
            score = 2;
        }
        return score;
    }

    public int calcScore() {
        score = 0;
        if (gender){
            score += getScoreBasedOnWaistlineMale();
        }
        else{
            score += getScoreBasedOnWaistlineFemale();
        }
        score += (getScoreBasedOnAge() + getScoreBasedOnBloodPressure() + getScoreBasedOnBMI() + getScoreBasedOnFamilyHistory() + getScoreBasedOnGender());
        return score;
    }

    public String getScore() {
        calcScore();
        String result = String.valueOf(score);
        return result;
    }

}
