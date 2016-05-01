package com.cs428.dit.diabetestracker.helpers;

/**
 * Created by mrodda on 4/23/2016.
 * A class that implements two interfaces to allow for comparing in other methods
 */
public class Restriction {

    /*
    * Allows a method to place a restriction on an integer
     */
    public interface int_restriction {
        boolean call(int param);
    }

    /*
    * Allows a method to place a restriction on a double
     */
    public interface dbl_restriction {
        boolean call(double param);
    }

    private dbl_restriction dbl_no_restriction;
    private int_restriction int_no_restriction;

    /*
    * Public constructor
     */
    public Restriction() {
        dbl_no_restriction = new dbl_restriction() {
            @Override
            public boolean call(double param) {
                return false;
            }
        };

        int_no_restriction = new int_restriction() {
            @Override
            public boolean call(int param) {
                return false;
            }
        };
    }

    /**
     *
     * @return the default dbl_restriction that will always return false
     */
    public dbl_restriction get_false_dbl(){
        return dbl_no_restriction;
    }

    /**
    * @return the default int_restriction that will always return false
     */
    public int_restriction get_false_int(){
        return int_no_restriction;
    }
}
