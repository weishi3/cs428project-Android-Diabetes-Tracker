package com.cs428.dit.diabetestracker.helpers;


import android.content.Context;
import com.firebase.client.Firebase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodList {


    public static void add(Context con,String email,String list){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String d = dateFormat.format(date); //2014/08/06 15:59:48
        Firebase.setAndroidContext(con);
        Firebase base = new Firebase("https://brilliant-fire-9755.firebaseio.com");
        Firebase user = base.child("users").child(email);
        Firebase newlist = user.child("foodlist").push();
        newlist.setValue(d+","+list);
    }
}
