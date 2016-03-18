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

    private boolean sedentaryIsNull = false;
    private boolean exerciseIsNull = false;
    private boolean diagnosedIsNull = false;
    private boolean GDMIsNull = false;
    private boolean weightBIsNull = false;
    private boolean CCVDIsNull = false;
    private boolean PCOSIsNull = false;
    private boolean psychotropicIsNull = false;
    private boolean HDL_CIsNull = false;
    private boolean TGIsNull = false;



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
        editText("bloodPressure", R.id.bloodPressure);
        TextView view = (TextView) findViewById(R.id.gender);
        view.setText((boolean) userDetails.get("gender") ? "Male" : "Female");
        editText("sedentaryJob", R.id.sedentaryJob);
        editText("exerciseT", R.id.exerciseT);
        editText("diagnosedD", R.id.diagnosedD);
        editText("GDM", R.id.GDM);
        editText("weightB", R.id.weightB);
        editText("CCVD", R.id.CCVD);
        editText("PCOS", R.id.PCOS);
        editText("psychotropic", R.id.psychotropic);
        editText("HDL_C", R.id.HDL_C);
        editText("TG", R.id.TG);

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

                TextView viewTG = (TextView) findViewById(R.id.TG);
                double TG = 0;
                if( viewTG.getText().toString().equals(getString(R.string.profile_no_data))){
                    TGIsNull = true;
                }
                else {
                    try {
                        TG = Double.parseDouble(viewTG.getText().toString());
                    } catch (NumberFormatException e) {
                        viewTG.setError(getString(R.string.error_num_format));
                        focusView = viewTG;
                        cancel = true;
                    }
                }


                TextView viewHDL_C = (TextView) findViewById(R.id.HDL_C);
                Double HDL_C = 0.0;
                if( viewHDL_C.getText().toString().equals(getString(R.string.profile_no_data))){
                    HDL_CIsNull = true;
                }
                else {
                    try {
                        HDL_C = Double.parseDouble(viewHDL_C.getText().toString());
                    } catch (NumberFormatException e) {
                        viewHDL_C.setError(getString(R.string.error_num_format));
                        focusView = viewHDL_C;
                        cancel = true;
                    }
                }


                TextView viewPsychotropic = (TextView) findViewById(R.id.psychotropic);
                boolean psychotropic = false;
                if( viewPsychotropic.getText().toString().equals(getString(R.string.profile_no_data))){
                    psychotropicIsNull = true;
                }
                else{
                    psychotropic= Boolean.parseBoolean(viewPsychotropic.getText().toString());
                }

                TextView viewPCOS = (TextView) findViewById(R.id.PCOS);
                boolean PCOS = false;
                if( viewPCOS.getText().toString().equals(getString(R.string.profile_no_data))){
                    PCOSIsNull = true;
                }
                else {
                    PCOS = Boolean.parseBoolean(viewPCOS.getText().toString());
                }

                TextView viewCCVD = (TextView) findViewById(R.id.CCVD);
                boolean CCVD = false;
                if( viewCCVD.getText().toString().equals(getString(R.string.profile_no_data))){
                    CCVDIsNull = true;
                }
                else {
                    CCVD = Boolean.parseBoolean(viewCCVD.getText().toString());
                }

                TextView viewWeightB = (TextView) findViewById(R.id.weightB);
                int weightB = 0;
                if( viewWeightB.getText().toString().equals(getString(R.string.profile_no_data))){
                    weightBIsNull = true;
                }
                else {
                    try {
                        weightB = Integer.parseInt(viewWeightB.getText().toString());
                    } catch (NumberFormatException e) {
                        //error stuff
                        viewWeightB.setError(getString(R.string.error_num_format));
                        focusView = viewWeightB;
                        cancel = true;
                    }
                }

                TextView viewGDM = (TextView) findViewById(R.id.GDM);
                boolean GDM = false;
                if( viewGDM.getText().toString().equals(getString(R.string.profile_no_data))){
                    GDMIsNull = true;
                }
                else {
                    GDM = Boolean.parseBoolean(viewGDM.getText().toString());
                }

                TextView viewDiagnosed = (TextView) findViewById(R.id.diagnosedD);
                boolean diagnosed = false;
                if( viewDiagnosed.getText().toString().equals(getString(R.string.profile_no_data))){
                    diagnosedIsNull = true;
                }
                else {
                    diagnosed = Boolean.parseBoolean(viewDiagnosed.getText().toString());
                }

                TextView viewExercise = (TextView) findViewById(R.id.exerciseT);
                int exercise = 0;
                if( viewExercise.getText().toString().equals(getString(R.string.profile_no_data))){
                    exerciseIsNull = true;
                }
                else {
                    try {
                        exercise = Integer.parseInt(viewExercise.getText().toString());
                    } catch (NumberFormatException e) {
                        //error stuff
                        viewExercise.setError(getString(R.string.error_num_format));
                        focusView = viewExercise;
                        cancel = true;
                    }
                }

                TextView viewSedentary = (TextView) findViewById(R.id.sedentaryJob);
                boolean sedentary = false;
                if( viewSedentary.getText().toString().equals(getString(R.string.profile_no_data))){
                    sedentaryIsNull = true;
                }
                else {
                    sedentary = Boolean.parseBoolean(viewSedentary.getText().toString());
                }

                TextView viewGender = (TextView) findViewById(R.id.gender);
                String sGender = viewGender.getText().toString().toLowerCase();
                if (!sGender.equals("male") && !sGender.equals("female")) {
                    viewGender.setError(getString(R.string.error_gender));
                    focusView = viewGender;
                    cancel = true;
                }

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

                TextView historyView = (TextView) findViewById(R.id.familyHistory);
                boolean history = Boolean.parseBoolean((historyView.getText().toString()));

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


                if (!cancel) {
                    Firebase userGender = user.child("gender");
                    userGender.setValue(sGender.equals("male"));
                    userDetails.put("gender", sGender.equals("male"));
                    Firebase userBloodPressure = user.child("bloodPressure");
                    userBloodPressure.setValue(pressure);
                    userDetails.put("bloodPressure", pressure);
                    Firebase userFamilyHistory = user.child("familyHistory");
                    userFamilyHistory.setValue(history);
                    userDetails.put("familyHistory", history);
                    Firebase userBMI = user.child("BMI");
                    userBMI.setValue(bmi);
                    userDetails.put("BMI", bmi);
                    Firebase userWaistline = user.child("waistline");
                    userWaistline.setValue(waistline);
                    userDetails.put("waistline", waistline);
                    Firebase userAge = user.child("age");
                    userAge.setValue(age);
                    userDetails.put("age", age);

                    if(sedentaryIsNull == false) {
                        Firebase userSedentary = user.child("sedentaryJob");
                        userSedentary.setValue(sedentary);
                        userDetails.put("sedentaryJob", sedentary);
                    }

                    if(exerciseIsNull == false) {
                        Firebase userExercise = user.child("exerciseT");
                        userExercise.setValue(exercise);
                        userDetails.put("exerciseT", exercise);
                    }

                    if(diagnosedIsNull == false) {
                        Firebase userDiagnosed = user.child("diagnosedD");
                        userDiagnosed.setValue(diagnosed);
                        userDetails.put("diagnosedD", diagnosed);
                    }

                    if(GDMIsNull == false) {
                        Firebase userGDM = user.child("GDM");
                        userGDM.setValue(GDM);
                        userDetails.put("GDM", GDM);
                    }

                    if(weightBIsNull == false) {
                        Firebase userWeightB = user.child("weightB");
                        userWeightB.setValue(weightB);
                        userDetails.put("weightB", weightB);
                    }

                    if(CCVDIsNull == false) {
                        Firebase userCCVD = user.child("CCVD");
                        userCCVD.setValue(CCVD);
                        userDetails.put("CCVD", CCVD);
                    }

                    if(PCOSIsNull == false) {
                        Firebase userPCOS = user.child("PCOS");
                        userPCOS.setValue(PCOS);
                        userDetails.put("PCOS", PCOS);
                    }

                    if(psychotropicIsNull == false) {
                        Firebase userPsychotropic = user.child("psychotropic");
                        userPsychotropic.setValue(psychotropic);
                        userDetails.put("psychotropic", psychotropic);
                    }

                    if(HDL_CIsNull == false) {
                        Firebase userHDL_C = user.child("HDL_C");
                        userHDL_C.setValue(HDL_C);
                        userDetails.put("HDL_C", HDL_C);
                    }

                    if(TGIsNull == false) {
                        Firebase userTG = user.child("TG");
                        userTG.setValue(TG);
                        userDetails.put("TG", TG);
                    }
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
        Object text = userDetails.get(key);
        if( text == null ){
            view.setText( "N/A" );
            return;
        }
        view.setText( text.toString() );
    }
}
