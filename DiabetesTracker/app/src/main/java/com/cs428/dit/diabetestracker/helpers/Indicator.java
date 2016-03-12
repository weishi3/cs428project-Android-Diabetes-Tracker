package com.cs428.dit.diabetestracker.helpers;

/**
 * Created by Yuchen on 3/11/16.
 */
public class Indicator {
    public float weight;
    public float bloodPressure;
    public float bloodSugar;

    public Indicator(){

    }

    public Indicator(float bloodSugar, float bloodPressure, float weight){
        this.weight = weight;
        this.bloodPressure = bloodPressure;
        this.bloodSugar = bloodSugar;
    }

    public float getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(float bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public float getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(float bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
