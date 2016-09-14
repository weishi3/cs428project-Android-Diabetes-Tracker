package com.cs428.dit.diabetestracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.cs428.dit.diabetestracker.helpers.FoodList;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.Firebase;

import java.util.HashMap;

public class SaveMediumGIDietActivity extends AppCompatActivity {
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
    private Context applicationContext;

    /**
     * The method is called when this activity is created,savedInstanceState is used to restore activity state when exited unexpectedly,not used here
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_diet);
        session = new SessionManager(getApplicationContext());
        Firebase.setAndroidContext(this);
        userDetails = session.getUserDetails();
        applicationContext = this;
        initializeAllButton();
        Button submitBtn = (Button) findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAllButtonInfo();
                String foodList = getFoodList();
                String userEmail = userDetails.get("email").toString().replace('.', '!');
                FoodList.saveFoodList(applicationContext, userEmail, foodList);

                Intent dietLow = new Intent(getApplicationContext(), DietLowGIActivity.class);
                startActivity(dietLow);
            }
        });
    }

    /**
     * Gets the list of food selected by the user through the radio buttons
     * @return      a comma seperated string list of selected foods
     */
    @NonNull
    private String getFoodList() {
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
        return foodList;
    }

    /**
     * Update the selection of food made by the user
     */
    private void updateAllButtonInfo() {
        riceNoodles = updateButtonInfo(R.id.riceNoodlesTrue);
        sweetCorn = updateButtonInfo(R.id.sweetCornTrue);
        lentils = updateButtonInfo(R.id.lentilsTrue);
        beans = updateButtonInfo(R.id.beansTrue);
        yogurt = updateButtonInfo(R.id.yogurtTrue);
        greekYogurt = updateButtonInfo(R.id.greekYogurtTrue);
        plums = updateButtonInfo(R.id.plumsTrue);
        oranges = updateButtonInfo(R.id.orangesTrue);
    }

    /**
     * Check wether a button is selected
     * @param buttonId
     * @return
     */
    private boolean updateButtonInfo(int buttonId) {
        RadioButton buttonTrue = (RadioButton) findViewById(buttonId);
        return buttonTrue.isChecked();
    }

    /**
     * Sets all the food buttons to be selected by default
     */
    private void initializeAllButton() {
        setButton(R.id.riceNoodlesTrue);
        setButton(R.id.sweetCornTrue);
        setButton(R.id.lentilsTrue);
        setButton(R.id.beansTrue);
        setButton(R.id.yogurtTrue);
        setButton(R.id.greekYogurtTrue);
        setButton(R.id.plumsTrue);
        setButton(R.id.orangesTrue);
    }

    /**
     * Set the selected attribute of the button with id idTrue to true
     * @param idTrue    the id of the button being set
     */
    private void setButton(int idTrue) {
        RadioButton t = (RadioButton) findViewById(idTrue);
        t.setChecked(true);
    }

    public void onRadioButtonClicked(View view) {

    }
}
