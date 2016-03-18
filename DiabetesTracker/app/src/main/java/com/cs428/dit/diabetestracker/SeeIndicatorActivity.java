package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View.OnClickListener;

import com.cs428.dit.diabetestracker.helpers.IndicatorItemLog;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.Arrays;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cs428.dit.diabetestracker.helpers.SessionManager;


import com.github.mikephil.charting.data.*;


import java.util.ArrayList;

public class SeeIndicatorActivity extends AppCompatActivity {


    LineChart weightChart,bloodSugarChart,bloodPressureChart;

    LineDataSet weightDateset,bloodSugarDataset,bloodPressureDateset;
    private SessionManager session;


    ArrayList<String> labels = new ArrayList<String>();
    ArrayList<Entry> weight = new ArrayList<>();
    ArrayList<Entry> bloodSugar = new ArrayList<>();
    ArrayList<Entry> bloodPressure = new ArrayList<>();
    LineData bloodSugarData,bloodPressureData,weightData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_indicator);
        bloodPressureChart = (LineChart) findViewById(R.id.blood_pressure);
        session = new SessionManager(this);
        bloodSugarChart = (LineChart) findViewById(R.id.blood_sugar);
        weightChart = (LineChart) findViewById(R.id.weight);
    }



    @Override
    protected void onStart() {
        super.onStart();
        String baseURL = getString(R.string.firebase_url);
        String userStatsURL = "userstats/" + session.getUserDetails().get(SessionManager.KEY_EMAIL).toString().replace('.', '!');
        userStatsURL = baseURL + userStatsURL;
        Firebase statsRef = new Firebase(userStatsURL);




        // creating list of entry

        statsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;


                for (DataSnapshot IndicatorItemLogSnapshot : dataSnapshot.getChildren()) {
                    IndicatorItemLog oneLog = IndicatorItemLogSnapshot.getValue(IndicatorItemLog.class);
                    labels.add(oneLog.getDate());

                    bloodSugar.add(new Entry((float) oneLog.getIndicator().getBloodSugar(), count));
                    bloodPressure.add(new Entry((float) oneLog.getIndicator().getBloodPressure(), count));
                    weight.add(new Entry((float) oneLog.getIndicator().getWeight(), count));
                    count = count + 1;


                }
                weightDateset = new LineDataSet(weight, "weight");
                weightDateset.setDrawCubic(true);
                weightDateset.setColor(ColorTemplate.COLORFUL_COLORS[3]);

                weightData = new LineData(labels, weightDateset);

                weightChart.setData(weightData);
                weightChart.setVisibility(View.INVISIBLE);


                bloodSugarDataset = new LineDataSet(bloodSugar, "bloodSugar");
                bloodSugarDataset.setDrawCubic(true);
                bloodSugarDataset.setColor(ColorTemplate.COLORFUL_COLORS[2]);
                bloodSugarData = new LineData(labels, bloodSugarDataset);

                bloodSugarChart.setData(bloodSugarData); // set the data and list of lables into chart
                bloodSugarChart.setVisibility(View.INVISIBLE);
                //weightChart.setDescription("Indicator Chart");  // set the description

                // set the data and list of lables into chart
                //weightChart.setDescription("Indicator Chart");  // set the description
                bloodPressureDateset = new LineDataSet(bloodPressure, "bloodPressure");
                bloodPressureDateset.setDrawCubic(true);
                bloodPressureDateset.setColor(ColorTemplate.COLORFUL_COLORS[0]);
                bloodPressureData = new LineData(labels, bloodPressureDateset);

                bloodPressureChart.setData(bloodPressureData); // set the data and list of lables into chart
                bloodPressureChart.setVisibility(View.INVISIBLE);
                //weightChart.setDescription("Indicator Chart");  // set the description


            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        final Button button2 = (Button) findViewById(R.id.Button06);
        final Button button1 = (Button) findViewById(R.id.Button05);
        final Button button = (Button) findViewById(R.id.Button04);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setBackgroundColor(Color.DKGRAY);
                button1.setBackgroundColor(Color.LTGRAY);
                button2.setBackgroundColor(Color.LTGRAY);

                weightChart.setVisibility(View.INVISIBLE);
                bloodSugarChart.setVisibility(View.INVISIBLE);
                bloodPressureChart.setVisibility(View.INVISIBLE);

                weightChart.setVisibility(View.VISIBLE);



            }
        });



        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                button1.setBackgroundColor(Color.DKGRAY);
                button.setBackgroundColor(Color.LTGRAY);
                button2.setBackgroundColor(Color.LTGRAY);
                weightChart.setVisibility(View.INVISIBLE);
                bloodSugarChart.setVisibility(View.INVISIBLE);
                bloodPressureChart.setVisibility(View.INVISIBLE);


                bloodSugarChart.setVisibility(View.VISIBLE);
            }
        });


        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                button2.setBackgroundColor(Color.DKGRAY);
                button1.setBackgroundColor(Color.LTGRAY);
                button.setBackgroundColor(Color.LTGRAY);
                weightChart.setVisibility(View.INVISIBLE);
                bloodSugarChart.setVisibility(View.INVISIBLE);
                bloodPressureChart.setVisibility(View.INVISIBLE);



                bloodPressureChart.setVisibility(View.VISIBLE);


            }
        });
    }




}
