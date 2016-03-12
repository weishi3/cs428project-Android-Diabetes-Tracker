package com.cs428.dit.diabetestracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cs428.dit.diabetestracker.helpers.SessionManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class SeeIndicatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_indicator);
        final SessionManager session = new SessionManager(this);

        // labels for each chart
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        // blood pressure
        ArrayList<Entry> bloodPressure = new ArrayList<>();
        bloodPressure.add(new Entry(4f, 0));
        bloodPressure.add(new Entry(8f, 1));
        bloodPressure.add(new Entry(6f, 2));
        bloodPressure.add(new Entry(2f, 3));
        bloodPressure.add(new Entry(18f, 4));
        bloodPressure.add(new Entry(9f, 5));

        LineDataSet bloodPressureDataset = new LineDataSet(bloodPressure, "# of Calls");
        bloodPressureDataset.setDrawCubic(true);
        bloodPressureDataset.setColor(ColorTemplate.COLORFUL_COLORS[0]);


        LineChart bloodPressureChart = (LineChart) findViewById(R.id.blood_pressure);
        LineData bloodPressureData = new LineData(labels, bloodPressureDataset);
        bloodPressureChart.setData(bloodPressureData); // set the data and list of lables into chart

        // blood sugar
        ArrayList<Entry> bloodSugar = new ArrayList<>();

        bloodSugar.add(new Entry(8f, 0));
        bloodSugar.add(new Entry(6f, 1));
        bloodSugar.add(new Entry(2f, 2));
        bloodSugar.add(new Entry(18f, 3));
        bloodSugar.add(new Entry(9f, 4));
        bloodSugar.add(new Entry(4f, 5));

        LineDataSet bloodSugarDataset = new LineDataSet(bloodSugar, "# of Calls");
        bloodSugarDataset.setDrawCubic(true);
        bloodSugarDataset.setColor(ColorTemplate.COLORFUL_COLORS[2]);


        LineChart bloodSugarChart = (LineChart) findViewById(R.id.blood_sugar);
        LineData bloodSugarData = new LineData(labels, bloodSugarDataset);
        bloodSugarChart.setData(bloodSugarData); // set the data and list of lables into chart
        //bloodSugarChart.setDescription("Indicator Chart");  // set the description


        ArrayList<Entry> weight = new ArrayList<>();

        weight.add(new Entry(9f, 0));
        weight.add(new Entry(4f, 1));
        weight.add(new Entry(8f, 2));
        weight.add(new Entry(6f, 3));
        weight.add(new Entry(2f, 4));
        weight.add(new Entry(18f, 5));


        LineDataSet weightDataset = new LineDataSet(weight, "# of Calls");
        weightDataset.setDrawCubic(true);
        weightDataset.setColor(ColorTemplate.COLORFUL_COLORS[3]);


        LineChart weightChart = (LineChart) findViewById(R.id.weight);
        LineData weightData = new LineData(labels, weightDataset);
        weightChart.setData(weightData); // set the data and list of lables into chart
        //weightChart.setDescription("Indicator Chart");  // set the description
    }
}
