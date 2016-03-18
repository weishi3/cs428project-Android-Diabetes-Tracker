package com.cs428.dit.diabetestracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private EditText mAgeView;
    private EditText mWaistlineView;
    private EditText mBMIView;
    private EditText mFamilyHistoryView;
    private EditText mBloodPressureView;
    private EditText mGenderView;
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
        mFamilyHistoryView = (EditText) findViewById(R.id.familyHistory);
        mBloodPressureView = (EditText) findViewById(R.id.bloodPressure);
        mGenderView = (EditText) findViewById(R.id.gender);

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
        mFamilyHistoryView.setError(null);
        mBloodPressureView.setError(null);
        mGenderView.setError(null);

        // Store values at the time of the register attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();
        String age = mAgeView.getText().toString();
        String waistline = mWaistlineView.getText().toString();
        String BMI = mBMIView.getText().toString();
        String familyHistory = mFamilyHistoryView.getText().toString();
        String blood_pressure = mBloodPressureView.getText().toString();
        String gender = mGenderView.getText().toString();



        boolean cancel = false;
        View focusView = null;

        //Check for empty or invalid gender.
        if (TextUtils.isEmpty(gender)) {
            mGenderView.setError(getString(R.string.error_field_required));
            focusView = mGenderView;
            cancel = true;
        } else if(!gender.equals("male") && !gender.equals("female")) {
            mGenderView.setError(getString(R.string.error_gender));
            focusView = mGenderView;
            cancel = true;
        } else {
            pGender = gender.equals("male");
        }
        //check for empty or invalid blood pressure
        if (TextUtils.isEmpty(blood_pressure)) {
            mBloodPressureView.setError(getString(R.string.error_field_required));
            focusView = mBloodPressureView;
            cancel = true;
        } else {
            try {
                pBloodPressure = Integer.parseInt(blood_pressure);
            } catch (NumberFormatException e) {
                mBloodPressureView.setError(getString(R.string.error_num_format));
                focusView = mBloodPressureView;
                cancel = true;
            }
        }
        //check for empty
        if (TextUtils.isEmpty(familyHistory)) {
            mFamilyHistoryView.setError(getString(R.string.error_field_required));
            focusView = mFamilyHistoryView;
            cancel = true;
        }

        //retrieve familyHistory
        pFamilyHistory = familyHistory.equals("true");

        //check for empty or invalid bmi
        if (TextUtils.isEmpty(BMI)) {
            mBMIView.setError(getString(R.string.error_field_required));
            focusView = mBMIView;
            cancel = true;
        } else {
            try {
                pBMI = Double.parseDouble(BMI);
            } catch (NumberFormatException e) {
                mBMIView.setError(getString(R.string.error_num_format));
                focusView = mBMIView;
                cancel = true;
            }
        }
        //check for empty or invalid waistline
        if (TextUtils.isEmpty(waistline)) {
            mWaistlineView.setError(getString(R.string.error_field_required));
            focusView = mWaistlineView;
            cancel = true;
        } else {
            try {
                pWaistline = Double.parseDouble(waistline);
            } catch (NumberFormatException e) {
                mWaistlineView.setError(getString(R.string.error_num_format));
                focusView = mWaistlineView;
                cancel = true;
            }
        }
        //check for empty or invalid age
        if (TextUtils.isEmpty(age)) {
            mAgeView.setError(getString(R.string.error_field_required));
            focusView = mAgeView;
            cancel = true;
        } else {
            try {
                pAge = Integer.parseInt(age);
                if (pAge < 1 || pAge > 120) {
                    mAgeView.setError(getString(R.string.error_age));
                    focusView = mAgeView;
                    cancel = true;
                }
            } catch (NumberFormatException e) {
                //error stuff
                mAgeView.setError(getString(R.string.error_num_format));
                focusView = mAgeView;
                cancel = true;
            }
        }
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
                Firebase user = myFirebaseRef.child("users").child(mEmail.replace('.','!'));
                Firebase userGender = user.child("gender");
                userGender.setValue(pGender);
                Firebase userBloodPressure = user.child("bloodPressure");
                userBloodPressure.setValue(pBloodPressure);
                Firebase userFamilyHistory = user.child("familyHistory");
                userFamilyHistory.setValue(pFamilyHistory);
                Firebase userBMI = user.child("BMI");
                userBMI.setValue(pBMI);
                Firebase userWaistline = user.child("waistline");
                userWaistline.setValue(pWaistline);
                Firebase userAge = user.child("age");
                userAge.setValue(pAge);
                Toast.makeText(getApplicationContext(), getString(R.string.on_success_registration), Toast.LENGTH_SHORT).show();
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
}
