package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.Food;
import com.cs428.dit.diabetestracker.helpers.FoodItemLog;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This activity is the Add Food Item page where users can log a food item.
 */
public class AddFoodItemActivity extends AppCompatActivity {
    public static final double CALORIESEMPTY = 0.0;
    public static final double SUGAREMPTY = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);
        final TextView foodText = (TextView) findViewById(R.id.foodNameTextfield);
        final TextView foodCalories = (TextView) findViewById(R.id.foodCaloriesTextfield);
        final TextView sugarInGramsText = (TextView) findViewById(R.id.sugarInGramsTextfield);
        Button saveFoodButton = (Button) findViewById(R.id.btn_save_food_item);
        final SessionManager session = new SessionManager(this);

        saveFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double cal = getCalories(foodCalories);
                double sugar = getSugar(sugarInGramsText);
                Food food = setFood(cal, sugar, foodText);

                // Parse the current date into string format for storage
                SimpleDateFormat dateformat = new SimpleDateFormat(getString(R.string.food_log_date_format));
                final String day = dateformat.format(new Date());

                // Store the food item in Firebase
                FoodItemLog d = new FoodItemLog(day, food);
                Firebase mRef = new Firebase(getString(R.string.firebase_url));
                String userStatsURL = getString(R.string.food_item_storage_endpoint) + session.getUserDetails().get(SessionManager.KEY_EMAIL);
                userStatsURL = userStatsURL.replace('.', '!');
                mRef = mRef.child(userStatsURL);
                mRef.push().setValue(d);

                // Go back to food item log page.
                Intent foodLog = new Intent(getApplicationContext(), FoodLogActivity.class);
                foodLog.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(foodLog);
                finish();
            }
        });
    }

    /**
     * Create a Food Object
     *
     * @param cal          calories the food contains
     * @param sugar        sugar the food contains
     * @param foodTextView EditText where users input food name
     * @return food Food Object
     */
    @NonNull
    private Food setFood(double cal, double sugar, TextView foodTextView) {
        Food food;
        if ((foodTextView.getText() + "").trim().equals("")) {
            food = new Food(sugar, cal, getString(R.string.prompt_food_empty));

        } else {
            food = new Food(sugar, cal, foodTextView.getText() + "");
        }
        return food;
    }

    /**
     * Get sugar from user input
     *
     * @param sugarInGramsText EditText where users input sugar
     * @return sugar sugar. 0 if the user input nothing
     */
    private double getSugar(TextView sugarInGramsText) {
        String sugarStr = sugarInGramsText.getText() + "";
        double sugar;
        if (sugarStr.equals("")) {
            sugar = SUGAREMPTY;
        } else {
            sugar = Double.parseDouble(sugarStr);
        }
        return sugar;
    }

    /**
     * Get calories from user input.
     *
     * @param foodCalories EditText where users input calories
     * @return cal calories. 0 if the user input nothing
     */
    private double getCalories(TextView foodCalories) {
        String caloriesStr = foodCalories.getText() + "";
        double cal;
        if (caloriesStr.equals("")) {
            cal = CALORIESEMPTY;
        } else {
            cal = Double.parseDouble(caloriesStr);
        }
        return cal;
    }


}
