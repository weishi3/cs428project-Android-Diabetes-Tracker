package com.cs428.dit.diabetestracker;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.Restriction;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.Firebase;

import org.w3c.dom.Text;

import java.util.HashMap;
public class EditProfileActivity extends AppCompatActivity {
    private SessionManager session;
    private HashMap<String, Object> userDetails;
    private HashMap<String, Boolean> isNull = new HashMap<String, Boolean>();
    private View focusView;
    private boolean cancel;

    /**
     * Method that is called when this activity is created (when this screen is pulled up)
     * @param savedInstanceState
     */
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
                focusView = null;
                cancel = false;
                initializeIsNull();
                double TG, HDL_C, bmi, waistline;
                int weightB, exercise, pressure, age;
                boolean diagnosed, psychotropic, PCOS, CCVD, GDM, sedentary, history, gender;
                Restriction rest = new Restriction();
                TG = setDblCancel(R.id.TG, true, "TG", rest.get_false_dbl(), "");
                HDL_C = setDblCancel(R.id.HDL_C, true, "HDL_C", rest.get_false_dbl(), "");
                psychotropic = getBoolean("psychotropic", R.id.psychotropicTrue,
                        R.id.psychotropicFalse);
                PCOS = getBoolean("PCOS", R.id.PCOSTrue, R.id.PCOSFalse);
                CCVD = getBoolean("CCVD", R.id.CCVDTrue, R.id.CCVDFalse);
                weightB = setIntCancel(R.id.weightB, true, "weightB", rest.get_false_int(), "");
                GDM = getBoolean("GDM", R.id.GDMTrue, R.id.GDMFalse);
                diagnosed = getBoolean("diagnosed", R.id.diagnosedDTrue,
                        R.id.diagnosedDFalse);
                exercise = setIntCancel(R.id.exerciseT, true, "exercise", rest.get_false_int(), "");
                sedentary = getBoolean("sedentary", R.id.sedentaryJobTrue,
                        R.id.sedentaryJobFalse);
                gender = getBoolean("gender", R.id.genderTrue, R.id.genderFalse);
                pressure = setIntCancel(R.id.bloodPressure, false, "pressure", rest.get_false_int(), "");
                history = getBoolean("history", R.id.familyHistoryTrue, R.id.familyHistoryFalse);
                bmi = setDblCancel(R.id.BMI, false, "BMI", rest.get_false_dbl(), "");
                waistline = setDblCancel(R.id.waistline, false, "waistline", new Restriction.dbl_restriction(){
                    @Override
                    public boolean call(double param){
                        if (param < 1)
                            return true;
                        else
                            return false;
                    }
                }, getString(R.string.error_waistline));
                age = setIntCancel(R.id.age, false, "age", new Restriction.int_restriction() {
                    @Override
                    public boolean call(int param) {
                        if (param < 1 || param > 120)
                            return true;
                        else
                            return false;
                    }
                }, getString(R.string.error_age));
                if (!cancel) {
                    updateValue("gender", false, gender, user);
                    updateValue("bloodPressure", false, pressure, user);
                    updateValue("familyHistory", false, history, user);
                    updateValue("BMI", false, bmi, user);
                    updateValue("waistline", false, waistline, user);
                    updateValue("age", false, age, user);
                    updateValue("sedentaryJob", isNull.get("sedentary"), sedentary, user);
                    updateValue("exerciseT", isNull.get("exercise"), exercise, user);
                    updateValue("diagnosedD", isNull.get("diagnosed"), diagnosed, user);
                    updateValue("GDM",isNull.get("GDM"), GDM, user);
                    updateValue("weightB", isNull.get("weightB"), weightB, user);
                    updateValue("CCVD", isNull.get("CCVD"), CCVD, user);
                    updateValue("PCOS", isNull.get("PCOS"), PCOS, user);
                    updateValue("psychotropic", isNull.get("psychotropic"), psychotropic, user);
                    updateValue("HDL_C", isNull.get("HDL_C"), HDL_C, user);
                    updateValue("TG", isNull.get("TG"), TG, user);
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

    /**
     * Sets the current value of the button group
     * @param key the name of the value in the database
     * @param idTrue id of the true button
     * @param idFalse id of the false button
     * @param idPNTA id of the "Prefer not to Answer" button
     */
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

    /**
     * Updates the value in the database and SessionManager
     * @param key the name of the value
     * @param isNull whether or not the value is null
     * @param value the value
     * @param user the Firebase pointing to this user
     */
    private void updateValue(String key, boolean isNull, Object value, Firebase user){
        Firebase thisValue = user.child(key);
        if (isNull) {
            thisValue.setValue(null);
            userDetails.put(key, null);
        } else {
            thisValue.setValue(value);
            userDetails.put(key, value);
        }
    }

    /**
     * Empty method required for radio button use
     * @param view the button on the screen
     */
    public void onRadioButtonClicked(View view) {

    }

    /**
     * Gets the value of a boolean value from a radio button
     * @param key the name of the value
     * @param id_t id of the true button
     * @param id_f id of the false button
     * @return the parsed value
     */
    private boolean getBoolean(String key, int id_t, int id_f){
        isNull.put(key, true);
        RadioButton t = (RadioButton) findViewById(id_t);
        if (t.isChecked()){
            isNull.put(key, false);
            return true;
        }
        RadioButton f = (RadioButton) findViewById(id_f);
        if (f.isChecked()){
            isNull.put(key, false);
            return false;
        }
        else {
            //value doesn't matter if isNull[key]
            return false;
        }
    }

    /**
     * Attempts to parse an int value from a text field
     * @param view_id the id of the field on the screen
     * @param canBeNull whether or not this field is optional
     * @param key the name of the field in the database
     * @param rest int_restriction used to restrict the value of the int
     * @param error the error corresponding to the failing the int_restriction
     * @return the parsed int
     */
    private int setIntCancel(int view_id, boolean canBeNull, String key, Restriction.int_restriction rest, String error) {
        TextView view = (TextView) findViewById(view_id);
        int retval;
        if (canBeNull && (view.getText().toString().equals(getString(R.string.profile_no_data))) ||
                view.getText().toString().equals("")){
            isNull.put(key, true);
            //retval unimportant
            return -1;
        }
        else {
            try {
                retval = Integer.parseInt(view.getText().toString());
            } catch (NumberFormatException e){
                view.setError(getString(R.string.error_num_format));
                focusView = view;
                cancel = true;
                //retval unimportant
                return -1;
            }
            if (rest.call(retval)){
                view.setError(error);
                focusView = view;
                cancel = true;
            }
            return retval;
        }
    }

    /**
     * Attempts to parse a dbl from a text field
     * @param view_id the id of the field on the screen
     * @param canBeNull whether or not the field is optional
     * @param key the name of the field in the database
     * @param rest the dbl_restriction used to restrict the value of the dbl
     * @param error the error corresponding to failng the dbl_restriction
     * @return the parse value
     */
    private double setDblCancel(int view_id, boolean canBeNull, String key, Restriction.dbl_restriction rest, String error) {
        double retval;
        TextView view = (TextView) findViewById(view_id);
        if (canBeNull && (view.getText().toString().equals(getString(R.string.profile_no_data))) ||
            view.getText().toString().equals("")){
            isNull.put(key, true);
            //retval unimportant
            return -1;
        }
        else {
            try {
                retval = Double.parseDouble(view.getText().toString());
            } catch (NumberFormatException e){
                view.setError(getString(R.string.error_num_format));
                focusView = view;
                cancel = true;
                //retval unimportant
                return -1;
            }
            if(rest.call(retval)) {
                view.setError(error);
                focusView = view;
                cancel = true;
            }
            return retval;
        }
    }

    /**
     * Initializes the optional fields in the isNull Hashmap denoting that they are initially not
     * null.
     */
    private void initializeIsNull(){
        isNull.put("sedentary", false);
        isNull.put("exercise", false);
        isNull.put("diagnosed", false);
        isNull.put("GDM", false);
        isNull.put("weightB", false);
        isNull.put("CCVD", false);
        isNull.put("PCOS", false);
        isNull.put("psychotropic", false);
        isNull.put("HDL_C", false);
        isNull.put("TG", false);
    }
}