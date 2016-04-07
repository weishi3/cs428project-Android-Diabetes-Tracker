package com.cs428.dit.diabetestracker.helpers;

import java.util.ArrayList;

/**
 * Created by water on 4/6/16.
 */
public class monitorSetting {

    public int numDaysMonitor;
    public String indicatorType;
    public ArrayList<String> warningMessage;

    public monitorSetting(){
        numDaysMonitor = 3;
        indicatorType = "bloodSugar";
        warningMessage = new ArrayList<>();
        warningMessage.add("bloodSugar");
    }

    public monitorSetting(int days, String indicatorType, ArrayList<String> warningMessage){
        numDaysMonitor = days;
        this.indicatorType = indicatorType;
        this.warningMessage =  warningMessage;
    }



}
