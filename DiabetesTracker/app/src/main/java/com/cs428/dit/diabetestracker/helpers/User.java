package com.cs428.dit.diabetestracker.helpers;

/**
 * Created by Yuchen on 3/4/16.
 */
public class User {

    private float BMI;
    private float waistline;
    private int age;
    private int bloodPressure;
    private boolean familyHistory;
    private boolean gender;

    private String score;

    public User(float BMI, float waistline, int age, int bloodPressure, boolean gender, boolean familyHistory) {
        this.BMI = BMI;
        this.waistline = waistline;
        this.age = age;
        this.bloodPressure = bloodPressure;
        this.gender = gender;
        this.familyHistory = familyHistory;
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

    public String getScore() {
        int score = calcScore();
        String result = String.valueOf(score);
        return result;
    }

    private int getScoreBasedOnAge(){
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

    private int getScoreBasedOnBMI(){
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
            score = 15;
        }
        return score;
    }

    private int getScoreBasedOnWaistlineMale(){
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

    private int getScoreBasedOnWaistlineFemale(){
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

    private int getScoreBasedOnBloodPressure(){
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

    private int getScoreBasedOnFamilyHistory(){
        int score = 0;
        if(familyHistory){
            score = 6;
        }
        return score;
    }

    private int getScoreBasedOnGender(){
        int score = 0;
        if(gender){
            score = 2;
        }
        return score;
    }

    private int calcScore() {
        int score = 0;
        if (gender){
            score += getScoreBasedOnWaistlineMale();
        }
        else{
            score += getScoreBasedOnWaistlineFemale();
        }
        score += (getScoreBasedOnAge() + getScoreBasedOnBloodPressure() + getScoreBasedOnBMI() + getScoreBasedOnFamilyHistory() + getScoreBasedOnGender());
        return score;
    }
}
