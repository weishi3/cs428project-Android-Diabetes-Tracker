package com.cs428.dit.diabetestracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.CircleProgress;

public class StepCounterActivity extends AppCompatActivity implements SensorEventListener {
    // sharedpref file name
    private static final String PREF_NAME = "StepPref";
    private static final String LastCount = "LastCount";
    // Shared preferences
    private SharedPreferences pref;
    // Editor for shared preferences
    private SharedPreferences.Editor editor;

    private SensorManager sensorManager;
    boolean activityRunning;
    private int steps;
    private int lastCount;
    private CircleProgress mStepProgress;
    private final int MAXSTEP = 20000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);

        mStepProgress = (CircleProgress) findViewById(R.id.step_circle_progress);
        mStepProgress.setMax(MAXSTEP);
        mStepProgress.setSuffixText("");




        pref = getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();

        lastCount = pref.getInt(LastCount, 0);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        recordLastCount();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            mStepProgress.setProgress((int) event.values[0] - lastCount);
            steps = (int) event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(this);
        recordLastCount();
        super.onDestroy();
    }

    /**
     * Record last count so that every time step counter starts counting from 0
     */
    private void recordLastCount() {
        editor.putInt(LastCount, steps);
        editor.commit();
    }
}
