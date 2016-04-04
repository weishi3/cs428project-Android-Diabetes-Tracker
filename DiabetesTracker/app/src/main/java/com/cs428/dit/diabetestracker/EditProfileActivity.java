package com.cs428.dit.diabetestracker;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
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
        setButton("familyHistory", R.id.familyHistoryTrue, R.id.familyHistoryFalse, 0);
        editText("bloodPressure", R.id.bloodPressure);
        setButton("gender", R.id.genderTrue, R.id.genderFalse, 0);
        setButton("sedentaryJob", R.id.sedentaryJobTrue, R.id.sedentaryJobFalse, R.id.sedentaryJobPNTA);
        editText("exerciseT", R.id.exerciseT);
        setButton("diagnosedD", R.id.diagnosedDTrue, R.id.diagnosedDFalse, R.id.diagnosedDPNTA);
        setButton("GDM", R.id.GDMTrue, R.id.GDMFalse, R.id.GDMPNTA);
        editText("weightB", R.id.weightB);
        setButton("CCVD", R.id.CCVDTrue, R.id.CCVDFalse, R.id.CCVDPNTA);
        setButton("PCOS", R.id.PCOSTrue, R.id.PCOSFalse, R.id.PCOSPNTA);
        setButton("psychotropic", R.id.psychotropicTrue, R.id.psychotropicFalse, R.id.psychotropicPNTA);
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
                if( viewTG.getText().toString().equals(getString(R.string.profile_no_data)) ||
                        viewTG.getText().toString().equals("")){
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
                if( viewHDL_C.getText().toString().equals(getString(R.string.profile_no_data)) ||
                        viewHDL_C.getText().toString().equals("")){
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
                boolean psychotropic = false;
                psychotropicIsNull = true;
                RadioButton psychotropicTrue = (RadioButton) findViewById(R.id.psychotropicTrue);
                if (psychotropicTrue.isChecked()){
                    psychotropic = true;
                    psychotropicIsNull = false;
                }
                RadioButton psychotropicFalse = (RadioButton) findViewById(R.id.psychotropicFalse);
                if(psychotropicFalse.isChecked()){
                    psychotropic = false;
                    psychotropicIsNull = false;
                }
                boolean PCOS = false;
                PCOSIsNull = true;
                RadioButton PCOSTrue = (RadioButton) findViewById(R.id.PCOSTrue);
                if (PCOSTrue.isChecked()){
                    PCOS = true;
                    PCOSIsNull = false;
                }
                RadioButton PCOSFalse = (RadioButton) findViewById(R.id.PCOSFalse);
                if(PCOSFalse.isChecked()){
                    PCOS = false;
                    PCOSIsNull = false;
                }
                boolean CCVD = false;
                CCVDIsNull = true;
                RadioButton CCVDTrue = (RadioButton) findViewById(R.id.CCVDTrue);
                if (CCVDTrue.isChecked()){
                    CCVD = true;
                    CCVDIsNull = false;
                }
                RadioButton CCVDFalse = (RadioButton) findViewById(R.id.CCVDFalse);
                if(CCVDFalse.isChecked()){
                    CCVD = false;
                    CCVDIsNull = false;
                }
                TextView viewWeightB = (TextView) findViewById(R.id.weightB);
                int weightB = 0;
                if( viewWeightB.getText().toString().equals(getString(R.string.profile_no_data)) ||
                        viewWeightB.getText().toString().equals("")){
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
                boolean GDM = false;
                GDMIsNull = true;
                RadioButton GDMTrue = (RadioButton) findViewById(R.id.GDMTrue);
                if (GDMTrue.isChecked()){
                    GDM = true;
                    GDMIsNull = false;
                }
                RadioButton GDMFalse = (RadioButton) findViewById(R.id.GDMFalse);
                if(GDMFalse.isChecked()){
                    GDM = false;
                    GDMIsNull = false;
                }
                boolean diagnosed = false;
                diagnosedIsNull = true;
                RadioButton diagnosedTrue = (RadioButton) findViewById(R.id.diagnosedDTrue);
                if (diagnosedTrue.isChecked()){
                    diagnosed = true;
                    diagnosedIsNull = false;
                }
                RadioButton diagnosedFalse = (RadioButton) findViewById(R.id.diagnosedDFalse);
                if(diagnosedFalse.isChecked()){
                    diagnosed = false;
                    diagnosedIsNull = false;
                }
                TextView viewExercise = (TextView) findViewById(R.id.exerciseT);
                int exercise = 0;
                if( viewExercise.getText().toString().equals(getString(R.string.profile_no_data)) ||
                        viewExercise.getText().toString().equals("")){
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
                boolean sedentary = false;
                sedentaryIsNull = true;
                RadioButton sedentaryTrue = (RadioButton) findViewById(R.id.sedentaryJobTrue);
                if (sedentaryTrue.isChecked()){
                    sedentary = true;
                    sedentaryIsNull = false;
                }
                RadioButton sedentaryFalse = (RadioButton) findViewById(R.id.sedentaryJobFalse);
                if(sedentaryFalse.isChecked()){
                    sedentary = false;
                    sedentaryIsNull = false;
                }
                boolean gender = false;
                RadioButton genderTrue = (RadioButton) findViewById(R.id.genderTrue);
                if (genderTrue.isChecked()){
                    gender = true;
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
                boolean history = false;
                RadioButton historyTrue = (RadioButton) findViewById(R.id.familyHistoryTrue);
                if (historyTrue.isChecked()){
                    history = true;
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
                    userGender.setValue(gender);
                    userDetails.put("gender", gender);
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
                    Firebase userSedentary = user.child("sedentaryJob");
                    if(sedentaryIsNull == false) {
                        userSedentary.setValue(sedentary);
                        userDetails.put("sedentaryJob", sedentary);
                    } else {
                        userDetails.put("sedentaryJob", null);
                        userSedentary.setValue(null);
                    }
                    Firebase userExercise = user.child("exerciseT");
                    if(exerciseIsNull == false) {
                        userExercise.setValue(exercise);
                        userDetails.put("exerciseT", exercise);
                    } else {
                        userDetails.put("exerciseT", null);
                        userExercise.setValue(null);
                    }
                    Firebase userDiagnosed = user.child("diagnosedD");
                    if(diagnosedIsNull == false) {
                        userDiagnosed.setValue(diagnosed);
                        userDetails.put("diagnosedD", diagnosed);
                    } else {
                        userDetails.put("diagnosedD", null);
                        userDiagnosed.setValue(null);
                    }
                    Firebase userGDM = user.child("GDM");
                    if(GDMIsNull == false) {
                        userGDM.setValue(GDM);
                        userDetails.put("GDM", GDM);
                    } else {
                        userGDM.setValue(null);
                        userDetails.put("GDM", null);
                    }
                    Firebase userWeightB = user.child("weightB");
                    if(weightBIsNull == false) {
                        userWeightB.setValue(weightB);
                        userDetails.put("weightB", weightB);
                    } else {
                        userWeightB.setValue(null);
                        userDetails.put("weightB", null);
                    }
                    Firebase userCCVD = user.child("CCVD");
                    if(CCVDIsNull == false) {
                        userCCVD.setValue(CCVD);
                        userDetails.put("CCVD", CCVD);
                    } else {
                        userCCVD.setValue(null);
                        userDetails.put("CCVD", null);
                    }
                    Firebase userPCOS = user.child("PCOS");
                    if(PCOSIsNull == false) {
                        userPCOS.setValue(PCOS);
                        userDetails.put("PCOS", PCOS);
                    } else {
                        userPCOS.setValue(null);
                        userDetails.put("PCOS", null);
                    }
                    Firebase userPsychotropic = user.child("psychotropic");
                    if(psychotropicIsNull == false) {
                        userPsychotropic.setValue(psychotropic);
                        userDetails.put("psychotropic", psychotropic);
                    } else {
                        userPsychotropic.setValue(null);
                        userDetails.put("psychotropic", null);
                    }
                    Firebase userHDL_C = user.child("HDL_C");
                    if(HDL_CIsNull == false) {
                        userHDL_C.setValue(HDL_C);
                        userDetails.put("HDL_C", HDL_C);
                    } else {
                        userHDL_C.setValue(null);
                        userDetails.put("HDL_C", null);
                    }
                    Firebase userTG = user.child("TG");
                    if(TGIsNull == false) {
                        userTG.setValue(TG);
                        userDetails.put("TG", TG);
                    } else {
                        userTG.setValue(null);
                        userDetails.put("TG", null);
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
            view.setText("N/A");
            return;
        }
        view.setText( text.toString() );
    }
    private void setButton(String key, int idTrue, int idFalse, int idPNTA) {
        Boolean value = (Boolean)userDetails.get(key);
        if( value == null ){
            RadioButton pnta = (RadioButton) findViewById(idPNTA);
            pnta.setChecked(true);
        }
        else if( value.booleanValue() == true ){
            RadioButton t = (RadioButton) findViewById(idTrue);
            t.setChecked(true);
        }
        else{
            RadioButton f = (RadioButton) findViewById(idFalse);
            f.setChecked(true);
        }
    }
    public void onRadioButtonClicked(View view) {

    }
}