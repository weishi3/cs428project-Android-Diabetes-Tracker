package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private final static Boolean loginPressed = true;

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
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        Button registerBtn = (Button) findViewById(R.id.registerBtn);
        //handle login
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLoginOrRegister(loginPressed);
            }
        });
        //handle register
        registerBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLoginOrRegister(!loginPressed);
            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     *
     * @param isLoginPressed A boolean value to check whether login or register is pressed.
     */
    private void attemptLoginOrRegister(Boolean isLoginPressed) {
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
            if (isLoginPressed) {
                login();
            } else {
                register();
            }
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
                session.createLoginSession(mEmail);
                Intent homepage = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homepage);
                finish();//close login page
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // Authenticated failed with error firebaseError
                //TODO: fail to login.
                session.eraseSharedPreference();
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        };

        myFirebaseRef.authWithPassword(mEmail, mPassword, authResultHandler);
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
                Toast.makeText(getApplicationContext(), firebaseError.toString(), Toast.LENGTH_SHORT).show();
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
        //TODO: Replace this with your own logic
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Check strength of password
     *
     * @param password user password
     * @return true if satisfy strength requirement
     */
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}

