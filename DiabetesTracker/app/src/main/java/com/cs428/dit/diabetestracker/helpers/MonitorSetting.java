package com.cs428.dit.diabetestracker.helpers;

import java.util.ArrayList;

/**
 * Created by water on 4/6/16.
 */
public class MonitorSetting {

    public int getNumDaysMonitor() {
        return numDaysMonitor;
    }

    public void setNumDaysMonitor(int numDaysMonitor) {
        this.numDaysMonitor = numDaysMonitor;
    }

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public ArrayList<String> getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(ArrayList<String> warningMessage) {
        this.warningMessage = warningMessage;
    }

    public int numDaysMonitor;
    public String indicatorType;
    public ArrayList<String> warningMessage;

    public MonitorSetting(){
        numDaysMonitor = 3;
        indicatorType = "bloodSugar";
        warningMessage = new ArrayList<>();
        warningMessage.add("bloodSugar");
    }

    public MonitorSetting(int days, String indicatorType, ArrayList<String> warningMessage){
        numDaysMonitor = days;
        this.indicatorType = indicatorType;
        this.warningMessage =  warningMessage;
    }



}
