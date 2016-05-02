package com.cs428.dit.diabetestracker.helpers;

/**
 * Created by Yuchen on 3/11/16.
 * This class contains indicator types
 */
public class Indicator {
    /*
     * weight, blood pressure, blood sugar: user's data
     */
    public double weight;
    public double bloodPressure;
    public double bloodSugar;

    public Indicator(){

    }

    public Indicator(double bloodSugar, double bloodPressure, double weight){
        this.weight = weight;
        this.bloodPressure = bloodPressure;
        this.bloodSugar = bloodSugar;
    }

    public double getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(double bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public double getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(double bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
