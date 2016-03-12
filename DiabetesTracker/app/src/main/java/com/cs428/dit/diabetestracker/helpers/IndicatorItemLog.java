package com.cs428.dit.diabetestracker.helpers;

/**
 * Created by Yuchen on 3/11/16.
 */
public class IndicatorItemLog {
    public String date;
    public Indicator indicator;

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
