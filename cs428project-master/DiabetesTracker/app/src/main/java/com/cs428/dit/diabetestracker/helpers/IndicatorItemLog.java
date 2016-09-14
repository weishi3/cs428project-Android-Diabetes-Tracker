package com.cs428.dit.diabetestracker.helpers;

/**
 * Created by Yuchen on 3/11/16.
 * This class stores an indicator object and the date
 * when the user input his/her indicator data
 */
public class IndicatorItemLog {
    /*
     * date: the date when the user input the data
     * indicator: the indicator object which contains weight, blood sugar and blood pressure
     */
    public String date;
    public Indicator indicator;

    public IndicatorItemLog(){

    }

    public IndicatorItemLog(String date, Indicator indicator) {
        this.date = date;
        this.indicator = indicator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }
}
