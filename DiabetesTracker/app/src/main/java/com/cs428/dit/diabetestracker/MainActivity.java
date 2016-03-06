package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
        ImageView profileAvatar = (ImageView) findViewById(R.id.profileAvatar);
        CardView diagnosisCard = (CardView) findViewById(R.id.diagnosis_card_view);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Go to profile page when the user click the avatar
        profileAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profile);
            }
        });

        //Go to diagnosis page when the user click the card
        diagnosisCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diagnosis = new Intent(getApplicationContext(), DiagnosisActivity.class);
                startActivity(diagnosis);
            }
        });

        TextView welcomeTxt = (TextView) findViewById(R.id.welcomeTxt);
        welcomeTxt.setText((String)session.getUserDetails().get(SessionManager.KEY_EMAIL));
        Log.d(TAG, "user is " + welcomeTxt.getText().toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //handle logout
        if (id == R.id.action_logout) {
            session.logoutUser();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
