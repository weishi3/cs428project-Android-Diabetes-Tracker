package com.cs428.dit.diabetestracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.cs428.dit.diabetestracker.helpers.FoodItemLog;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SeeCaloriesActivity extends AppCompatActivity {

    LineChart lineChart;
    HashMap<String, Double> mapped_keys;
    LineDataSet dataset;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_colaries);
        lineChart = (LineChart) findViewById(R.id.calories_chart);
        session = new SessionManager(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        String baseURL = getString(R.string.firebase_url);
        String userStatsURL = "stats/" + session.getUserDetails().get(SessionManager.KEY_EMAIL).toString().replace('.', '!');
        userStatsURL = baseURL + userStatsURL;
        Firebase statsRef = new Firebase(userStatsURL);


        // creating list of entry

        statsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mapped_keys = new HashMap<>();

                for (DataSnapshot foodItemLogSnapshot : dataSnapshot.getChildren()) {
                    FoodItemLog oneLog = foodItemLogSnapshot.getValue(FoodItemLog.class);

                    if (mapped_keys.get(oneLog.getDate()) == null) {

                        mapped_keys.put((oneLog.getDate()),
                                new Double(oneLog.getFood().getKilocalorie()));
                    } else {


                        mapped_keys.put((oneLog.getDate()),
                                mapped_keys.get(oneLog.getDate()) +
                                        new Double(oneLog.getFood().getKilocalorie())
                        );
                    }

                }

                Object[] keys = mapped_keys.keySet().toArray();
                Log.d("KEYS LEN", keys.length + "");
                String[] keys_str = Arrays.copyOf(keys, keys.length, String[].class);

                ArrayList<String> labels = new ArrayList<String>();
                //title for the chart
                Arrays.sort(keys_str);
                Log.d("str LEN", keys_str.length + "");


                ArrayList<Entry> entries = new ArrayList<>();
                int i = 0;
                for (String elem : keys_str) {

                    entries.add(new Entry(
                            ((float) ((double) mapped_keys.get(elem)))
                            , i));

                    labels.add(elem);
                    i++;

                }

                if(keys_str.length > 0 ){
                    Log.d("CALORIES=", ((float) ((double) mapped_keys.get(keys_str[0]))) + "");
                    Log.d("entry: ", entries.get(0) + "");
                    dataset = new LineDataSet(entries, "total Calories");
                    Log.d("dataset: ", dataset + "");
                    LineData data = new LineData(labels, dataset);
                    lineChart.setData(data);
                    lineChart.setDescription("Daily Calories Report");
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
