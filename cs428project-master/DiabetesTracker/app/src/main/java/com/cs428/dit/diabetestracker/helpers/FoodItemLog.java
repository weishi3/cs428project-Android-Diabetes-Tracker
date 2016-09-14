package com.cs428.dit.diabetestracker.helpers;

/**
 * Created by QiZhang on 3/9/16.
 * This class represents how a food item is stored in Firebase.
 */
public class FoodItemLog {
    String date;
    Food food;

    /**
     * Empty constructor, necessary for Firebase.
     */
    public FoodItemLog() {
    }

    /**
     * @param date the date the food item is logged
     * @param food the Food Object of the food item
     */
    public FoodItemLog(String date, Food food) {
        this.date = date;
        this.food = food;

    }

    /**
     * @return food Food Object of the food item
     */
    public Food getFood() {
        return food;
    }

    /**
     * @param food Food Object of the food item
     */
    public void setFood(Food food) {
        this.food = food;
    }

    /**
     * @return date the date the food item is logged
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date the food item is logged
     */
    public void setDate(String date) {
        this.date = date;
    }

}
