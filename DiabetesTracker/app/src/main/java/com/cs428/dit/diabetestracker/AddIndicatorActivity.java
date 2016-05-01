package com.cs428.dit.diabetestracker;


import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.Indicator;
import com.cs428.dit.diabetestracker.helpers.IndicatorItemLog;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * This activity add indicator into the firebase.
 * These indicators are weight, blood pressure and sugar.
 * These indicators are input by the user.
 * In one day, there will be only one set of indicators.
 * If multiple inputs are made in one day, the latest input will be presented.
 * After adding the indicators, user will be navigated to indicator log page where
 * user will be able to see all his/her data input histories.
 */
public class AddIndicatorActivity extends AppCompatActivity {

    /*
     * Weight: the textview of presenting the weight
     * bloodPressure: the textview of presenting the bloodPressure
     * bloodSugar: the textview of presenting the bloodSugar
     */
    private TextView weight, bloodPressure, bloodSugar;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_indicator);
        session = new SessionManager(this);

        weight = (TextView) findViewById(R.id.weightTextfield);
        bloodPressure = (TextView) findViewById(R.id.bloodPressureTextfield);
        bloodSugar = (TextView) findViewById(R.id.bloodSugarTextfield);

        Button save_indicator_button = (Button) findViewById(R.id.save_indicator_button);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        final String day = dateformat.format(new Date());

        save_indicator_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double userWeight = getWeight();
                if (userWeight > 625) {
                    weight.setText("");
                    weight.setHint("World Record is 625kg!");
                    weight.setHintTextColor(Color.RED);
                    return;
                }
                double userBloodPressure = getBloodPressure();
                if (userBloodPressure > 200) {
                    bloodPressure.setText("");
                    bloodPressure.setHint("Be Honest or Leave Empty!");
                    bloodPressure.setHintTextColor(Color.RED);
                    return;
                }
                double userBloodSugar = getBloodSugar();
                if (userBloodSugar > 50) {
                    bloodSugar.setText("");
                    bloodSugar.setHint("Be Honest or Leave Empty!");
                    bloodSugar.setHintTextColor(Color.RED);
                    return;
                }
                Indicator indicator = new Indicator(userBloodSugar, userBloodPressure, userWeight);
                IndicatorItemLog indicatorItemLog = new IndicatorItemLog(day, indicator);
                Firebase mRef = new Firebase(getString(R.string.firebase_url));
                String userIndicatorURL = "userstats/" + session.getUserDetails().get(SessionManager.KEY_EMAIL);
                userIndicatorURL = userIndicatorURL.replace('.', '!');
                mRef = mRef.child(userIndicatorURL).child(day);
                mRef.setValue(indicatorItemLog);

                Intent jumpBack = new Intent(getApplicationContext(), IndicatorLogActivity.class);
                jumpBack.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(jumpBack);
                finish();
            }
        });
    }

    /*
     * Get user's blood sugar from user input
     */
    public double getBloodSugar() {
        double myBloodSugar;
        String BloodSugar_str = bloodSugar.getText().toString();
        if (BloodSugar_str.equals("")) {
            myBloodSugar = 0.0;
        } else {
            myBloodSugar = Double.parseDouble(BloodSugar_str);
        }
        return myBloodSugar;
    }

    /*
     * Get user's blood pressure from user input
     */
    public double getBloodPressure() {
        double myBloodPressure;
        String BloodPressure_str = bloodPressure.getText().toString();
        if (BloodPressure_str.equals("")) {
            myBloodPressure = 0.0;
        } else {
            myBloodPressure = Double.parseDouble(BloodPressure_str);
        }
        return myBloodPressure;
    }

    /*
     * Get user's weight from user input
     */
    public double getWeight() {
        double myWeight;
        String Weight_str = weight.getText().toString();
        if (Weight_str.equals("")) {
            myWeight = 0.0;
        } else {
            myWeight = Double.parseDouble(Weight_str);
        }
        return myWeight;
    }

}