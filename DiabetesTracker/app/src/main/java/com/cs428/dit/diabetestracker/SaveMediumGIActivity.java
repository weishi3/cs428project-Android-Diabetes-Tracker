package com.cs428.dit.diabetestracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import com.cs428.dit.diabetestracker.helpers.FoodList;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import android.util.Log;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import com.firebase.client.Firebase;

import java.util.HashMap;

public class SaveMediumGIActivity extends AppCompatActivity {
    private SessionManager session;
    private HashMap<String, Object> userDetails;

    private boolean riceNoodles = false;
    private boolean sweetCorn = false;
    private boolean lentils = false;
    private boolean beans = false;
    private boolean yogurt = false;
    private boolean greekYogurt = false;
    private boolean plums = false;
    private boolean oranges = false;
    private Context ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_diet);
        session = new SessionManager(getApplicationContext());
        Firebase.setAndroidContext(this);
        userDetails = session.getUserDetails();
        ac = this;
        setButton(R.id.riceNoodlesTrue);
        setButton(R.id.sweetCornTrue);
        setButton(R.id.lentilsTrue);
        setButton(R.id.beansTrue);
        setButton(R.id.yogurtTrue);
        setButton(R.id.greekYogurtTrue);
        setButton(R.id.plumsTrue);
        setButton(R.id.orangesTrue);
        Button submitBtn = (Button) findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                riceNoodles = false;
                RadioButton riceNoodlesTrue = (RadioButton) findViewById(R.id.riceNoodlesTrue);
                if (riceNoodlesTrue.isChecked()) {
                    riceNoodles = true;
                }
                sweetCorn = false;
                RadioButton  sweetCornTrue= (RadioButton) findViewById(R.id.sweetCornTrue);
                if (sweetCornTrue.isChecked()) {
                    sweetCorn = true;
                }
                lentils = false;
                RadioButton lentilsTrue = (RadioButton) findViewById(R.id.lentilsTrue);
                if (lentilsTrue.isChecked()) {
                    lentils = true;
                }
                beans = false;
                RadioButton beansTrue = (RadioButton) findViewById(R.id.beansTrue);
                if (beansTrue.isChecked()) {
                    beans = true;
                }
                yogurt = false;
                RadioButton yogurtTrue = (RadioButton) findViewById(R.id.yogurtTrue);
                if (yogurtTrue.isChecked()) {
                    yogurt = true;
                }
                greekYogurt = false;
                RadioButton greekYogurtTrue = (RadioButton) findViewById(R.id.greekYogurtTrue);
                if (greekYogurtTrue.isChecked()) {
                    greekYogurt = true;
                }
                plums = false;
                RadioButton plumsTrue = (RadioButton) findViewById(R.id.plumsTrue);
                if (plumsTrue.isChecked()) {
                    plums = true;
                }
                oranges = false;
                RadioButton orangesTrue = (RadioButton) findViewById(R.id.orangesTrue);
                if (orangesTrue.isChecked()) {
                    oranges = true;
                }

                String foodList = "";

                if (riceNoodles) {
                    foodList += "Rice noodles,";
                }
                if (sweetCorn) {
                    foodList += "Sweet corn,";
                }
                if (lentils) {
                    foodList += "Lentils,";
                }
                if (beans) {
                    foodList += "Beans,";
                }
                if (yogurt) {
                    foodList += "Yogurt,";
                }
                if (greekYogurt) {
                    foodList += "Greek Yogurt,";
                }
                if (plums) {
                    foodList += "Plums,";
                }
                if (oranges) {
                    foodList += "Oranges,";
                }

                String userEmail = userDetails.get("email").toString().replace('.', '!');

                Log.d("tag", foodList);
                FoodList.add(ac, userEmail, foodList);

                Intent dietLow = new Intent(getApplicationContext(), DietLow.class);
                startActivity(dietLow);
            }
        });

    }


    private void setButton(int idTrue) {
        RadioButton t = (RadioButton) findViewById(idTrue);
        t.setChecked(true);
    }

    public void onRadioButtonClicked(View view) {

    }
}
