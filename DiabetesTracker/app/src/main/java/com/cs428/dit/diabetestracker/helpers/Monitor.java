package com.cs428.dit.diabetestracker.helpers;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by water on 4/6/16.
 */
public class Monitor {

    public int count;
    public Queue<Double> bloodSugarQueue;
    public boolean warning;

    public Monitor(){

    }

    public Monitor(int count){
        this.count = count;
        bloodSugarQueue = new LinkedList<Double>();
    }

    public void addBloodSugar(double bs){
        bloodSugarQueue.offer(bs);
        if (bloodSugarQueue.size() > count){
            bloodSugarQueue.poll();
        }
    }

    public void detectWarning(){
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
