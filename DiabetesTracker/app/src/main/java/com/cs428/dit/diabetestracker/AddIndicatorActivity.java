package com.cs428.dit.diabetestracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.cs428.dit.diabetestracker.helpers.Food;
import com.cs428.dit.diabetestracker.helpers.FoodItemLog;
import com.cs428.dit.diabetestracker.helpers.Indicator;
import com.cs428.dit.diabetestracker.helpers.IndicatorItemLog;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AddIndicatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_indicator);
        final TextView weight = (TextView) findViewById(R.id.weightTextfield);
        final TextView bloodPressure = (TextView) findViewById(R.id.bloodPressureTextfield);
        final TextView bloodSugar = (TextView) findViewById(R.id.bloodSugarTextfield);

        Button btn = (Button) findViewById(R.id.btn_save_indicator);
        final SessionManager session = new SessionManager(this);

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        final String day = dateformat.format(new Date());



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set empty fields to default value;
                String Weight_str = weight.getText().toString();
                double wei;
                if (Weight_str.equals("")) {
                    wei = 0.0;
                } else {
                    wei = Double.parseDouble(Weight_str);
                }

                if (wei > 625) {
                    weight.setText("");
                    weight.setHint("World Record is 625kg!");
                    weight.setHintTextColor(Color.RED);
                   return;
                }

                String BloodPressure_str = bloodPressure.getText().toString();
                double bp;
                if (BloodPressure_str.equals("")) {
                    bp = 0.0;
                } else {
                    bp = Double.parseDouble(BloodPressure_str);
                }

                if (bp > 200) {
                    bloodPressure.setText("");
                    bloodPressure.setHint("Be Honest or Leave Empty!");
                    bloodPressure.setHintTextColor(Color.RED);
                    return;

                }

                String BloodSugar_str = bloodSugar.getText().toString();
                double bs;
                if (BloodSugar_str.equals("")) {
                    bs = 0.0;
                } else {
                    bs = Double.parseDouble(BloodSugar_str);
                }

                if (bs > 50) {
                    bloodSugar.setText("");
                    bloodSugar.setHint("Be Honest or Leave Empty!");
                    bloodSugar.setHintTextColor(Color.RED);
                    return;

                }

                Indicator indicator = new Indicator(bs, bp, wei);
                IndicatorItemLog indicatorItemLog = new IndicatorItemLog(day, indicator);
                Firebase mRef = new Firebase(getString(R.string.firebase_url));
                String userIndicatorURL = "userstats/" + session.getUserDetails().get(SessionManager.KEY_EMAIL);
                userIndicatorURL = userIndicatorURL.replace('.', '!');

                Log.d("USER_EMAIL", userIndicatorURL);
                mRef = mRef.child(userIndicatorURL).child(day);
                mRef.setValue(indicatorItemLog);


                Intent jumpBack = new Intent(getApplicationContext(), IndicatorLogActivity.class);
                jumpBack.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(jumpBack);
                finish();
            }

            private void onCreateDialog(Bundle bundle) {
            }
        });



    }


    
}