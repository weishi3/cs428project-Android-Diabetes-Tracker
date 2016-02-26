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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Firebase.setAndroidContext(this);
        mEmailView = (EditText) findViewById(R.id.registrationEmail);
        mPasswordView = (EditText) findViewById(R.id.registrationPW);
        mConfirmPasswordView = (EditText) findViewById(R.id.registrationConfirmPW);

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

        // Store values at the time of the register attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

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
