package com.cs428.dit.diabetestracker.helpers;

/**
 * Created by QiZhang on 3/9/16.
 * This class corresponds to the food json object in firebase.
 */
public class Food {
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
        if (kilocalorie < 0) {
            this.kilocalorie = 0.0;
        }
        else{
            this.kilocalorie = kilocalorie;
        }
    }

    public double getSugarInGram() {
        return sugarInGram;
    }

    public void setSugarInGram(double sugarInGram) {
        if (sugarInGram < 0) {
            this.sugarInGram = 0.0;
        }
        else{
            this.sugarInGram = sugarInGram;
        }

    }
}
