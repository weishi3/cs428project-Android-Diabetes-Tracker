package com.cs428.dit.diabetestracker.helpers;

import bsh.Interpreter;
/**
 * Created by User on 4/12/2016.
 */
public class StringRunner {
    public void runString(String code){
        Interpreter interpreter = new Interpreter();
        try {
            interpreter.set("context", this);//set any variable, you can refer to it directly from string
            interpreter.eval(code);//execute code
        }
        catch (Exception e){//handle exception
            e.printStackTrace();
        }
    }
}
