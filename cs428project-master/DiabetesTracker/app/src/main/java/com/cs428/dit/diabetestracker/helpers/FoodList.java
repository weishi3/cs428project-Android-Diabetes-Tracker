package com.cs428.dit.diabetestracker.helpers;


import android.content.Context;

import com.firebase.client.Firebase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodList {
    /**
     * Given the android context con,the user email,and the comma seperated string food list, this function will save the list along with the time in firebase
     *
     * @param con   the android context
     * @param email the email of the user we are saving the foodlist for
     * @param list  the com
     */
    public static void saveFoodList(Context con, String email, String list) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String d = dateFormat.format(date);
        Firebase.setAndroidContext(con);
        Firebase base = new Firebase("https://brilliant-fire-9755.firebaseio.com");
        Firebase user = base.child("users").child(email);
        Firebase newList = user.child("foodlist").push();
        newList.setValue(d + "," + list);
    }
}
