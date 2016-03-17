package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.Food;
import com.cs428.dit.diabetestracker.helpers.FoodItemLog;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddFoodItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);
        final TextView food = (TextView) findViewById(R.id.foodNameTextfield);
        final TextView foodCalories = (TextView) findViewById(R.id.foodCaloriesTextfield);
        final TextView sugarInGramsText = (TextView) findViewById(R.id.sugarInGramsTextfield);
        Button btn = (Button) findViewById(R.id.btn_save_food_item);
        final SessionManager session = new SessionManager(this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set empty fields to default value;
                String Calories_str = foodCalories.getText() + "";
                double Cal;
                if (Calories_str.equals("")) {
                    Cal = 0.0;
                } else {
                    Cal = Double.parseDouble(Calories_str);
                }

                String Sugar_str = sugarInGramsText.getText() + "";
                double Sugar;
                if (Sugar_str.equals("")) {
                    Sugar = 0.0;
                } else {
                    Sugar = Double.parseDouble(Sugar_str);
                }

                Food f;

                if ((food.getText() + "").trim().equals("")) {
                    f = new Food(Sugar, Cal, "item_name_not_entered");

                } else {
                    f = new Food(Sugar, Cal, food.getText() + "");
                }
                //Parse the current date into string format for storage
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
                final String day = dateformat.format(new Date());
                FoodItemLog d = new FoodItemLog(day, f);
                Firebase mRef = new Firebase(getString(R.string.firebase_url));
                String userStatsURL = "foodStats/"+session.getUserDetails().get(SessionManager.KEY_EMAIL);
                userStatsURL = userStatsURL.replace('.', '!');

                Log.d("USER_EMAIL", userStatsURL);
                Log.d("DATE FORMAT",day);

                mRef = mRef.child(userStatsURL);
                mRef.push().setValue(d);

//                long currentTime = System.currentTimeMillis();
//                String currentTimeStr = currentTime+"";
//                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
//                final String date = dateformat.format(new Date());
//                Firebase mRef = new Firebase(getString(R.string.firebase_url));
//                String userStatsURL = "food/" + session.getUserDetails().get(SessionManager.KEY_EMAIL);
//                userStatsURL = userStatsURL.replace('.', '!');
//
//                //The key of one food item is currentTime in milliseconds. No food name contained.
//                //In case bad characters occur in the name causing Firebase errors.
//
//                Firebase currentTimeRef = mRef.child(userStatsURL).child(date).child(currentTimeStr);
//                currentTimeRef.setValue(f);

                //Go back to food item log page.
                Intent foodLog = new Intent(getApplicationContext(), FoodLogActivity.class);
                foodLog.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(foodLog);
                finish();

            }
        });
    }


}
