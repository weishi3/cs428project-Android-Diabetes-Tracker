package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.SessionManager;

public class MainActivity extends AppCompatActivity {
    private SessionManager session;

    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "on create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());
        Button logoutBtn = (Button) findViewById(R.id.logoutBtn);
        Button diagnosisBtn = (Button) findViewById(R.id.diagnosisBtn);

        ImageView profileAvatar = (ImageView) findViewById(R.id.profileAvatar);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();
                finish();
            }
        });

        //Go to profile page when the user click the avatar
        profileAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profile);
            }
        });

        //Go to diagnosis page when the user click the button
        diagnosisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diagnosis = new Intent(getApplicationContext(), DiagnosisActivity.class);
                startActivity(diagnosis);
            }
        });
        TextView welcomeTxt = (TextView) findViewById(R.id.welcomeTxt);
        welcomeTxt.setText(session.getUserDetails().get(SessionManager.KEY_EMAIL));
        Log.d(TAG, "user is " + welcomeTxt.getText().toString());

    }


}
