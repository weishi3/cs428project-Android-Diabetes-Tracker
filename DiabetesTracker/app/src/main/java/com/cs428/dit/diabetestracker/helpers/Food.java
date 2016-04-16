package com.cs428.dit.diabetestracker.helpers;

/**
 * Created by QiZhang on 3/9/16.
 * This class corresponds to the food json object in firebase.
 */
public class Food {
    public static final int CALORIESMIN = 0;
    public static final double CALORIESDEFAULT = 0.0;
    public static final int SUGARMIN = 0;
    public static final double SUGARDEFAULT = 0.0;
    String name;
    double kilocalorie;
    double sugarInGram;

    public Food() {
    }

    public Food(double sugarInGram, double kilocalorie, String name) {
        setSugarInGram(sugarInGram);
        setKilocalorie(kilocalorie);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKilocalorie() {

        return kilocalorie;
    }

    public void setKilocalorie(double kilocalorie) {
        if (kilocalorie < CALORIESMIN) {
            this.kilocalorie = CALORIESDEFAULT;
        }
        else{
            this.kilocalorie = kilocalorie;
        }
    }

    public double getSugarInGram() {
        return sugarInGram;
    }

    public void setSugarInGram(double sugarInGram) {
        if (sugarInGram < SUGARMIN) {
            this.sugarInGram = SUGARDEFAULT;
        }
        else{
            this.sugarInGram = sugarInGram;
        }

    }
}
