package com.cs428.dit.diabetestracker.helpers;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by water on 4/6/16.
 */
public class Monitor {

    private int count=3;
    private Queue<Double> bloodSugarQueue;
    private boolean warning;

    public Monitor(){
        bloodSugarQueue = new LinkedList<Double>();

    }




    public Monitor(int count){
        this.count = count;
        bloodSugarQueue = new LinkedList<Double>();
    }

    //setters
    public void setCount(int c){
        count=c;
    }

    public void setQueue(Queue<Double> bloodSugarQueue){
        this.bloodSugarQueue=bloodSugarQueue;
    }

    //getters
    public boolean getWarning(){
        return warning;
    }




    public void addBloodSugar(double bs){
        bloodSugarQueue.offer(bs);
        if (bloodSugarQueue.size() > count){
            bloodSugarQueue.poll();
        }
    }

    public void detectWarning(){
        while(bloodSugarQueue.size()>count){
            bloodSugarQueue.poll();
        }
        int counter = 0;
        for(double d : bloodSugarQueue){
            if (d >= 10.0){
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
