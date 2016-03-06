package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        session = new SessionManager(getApplicationContext());
        //if logged in, go to main screen.
        if (session.isLoggedIn()) {
            Intent main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(main);
            finish();
        }

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        TextView mForgetPasswordLink = (TextView) findViewById(R.id.forgetPasswordLink);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        //handle login
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        //handle register, go to Registration Activity
        registerBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(register);
            }
        });
        //handle forget password
        mForgetPasswordLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetPassword = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(forgetPassword);
            }
        });

    }

    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

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
            login();
        }
    }

    /**
     * Authenticate user through Firebase
     */
    private void login() {
        //perform login attempt
        final String mEmail = mEmailView.getText().toString();
        final String mPassword = mPasswordView.getText().toString();

        //Reference to Firebase data
        final Firebase myFirebaseRef = new Firebase(getString(R.string.firebase_url));
        // Create a handler to handle the result of the authentication
        final Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                // Authenticated successfully with payload authData
                Firebase ref = myFirebaseRef.child("users").child(mEmail.replace('.','!'));
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        HashMap<String, Object> userDetails = (HashMap<String, Object>) snapshot.getValue();
                        //null check
                        if (userDetails == null){
                            userDetails = new HashMap<String, Object>();
                        }
                        session.createLoginSession(mEmail, userDetails);
                    }

                    @Override
                    public void onCancelled(FirebaseError error) {
                        session.createLoginSession(mEmail, new HashMap<String, Object>());
                    }
                });
                Intent homepage = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homepage);
                finish();//close login page
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // Authenticated failed with error firebaseError
                session.eraseSharedPreference();
                if (firebaseError.getCode() == FirebaseError.INVALID_PASSWORD) {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                } else {
                    Toast.makeText(getApplicationContext(), firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        myFirebaseRef.authWithPassword(mEmail, mPassword, authResultHandler);
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

