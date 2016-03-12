package com.cs428.dit.diabetestracker;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by QiZhang on 3/9/16.
 */
public class DiabetesTrackerApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
