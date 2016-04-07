package com.cs428.dit.diabetestracker.helpers;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by water on 4/7/16.
 */
public class MonitorPressure {
    public int count=3;
    public Queue<Double> bloodPressureQueue;
    public boolean warning;

    public MonitorPressure(){

    }



    public MonitorPressure(int count){
        this.count = count;
        bloodPressureQueue = new LinkedList<Double>();
    }

    public void addBloodPressure(double bs){
        bloodPressureQueue.offer(bs);
        if (bloodPressureQueue.size() > count){
            bloodPressureQueue.poll();
        }
    }

    public void detectWarning(){
        while(bloodPressureQueue.size()>count){
            bloodPressureQueue.poll();
        }
        int counter = 0;
        for(double d : bloodPressureQueue){
            if (d >= 140.0){
                counter++;
            }
        }

        if (counter == count){
            warning = true;
        }
        else{
            warning = false;
        }
    }
}
