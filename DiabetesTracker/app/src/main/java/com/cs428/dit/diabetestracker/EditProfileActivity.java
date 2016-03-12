package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.Firebase;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    private SessionManager session;
    private HashMap<String, Object> userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        session = new SessionManager(getApplicationContext());
        Firebase.setAndroidContext(this);

        userDetails = session.getUserDetails();
        editText("age", R.id.age);
        editText("waistline", R.id.waistline);
        editText("BMI", R.id.BMI);
        editText("familyHistory", R.id.familyHistory);
        editText("blood_pressure", R.id.bloodPressure);
        TextView view = (TextView) findViewById(R.id.gender);
        view.setText((boolean) userDetails.get("gender") ? "Male" : "Female");

        //set button to take you to edit profile page
        Button submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase ref = new Firebase(getString(R.string.firebase_url));
                String userEmail = userDetails.get("email").toString().replace('.', '!');
                Firebase user = ref.child("users").child(userEmail);

                //read the edit boxes and update the information in the database
                //also update the local copy
                View focusView = null;
                Boolean cancel = false;
                HashMap<String, Object> userDetails = new HashMap<String, Object>();
                Firebase userAge = user.child("age");
                int age = 1;
                TextView viewAge = (TextView) findViewById(R.id.age);
                try {
                    age = Integer.parseInt(viewAge.getText().toString());
                } catch (NumberFormatException e) {
                    //error stuff
                    viewAge.setError(getString(R.string.error_num_format));
                    focusView = viewAge;
                    cancel = true;
                }
                if (age < 1 || age > 120) {
                    viewAge.setError(getString(R.string.error_age));
                    focusView = viewAge;
                    cancel = true;
                }
                double waistline = 1;
                TextView viewWaist = (TextView) findViewById(R.id.waistline);
                try {
                    waistline = Double.parseDouble(viewWaist.getText().toString());
                } catch (NumberFormatException e) {
                    viewWaist.setError(getString(R.string.error_num_format));
                    focusView = viewWaist;
                    cancel = true;
                }
                if (waistline < 1) {
                    viewWaist.setError(getString(R.string.error_waistline));
                    focusView = viewWaist;
                    cancel = true;
                }
                TextView viewBMI = (TextView) findViewById(R.id.BMI);
                double bmi = 0;
                try {
                    bmi = Double.parseDouble(viewBMI.getText().toString());
                } catch (NumberFormatException e) {
                    viewBMI.setError(getString(R.string.error_num_format));
                    focusView = viewBMI;
                    cancel = true;
                }
                /*TODO if (bmi <  0|| bmi > ) {
                    viewBMI.setError("Invalid BMI.");
                    focusView = viewBMI;
                    cancel = true;
                }*/
                TextView historyView = (TextView) findViewById(R.id.familyHistory);
                boolean history = Boolean.parseBoolean((historyView.getText().toString()));
                TextView viewPressure = (TextView) findViewById(R.id.bloodPressure);
                int pressure = 0;
                try {
                    pressure = Integer.parseInt(viewPressure.getText().toString());
                } catch (NumberFormatException e) {
                    viewPressure.setError(getString(R.string.error_num_format));
                    focusView = viewPressure;
                    cancel = true;
                }
                //TODO range check blood pressure
                TextView viewGender = (TextView) findViewById(R.id.gender);
                String sGender = viewGender.getText().toString().toLowerCase();
                if (!sGender.equals("male") && !sGender.equals("female")) {
                    viewGender.setError(getString(R.string.error_gender));
                    focusView = viewGender;
                    cancel = true;
                }

                if (!cancel) {
                    Firebase userGender = user.child("gender");
                    userGender.setValue(sGender.equals("male"));
                    userDetails.put("gender", sGender.equals("male"));
                    Firebase userBloodPressure = user.child("blood_pressure");
                    userBloodPressure.setValue(pressure);
                    userDetails.put("blood_pressure", pressure);
                    Firebase userFamilyHistory = user.child("familyHistory");
                    userFamilyHistory.setValue(history);
                    userDetails.put("familyHistory", history);
                    Firebase userBMI = user.child("BMI");
                    userBMI.setValue(bmi);
                    userDetails.put("BMI", bmi);
                    Firebase userWaistline = user.child("waistline");
                    userWaistline.setValue(waistline);
                    userDetails.put("waistline", waistline);
                    userAge.setValue(age);
                    userDetails.put("age", age);
                    session.updateUserDetails(userDetails);

                    Intent viewProfile = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(viewProfile);
                } else {
                    focusView.requestFocus();
                }
            }
        });
    }

    /**
     * Grabs a value from our SessionManager hashmap and overwrites
     * it in the layout object
     * @param key the key to the value we're writing
     * @param id the id of the layout object we're overwriting
     */
    private void editText(String key, int id) {
        TextView view = (TextView) findViewById(id);
        view.setText(userDetails.get(key).toString());
    }
}
