package com.cs428.dit.diabetestracker.helpers;

import bsh.Interpreter;
/**
 * Created by Yuelong on 4/12/2016.
 * a class to run string as java code
 */
public class StringRunner {
    /**
     * Runs the code in string in the context where the function is called
     * @param code  the code in string
     */
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
