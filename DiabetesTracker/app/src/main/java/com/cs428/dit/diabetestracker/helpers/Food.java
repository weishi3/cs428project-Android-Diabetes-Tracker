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

    /**
     * Empty Food constructor. Required for Firebase to auto fill Food Object.
     */
    public Food() {
    }

    /**
     * Food constructor
     *
     * @param sugarInGram sugar food contains in gram
     * @param kilocalorie calories food contains
     * @param name        name of the food
     */
    public Food(double sugarInGram, double kilocalorie, String name) {
        setSugarInGram(sugarInGram);
        setKilocalorie(kilocalorie);
        this.name = name;
    }

    /**
     * @return name name of the food
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name of the food
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return kilocalorie calories the food contains
     */
    public double getKilocalorie() {

        return kilocalorie;
    }

    /**
     * @param kilocalorie calories the food contains
     */
    public void setKilocalorie(double kilocalorie) {
        if (kilocalorie < CALORIESMIN) {
            this.kilocalorie = CALORIESDEFAULT;
        } else {
            this.kilocalorie = kilocalorie;
        }
    }

    /**
     * @return sugarInGram sugar the food contains
     */
    public double getSugarInGram() {
        return sugarInGram;
    }

    /**
     * @param sugarInGram sugar the food contains
     */
    public void setSugarInGram(double sugarInGram) {
        if (sugarInGram < SUGARMIN) {
            this.sugarInGram = SUGARDEFAULT;
        } else {
            this.sugarInGram = sugarInGram;
        }

    }
}
