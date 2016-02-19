package com.cs428.dit.diabetestracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.SessionManager;

public class MainActivity extends AppCompatActivity {
    private SessionManager session;
    private TextView welcomeTxt;
    private Button logoutBtn;

    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "on create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
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
        welcomeTxt = (TextView) findViewById(R.id.welcomeTxt);
        welcomeTxt.setText(session.getUserDetails().get(SessionManager.KEY_EMAIL));
        Log.d(TAG, "user is " + welcomeTxt.getText().toString());

    }
}
