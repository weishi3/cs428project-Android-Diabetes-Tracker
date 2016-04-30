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
        intializeAllButton();
        Button submitBtn = (Button) findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAllButtonInfo();
                String foodList = getFoodList();
                String userEmail = userDetails.get("email").toString().replace('.', '!');
                FoodList.add(ac, userEmail, foodList);

                Intent dietLow = new Intent(getApplicationContext(), DietLow.class);
                startActivity(dietLow);
            }
        });
    }

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

    private boolean updateButtonInfo(int buttonId){
        RadioButton buttonTrue = (RadioButton) findViewById(buttonId);
        return buttonTrue.isChecked();
    }

    private void intializeAllButton() {
        setButton(R.id.riceNoodlesTrue);
        setButton(R.id.sweetCornTrue);
        setButton(R.id.lentilsTrue);
        setButton(R.id.beansTrue);
        setButton(R.id.yogurtTrue);
        setButton(R.id.greekYogurtTrue);
        setButton(R.id.plumsTrue);
        setButton(R.id.orangesTrue);
    }

    private void setButton(int idTrue) {
        RadioButton t = (RadioButton) findViewById(idTrue);
        t.setChecked(true);
    }

    public void onRadioButtonClicked(View view) {

    }
}
