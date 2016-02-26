package com.cs428.dit.diabetestracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        final Firebase ref = new Firebase(getString(R.string.firebase_url));
        final Button mResetBtn = (Button) findViewById(R.id.resetButton);
        final EditText mEmailView = (EditText) findViewById(R.id.resetEmail);

        mResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailView.getText().toString();

                ref.resetPassword(email, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        // password reset email sent
                        Toast.makeText(getApplicationContext(), getString(R.string.prompt_new_password_sent), Toast.LENGTH_SHORT).show();
                        mResetBtn.setClickable(false);
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        // error encountered
                        Toast.makeText(getApplicationContext(), getString(R.string.error_fail_to_sent_reset_pw_email), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        // Once reset button is disabled, it will be enabled when the user modifies the email
        mEmailView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mResetBtn.setClickable(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                mResetBtn.setClickable(true);
            }
        });
    }
}
