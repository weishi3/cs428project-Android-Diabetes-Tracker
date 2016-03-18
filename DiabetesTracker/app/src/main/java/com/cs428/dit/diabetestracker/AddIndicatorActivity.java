package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.Food;
import com.cs428.dit.diabetestracker.helpers.FoodItemLog;
import com.cs428.dit.diabetestracker.helpers.Indicator;
import com.cs428.dit.diabetestracker.helpers.IndicatorItemLog;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
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
                if (Weight_str.equals("")){
                    wei = 0.0;
                }else{
                    wei = Double.parseDouble(Weight_str);
                }

                String BloodPressure_str = bloodPressure.getText().toString();
                double bp;
                if (BloodPressure_str.equals("")){
                    bp = 0.0;
                }else{
                    bp = Double.parseDouble(BloodPressure_str);
                }

                String BloodSugar_str = bloodSugar.getText().toString();
                double bs;
                if (BloodSugar_str.equals("")){
                    bs = 0.0;
                }else{
                    bs = Double.parseDouble(BloodSugar_str);
                }

                Indicator indicator = new Indicator(bs, bp, wei);
                IndicatorItemLog indicatorItemLog = new IndicatorItemLog(day, indicator);
                Firebase mRef = new Firebase(getString(R.string.firebase_url));
                String userIndicatorURL = "userstats/"+session.getUserDetails().get(SessionManager.KEY_EMAIL);
                userIndicatorURL = userIndicatorURL.replace('.', '!');

                Log.d("USER_EMAIL", userIndicatorURL);
                mRef = mRef.child(userIndicatorURL).child(day);
                mRef.setValue(indicatorItemLog);


                Intent jumpBack = new Intent(getApplicationContext(), IndicatorLogActivity.class);
                jumpBack.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(jumpBack);
                finish();
            }
        });
    }
}