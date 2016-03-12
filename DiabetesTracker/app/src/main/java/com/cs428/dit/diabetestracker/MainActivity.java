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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.FoodItemLog;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN_ACTIVITY";
    private SessionManager session;
    private TextView mTextCalories;

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

        // Food part
        ImageButton logFoodButton = (ImageButton) findViewById(R.id.button_log_food);
        ImageButton caloriesHistoryButton = (ImageButton) findViewById(R.id.button_see_calories_history);
        LinearLayout caloriesStatsLayout = (LinearLayout) findViewById(R.id.layout_calories_stats);
        mTextCalories = (TextView) findViewById(R.id.total_calories_main);

        // Indicator part
        ImageButton logIndicatorButton = (ImageButton) findViewById(R.id.button_log_indicator);
        ImageButton indicatorHistoryButton = (ImageButton) findViewById(R.id.button_see_indicator_history);


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

        //Go to add food item page
        logFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logFoodIntent = new Intent(getApplicationContext(), AddFoodItemActivity.class);
                startActivity(logFoodIntent);
            }
        });

        //Go to food calories history page
        caloriesHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caloriesHistoryIntent = new Intent(getApplicationContext(), SeeCaloriesActivity.class);
                startActivity(caloriesHistoryIntent);
            }
        });

        //Go to food log page
        //This is the calories card view on click
        caloriesStatsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foodLogIntent = new Intent(getApplicationContext(), FoodLogActivity.class);
                startActivity(foodLogIntent);
            }
        });

        //Go to add indicator page
        logIndicatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIndicatorIntent = new Intent(getApplicationContext(), AddIndicatorActivity.class);
                startActivity(logIndicatorIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        String baseURL = getString(R.string.firebase_url);
        String userStatsURL = "stats/" + session.getUserDetails().get(SessionManager.KEY_EMAIL).toString().replace('.', '!');
        userStatsURL = baseURL + userStatsURL;
        Firebase statsRef = new Firebase(userStatsURL);
        statsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
                final String day = dateformat.format(new Date());
                double tCal = 0.0;

                for (DataSnapshot foodItemLogSnapshot: dataSnapshot.getChildren()) {
                    FoodItemLog oneLog = foodItemLogSnapshot.getValue(FoodItemLog.class);
                    if(day.equals(oneLog.getDate())){
                        tCal+=(oneLog.getFood().getKilocalorie());
                    }
                }

                if(tCal < 0.0){
                    tCal = 0.0;
                }

                mTextCalories.setText(tCal+"");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
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
