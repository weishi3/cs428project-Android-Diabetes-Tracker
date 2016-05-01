package com.cs428.dit.diabetestracker;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/*
 * This activity allows user to see the indicator history in a graph version
 * Use will be able to click the button and view the corresponding graph
 */
public class SeeIndicatorActivity extends AppCompatActivity {

    /*
     * session: The session object which contains the data in firebase
     * weightChart: The chart which shows the weight history
     * bloodSugarChart: The chart which shows the blood sugar history
     * bloodPressureChart: The chart which shows the blood pressure history
     * weightDataset: The dataset contains the user's weight history data
     * bloodSugarDataset: The dataset contains the user's blood sugar history data
     * bloodPressureDataset: The dataset contains the user's blood pressure history data
     * Weight_button: The button which shows the weight graph when clicked
     * BloodSugar_button: The button which shows the blood sugar graph when clicked
     * BloodPressure_button: The button which shows the blood sugar graph when clicked
     */
    private SessionManager session;
    LineChart weightChart, bloodSugarChart, bloodPressureChart;
    LineDataSet weightDataset, bloodSugarDataset, bloodPressureDataset;
    Button Weight_button, BloodSugar_button, BloodPressure_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(this);
        setContentView(R.layout.activity_see_indicator);
        weightChart = (LineChart) findViewById(R.id.weight);
        bloodSugarChart = (LineChart) findViewById(R.id.blood_sugar);
        bloodPressureChart = (LineChart) findViewById(R.id.blood_pressure);
        Weight_button = (Button) findViewById(R.id.Weight_button);
        BloodSugar_button = (Button) findViewById(R.id.BloodSugar_button);
        BloodPressure_button = (Button) findViewById(R.id.BloodPressure_button);
    }

    /*
     * This function will process data from firebase and
     * get all the corresponding data related to the user
     */
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
                ArrayList<String> labels = new ArrayList<String>();
                ArrayList<Entry> weight = new ArrayList<>();
                ArrayList<Entry> bloodSugar = new ArrayList<>();
                ArrayList<Entry> bloodPressure = new ArrayList<>();
                for (DataSnapshot IndicatorItemLogSnapshot : dataSnapshot.getChildren()) {
                    IndicatorItemLog oneLog = IndicatorItemLogSnapshot.getValue(IndicatorItemLog.class);
                    labels.add(oneLog.getDate());
                    bloodSugar.add(new Entry((float) oneLog.getIndicator().getBloodSugar(), count));
                    bloodPressure.add(new Entry((float) oneLog.getIndicator().getBloodPressure(), count));
                    weight.add(new Entry((float) oneLog.getIndicator().getWeight(), count));
                    count = count + 1;
                }
                weightchart(labels, weight);
                bloodSugarchart(labels, bloodSugar);
                bloodPressurechart(labels, bloodPressure);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        /*
         * Setting corresponding onclick listener for
         * weight button, blood sugar button and blood pressure button
         */
        Weight_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Weight_button.setBackgroundColor(Color.CYAN);
                BloodSugar_button.setBackgroundColor(Color.LTGRAY);
                BloodPressure_button.setBackgroundColor(Color.LTGRAY);
                setInvisibility();
                weightChart.setVisibility(View.VISIBLE);

            }
        });

        BloodSugar_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BloodSugar_button.setBackgroundColor(Color.CYAN);
                Weight_button.setBackgroundColor(Color.LTGRAY);
                BloodPressure_button.setBackgroundColor(Color.LTGRAY);
                setInvisibility();
                bloodSugarChart.setVisibility(View.VISIBLE);
            }
        });

        BloodPressure_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BloodPressure_button.setBackgroundColor(Color.CYAN);
                BloodSugar_button.setBackgroundColor(Color.LTGRAY);
                Weight_button.setBackgroundColor(Color.LTGRAY);
                setInvisibility();
                bloodPressureChart.setVisibility(View.VISIBLE);
            }
        });
    }

    /*
     * Set all the charts to be invisible
     */
    public void setInvisibility() {
        weightChart.setVisibility(View.INVISIBLE);
        bloodSugarChart.setVisibility(View.INVISIBLE);
        bloodPressureChart.setVisibility(View.INVISIBLE);
    }

    /*
     * Create the line chart based on the dataset for
     * weight, blood sugar and blood pressure
     */

    public void bloodPressurechart(ArrayList<String> labels, ArrayList<Entry> bloodPressure) {
        bloodPressureDataset = new LineDataSet(bloodPressure, "bloodPressure");
        bloodPressureDataset.setDrawCubic(true);
        bloodPressureDataset.setColor(ColorTemplate.COLORFUL_COLORS[0]);
        LineData bloodPressureData = new LineData(labels, bloodPressureDataset);
        // set the data and list of lables into chart
        bloodPressureChart.setData(bloodPressureData);
        bloodPressureChart.setVisibility(View.INVISIBLE);
    }

    public void bloodSugarchart(ArrayList<String> labels, ArrayList<Entry> bloodSugar) {
        bloodSugarDataset = new LineDataSet(bloodSugar, "bloodSugar");
        bloodSugarDataset.setDrawCubic(true);
        bloodSugarDataset.setColor(ColorTemplate.COLORFUL_COLORS[2]);
        LineData bloodSugarData = new LineData(labels, bloodSugarDataset);
        // set the data and list of lables into chart
        bloodSugarChart.setData(bloodSugarData);
        bloodSugarChart.setVisibility(View.INVISIBLE);
    }

    public void weightchart(ArrayList<String> labels, ArrayList<Entry> weight) {
        weightDataset = new LineDataSet(weight, "weight");
        weightDataset.setDrawCubic(true);
        weightDataset.setColor(ColorTemplate.COLORFUL_COLORS[3]);
        LineData weightData = new LineData(labels, weightDataset);
        // set the data and list of lables into chart
        weightChart.setData(weightData);
        weightChart.setVisibility(View.INVISIBLE);
    }

}
