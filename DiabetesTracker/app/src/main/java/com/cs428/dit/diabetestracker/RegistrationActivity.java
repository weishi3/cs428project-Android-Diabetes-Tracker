package com.cs428.dit.diabetestracker;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cs428.dit.diabetestracker.helpers.Restriction;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.w3c.dom.Text;

import java.util.Map;
import java.util.concurrent.Callable;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private EditText mAgeView;
    private EditText mWaistlineView;
    private EditText mBMIView;
    private RadioButton mFamilyHistoryTrueBtn;
    private RadioButton mFamilyHistoryFalseBtn;
    private EditText mBloodPressureView;
    private RadioButton mGenderTrueBtn;
    private RadioButton mGenderFalseBtn;
    private View focusView;
    private boolean cancel;
    //Store parsed vars
    int pAge;
    double pWaistline;
    double pBMI;
    boolean pFamilyHistory;
    int pBloodPressure;
    boolean pGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Firebase.setAndroidContext(this);
        mEmailView = (EditText) findViewById(R.id.registrationEmail);
        mPasswordView = (EditText) findViewById(R.id.registrationPW);
        mConfirmPasswordView = (EditText) findViewById(R.id.registrationConfirmPW);
        mAgeView = (EditText) findViewById(R.id.age);
        mWaistlineView = (EditText) findViewById(R.id.waistline);
        mBMIView = (EditText) findViewById(R.id.BMI);
        mFamilyHistoryTrueBtn = (RadioButton) findViewById(R.id.familyHistoryTrue);
        mFamilyHistoryFalseBtn = (RadioButton) findViewById(R.id.familyHistoryFalse);
        mBloodPressureView = (EditText) findViewById(R.id.bloodPressure);
        mGenderTrueBtn = (RadioButton) findViewById(R.id.genderTrue);
        mGenderFalseBtn = (RadioButton) findViewById(R.id.genderFalse);
        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }
    /**
     * Attempts to register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);
        mAgeView.setError(null);
        mWaistlineView.setError(null);
        mBMIView.setError(null);
        mFamilyHistoryFalseBtn.setError(null);
        mBloodPressureView.setError(null);
        mGenderFalseBtn.setError(null);
        // Store values at the time of the register attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();
        String age = mAgeView.getText().toString();
        String waistline = mWaistlineView.getText().toString();
        String BMI = mBMIView.getText().toString();
        pFamilyHistory = mFamilyHistoryTrueBtn.isChecked();
        String blood_pressure = mBloodPressureView.getText().toString();
        pGender = mGenderTrueBtn.isChecked();
        cancel = false;
        Restriction rest = new Restriction();
        //Check for empty gender
        setBooleanCancel((!pGender && !mGenderFalseBtn.isChecked()),
                mGenderFalseBtn);
        //check for empty or invalid blood pressure
        pBloodPressure = setIntCancel(blood_pressure, mBloodPressureView, rest.get_false_int(), "");
        //check for empty or invalid familyHistory
        setBooleanCancel((!pFamilyHistory && !mFamilyHistoryFalseBtn.isChecked()),
                mFamilyHistoryFalseBtn);
        //check for empty or invalid bmi
        pBMI = setDblCancel(BMI, mBMIView, rest.get_false_dbl(), "");
        //check for empty or invalid waistline
        pWaistline = setDblCancel(waistline, mWaistlineView, rest.get_false_dbl(), "");
        //check for empty or invalid age
        pAge = setIntCancel(age,  mAgeView, new Restriction.int_restriction() {
            public boolean call(int param){
                return (param < 1 || param > 120);
            }
        }, getString(R.string.error_age));
        // Check for an empty confirm password.
        if (TextUtils.isEmpty(confirmPassword)) {
            mConfirmPasswordView.setError(getString(R.string.error_field_required));
            focusView = mConfirmPasswordView;
            cancel = true;
        }
        // Check if confirm password matches password.
        if (!confirmPassword.equals(password)) {
            mConfirmPasswordView.setError(getString(R.string.error_not_match_password));
            focusView = mConfirmPasswordView;
            cancel = true;
        }
        // Check for an empty password
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            register();
        }
    }
    /**
     * Register a new account
     */
    private void register() {
        //perform register attempt
        final String mEmail = mEmailView.getText().toString();
        final String mPassword = mPasswordView.getText().toString();
        //Reference to Firebase data
        final Firebase myFirebaseRef = new Firebase(getString(R.string.firebase_url));
        myFirebaseRef.createUser(mEmail, mPassword, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Firebase user = myFirebaseRef.child("users").child(mEmail.replace('.', '!'));
                user.child("gender").setValue(pGender);
                user.child("bloodPressure").setValue(pBloodPressure);
                user.child("familyHistory").setValue(pFamilyHistory);
                user.child("BMI").setValue(pBMI);
                user.child("waistline").setValue(pWaistline);
                user.child("age").setValue(pAge);
                Toast.makeText(getApplicationContext(), getString(R.string.on_success_registration), Toast.LENGTH_SHORT).show();
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                // if the email is already in use, notify the user.
                if (firebaseError.getCode() == FirebaseError.EMAIL_TAKEN) {
                    mEmailView.setError(firebaseError.getMessage());
                    mEmailView.requestFocus();
                }
                else {
                    Toast.makeText(getApplicationContext(), firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * Check validity of email
     *
     * @param email user registration email
     * @return true is valid
     */
    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    /**
     * Check strength of password
     *
     * @param password user password
     * @return true if satisfy strength requirement
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void setBooleanCancel(boolean condition, RadioButton btn) {
        if(condition) {
            btn.setError(getString(R.string.error_field_required));
            focusView = btn;
            cancel = true;
        }
    }

    private void setValue(Firebase user, String child, Object value){
        user.child(child).setValue(value);
    }

    private int setIntCancel(String value, EditText view, Restriction.int_restriction restriction, String error) {
        if(TextUtils.isEmpty(value)) {
            view.setError(getString(R.string.error_field_required));
            focusView = view;
            cancel = true;
            return -1;
        }
        try {
            int parsedValue = Integer.parseInt(value);
            if (restriction.call(parsedValue)){
                view.setError(error);
                focusView = view;
                cancel = true;
            }
            return parsedValue;
        } catch (NumberFormatException e) {
            view.setError(getString(R.string.error_num_format));
            focusView = view;
            cancel = true;
            return -1; //return value not used if cancel is set
        }
    }
    /*
    *
     */
    private double setDblCancel(String value, EditText view, Restriction.dbl_restriction restriction, String error) {
        if(TextUtils.isEmpty(value)) {
            view.setError(getString(R.string.error_field_required));
            focusView = view;
            cancel = true;
            return -1;
        }
        try {
            double parsedValue = Double.parseDouble(value);
            if (restriction.call(parsedValue)){
                view.setError(error);
                focusView = view;
                cancel = true;
            }
            return parsedValue;
        } catch (NumberFormatException e) {
            view.setError(getString(R.string.error_num_format));
            focusView = view;
            cancel = true;
            return -1; //return value not used if cancel is set
        }
    }
    /**
     * Empty function necessary for radiobuttons
     * @param view
     */
    public void onRadioButtonClicked(View view) {
    }
}
