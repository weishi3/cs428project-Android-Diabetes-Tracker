package com.cs428.dit.diabetestracker.helpers;

/**
 * Created by QiZhang on 3/9/16.
 */
public class FoodItemLog {
    String date;
    Food food;

    /**
     * Empty constructor, necessary for Firebase.
     */
    public FoodItemLog() {
    }

    public FoodItemLog(String date, Food food) {
        this.date = date;
        this.food = food;

    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
