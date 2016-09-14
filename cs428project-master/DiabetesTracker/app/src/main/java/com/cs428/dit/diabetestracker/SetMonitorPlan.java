package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.MonitorSetting;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/*
 * This activity is to set the monitor plan for alert message
 * User will be able to select the
 * monitor type: Weight, blood sugar and blood pressure
 * monitor period: After how many unhealthy days does the user want to be alerted
 */
public class SetMonitorPlan extends AppCompatActivity {

    /*
     * Period: After how many unhealthy days does the user want to be alerted
     * session: The session object which contains the data in firebase
     * submitSetting: The button which will submit the data to the firebase
     */
    TextView period;
    Button submitSetting;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_monitor_plan);
        period = (TextView) findViewById(R.id.period);
        submitSetting = (Button) findViewById(R.id.submitSetting);
        session = new SessionManager(this);

        String baseURL = getString(R.string.firebase_url);
        String userStatsURL = "monitorsetting/" + session.getUserDetails().get(SessionManager.KEY_EMAIL).toString().replace('.', '!');
        userStatsURL = baseURL + userStatsURL;
        Firebase statsRef = new Firebase(userStatsURL);
        // creating list of entry
        statsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MonitorSetting oneSetting= dataSnapshot.getValue(MonitorSetting.class);
                int thisDays= oneSetting.getNumDaysMonitor();

                String displayType= oneSetting.getIndicatorType();
                ArrayList<String> msg=oneSetting.getWarningMessage();
                period.setText(Integer.toString(thisDays));
                RadioButton select_blood_sugar = (RadioButton) findViewById(R.id.select_blood_sugar);
                RadioButton select_blood_pressure = (RadioButton) findViewById(R.id.select_blood_pressure);
                RadioButton select_weight = (RadioButton) findViewById(R.id.select_weight);
                if (displayType.equals("weight"))
                    select_weight.setChecked(true);
                if (displayType.equals("bloodSugar"))
                    select_blood_sugar.setChecked(true);
                if (displayType.equals("bloodPressure"))
                    select_blood_pressure.setChecked(true);
                CheckBox blood_sugar_check_box = (CheckBox) findViewById(R.id.blood_sugar_check_box);
                CheckBox blood_pressure_check_box = (CheckBox) findViewById(R.id.blood_pressure_check_box);
                if (msg.contains("bloodSugar"))
                    blood_sugar_check_box.setChecked(true);
                if (msg.contains("bloodPressure"))
                    blood_pressure_check_box.setChecked(true);

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        /*
         * Set the onclickerlistener for submit the setting
         */
        submitSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase mRef = new Firebase(getString(R.string.firebase_url));
                String userSettingURL = "monitorsetting/" + session.getUserDetails().get(SessionManager.KEY_EMAIL);
                userSettingURL = userSettingURL.replace('.', '!');

                String displayed = checkRadioButton();
                ArrayList<String> warning = addWarningMessage();
                int user_period = getUser_period();

                MonitorSetting ms = new MonitorSetting(user_period, displayed, warning);
                mRef = mRef.child(userSettingURL);
                mRef.setValue(ms);

                Intent jumpBack = new Intent(getApplicationContext(), MainActivity.class);
                jumpBack.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(jumpBack);
                finish();
            }
        });
    }

    /*
     * Add warning message based on the checkbox that user checked
     */
    @NonNull
    public ArrayList<String> addWarningMessage() {
        ArrayList<String> warning = new ArrayList();
        CheckBox c1 = (CheckBox) findViewById(R.id.blood_sugar_check_box);
        if (c1.isChecked()) {
            warning.add("bloodSugar");
        }
        CheckBox c2 = (CheckBox) findViewById(R.id.blood_pressure_check_box);
        if (c2.isChecked()) {
            warning.add("bloodPressure");
        }
        return warning;
    }

    /*
     * Get the alert period set by the user
     */
    public int getUser_period() {
        int user_period;
        String periods = period.getText().toString();

        if (periods.equals("")) {
            user_period = 3;
        } else {
            user_period = Integer.parseInt(periods);
        }
        return user_period;
    }

    /*
     * Show displayed string based on the button clicked
     */
    @NonNull
    public String checkRadioButton() {
        String displayed = "bloodSugar";
        RadioButton cbs = (RadioButton) findViewById(R.id.select_blood_sugar);
        if (cbs.isChecked()) {
            displayed = "bloodSugar";
        }
        RadioButton cbp = (RadioButton) findViewById(R.id.select_blood_pressure);
        if (cbp.isChecked()) {
            displayed = "bloodPressure";
        }
        RadioButton cw = (RadioButton) findViewById(R.id.select_weight);
        if (cw.isChecked()) {
            displayed = "weight";
        }
        return displayed;
    }

    public void onRadioButtonClicked(View view) {

    }

}
