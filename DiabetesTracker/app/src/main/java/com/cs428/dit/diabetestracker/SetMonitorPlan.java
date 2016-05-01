package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.MonitorSetting;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.Firebase;

import java.util.ArrayList;

/*
 * This activity is to
 */
public class SetMonitorPlan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_monitor_plan);
        final TextView period = (TextView) findViewById(R.id.period);

        Button btn = (Button) findViewById(R.id.submitSetting);
        final SessionManager session = new SessionManager(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase mRef = new Firebase(getString(R.string.firebase_url));
                String userSettingURL = "monitorsetting/" + session.getUserDetails().get(SessionManager.KEY_EMAIL);
                userSettingURL = userSettingURL.replace('.', '!');


                String displayed = "bloodSugar";
                RadioButton cbs = (RadioButton) findViewById(R.id.cbs);
                if (cbs.isChecked()) {
                    displayed = "bloodSugar";
                }
                RadioButton cbp = (RadioButton) findViewById(R.id.cbp);
                if (cbp.isChecked()) {
                    displayed = "bloodPressure";
                }
                RadioButton cw = (RadioButton) findViewById(R.id.cw);
                if (cw.isChecked()) {
                    displayed = "weight";
                }

                ArrayList<String> warning = new ArrayList();
                CheckBox c1 = (CheckBox) findViewById(R.id.checkBox1);
                if (c1.isChecked()) {
                    warning.add("bloodSugar");
                }
                CheckBox c2 = (CheckBox) findViewById(R.id.checkBox2);
                if (c2.isChecked()) {
                    warning.add("bloodPressure");
                }

                String periods = period.getText().toString();
                int periodd;
                if (periods.equals("")) {
                    periodd = 3;
                } else {
                    periodd = Integer.parseInt(periods);
                }
                MonitorSetting ms = new MonitorSetting(periodd, displayed, warning);
                mRef = mRef.child(userSettingURL);
                mRef.setValue(ms);


                Intent jumpBack = new Intent(getApplicationContext(), MainActivity.class);
                jumpBack.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(jumpBack);
                finish();


            }
        });

    }

    public void onRadioButtonClicked(View view) {

    }

}
