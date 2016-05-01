package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * A splash screen that shows the app logo.
 */
public class SplashActivity extends AppCompatActivity {
    // Define how long the splash screen is shown
    public static final int DELAYTIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int delayTime = DELAYTIME;
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(delayTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // Go to Activity Login
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(login);
                    finish();
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
