package com.cs428.dit.diabetestracker.helpers;

/**
 * Created by mrodda on 4/23/2016.
 */
public class Restriction {

    public interface int_restriction {
        boolean call(int param);
    }

    public interface dbl_restriction {
        boolean call(double param);
    }

    private dbl_restriction dbl_no_restriction;
    private int_restriction int_no_restriction;

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

    public dbl_restriction get_false_dbl(){
        return dbl_no_restriction;
    }

    public int_restriction get_false_int(){
        return int_no_restriction;
    }
}
