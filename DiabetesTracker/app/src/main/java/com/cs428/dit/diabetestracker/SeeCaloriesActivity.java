package com.cs428.dit.diabetestracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SeeCaloriesActivity extends AppCompatActivity {
    public static final int DURATION_MILLIS_Y = 1000;
    public static final int DURATION_MILLIS_X = 1000;
    private SessionManager session;
    LineChart lineChart;
    HashMap<String, Double> mappedKeys;
    LineDataSet dataset;

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
        String userStatsURL = getString(R.string.food_item_storage_endpoint) + session.getUserDetails().get(SessionManager.KEY_EMAIL).toString().replace('.', '!');
        userStatsURL = baseURL + userStatsURL;
        Firebase statsRef = new Firebase(userStatsURL);

        // Creating list of entry
        statsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mappedKeys = new HashMap<>();
                // Get a hash table of a list of food items from Firebase
                for (DataSnapshot foodItemLogSnapshot : dataSnapshot.getChildren()) {
                    FoodItemLog oneLog = foodItemLogSnapshot.getValue(FoodItemLog.class);
                    if (mappedKeys.get(oneLog.getDate()) == null) {
                        mappedKeys.put((oneLog.getDate()),
                                new Double(oneLog.getFood().getKilocalorie()));
                    } else {
                        mappedKeys.put((oneLog.getDate()),
                                mappedKeys.get(oneLog.getDate()) +
                                        new Double(oneLog.getFood().getKilocalorie())
                        );
                    }
                }

                // Extract date and calories from Hashtable to CaloriesChartData Class
                CaloriesChartData caloriesChartData = new CaloriesChartData().invoke();
                String[] keysStr = caloriesChartData.getKeysStr();
                ArrayList<String> labels = caloriesChartData.getLabels();
                ArrayList<Entry> entries = caloriesChartData.getEntries();

                displayChart(keysStr, labels, entries);
            }

            /**
             * Given date and calories values, draw linechart
             * @param keys_str dates represented as strings
             * @param labels date labels
             * @param entries a list of calories values
             */
            private void displayChart(String[] keys_str, ArrayList<String> labels, ArrayList<Entry> entries) {
                if (keys_str.length > 0) {
                    // Group data into dataset, a data structure recognized by Chart Library
                    dataset = new LineDataSet(entries, getString(R.string.calories_chart_calories_legend));
                    dataset.setDrawCubic(true);
                    dataset.setDrawFilled(true);
                    dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    // Populate the data on the Chart
                    LineData data = new LineData(labels, dataset);
                    lineChart.setData(data);
                    lineChart.setDescription(getString(R.string.calories_chart_title));
                    lineChart.animateXY(DURATION_MILLIS_X, DURATION_MILLIS_Y);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    /**
     * This Class is used to decode (date, calories) hashtable extracted from Firebase
     */
    private class CaloriesChartData {
        public static final int DATASTARTINDEX = 0;
        private String[] keysStr;
        private ArrayList<String> labels;
        private ArrayList<Entry> entries;

        public String[] getKeysStr() {
            return keysStr;
        }

        public ArrayList<String> getLabels() {
            return labels;
        }

        public ArrayList<Entry> getEntries() {
            return entries;
        }

        public CaloriesChartData invoke() {
            Object[] keys = mappedKeys.keySet().toArray();
            keysStr = Arrays.copyOf(keys, keys.length, String[].class);
            labels = new ArrayList<String>();
            Arrays.sort(keysStr);
            entries = new ArrayList<>();
            int item_index = DATASTARTINDEX;
            for (String elem : keysStr) {
                entries.add(new Entry(
                        ((float) ((double) mappedKeys.get(elem)))
                        , item_index));
                labels.add(elem);
                item_index++;
            }
            return this;
        }
    }
}
