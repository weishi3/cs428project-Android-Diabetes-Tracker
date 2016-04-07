package com.cs428.dit.diabetestracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.cs428.dit.diabetestracker.helpers.Indicator;
import com.cs428.dit.diabetestracker.helpers.IndicatorItemLog;
import com.cs428.dit.diabetestracker.helpers.Monitor;
import com.cs428.dit.diabetestracker.helpers.MonitorPressure;
import com.cs428.dit.diabetestracker.helpers.MonitorSetting;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN_ACTIVITY";
    private SessionManager session;
    private TextView mTextCalories;
    private TextView units;
    private TextView mTextBloodSugar;
    public MonitorPressure monitorP;
    public Monitor monitor;
    public int countMo=-1;
    public String toDisplay;
    public ArrayList<String> toMonitor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "on create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());
//        session = new SessionManager(this);
        Log.d("ON_CREATE", (session.getUserDetails()+""));

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
        ImageButton monitorPlanButton = (ImageButton) findViewById(R.id.button_monitor_plan);
        ImageButton indicatorHistoryButton = (ImageButton) findViewById(R.id.button_see_indicator_history);
        LinearLayout indicatorLayout = (LinearLayout) findViewById(R.id.layout_indicator_stats);
        mTextBloodSugar = (TextView) findViewById(R.id.total_indicator_main);


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

        monitorPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monitorPlanIntent = new Intent(getApplicationContext(), SetMonitorPlan.class);
                startActivity(monitorPlanIntent);
            }
        });

        //Go to indicator history page
        indicatorHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent indicatorHistoryIntent = new Intent(getApplicationContext(), SeeIndicatorActivity.class);
                startActivity(indicatorHistoryIntent);
            }
        });

        indicatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent indicatorLogIntent = new Intent(getApplicationContext(), IndicatorLogActivity.class);
                startActivity(indicatorLogIntent);
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
        Log.d("USER_EMAIL", session.getUserDetails().toString());
        String userStatsURL = "foodStats/" + (session.getUserDetails().get(SessionManager.KEY_EMAIL)+"").replace('.', '!');
        userStatsURL = baseURL + userStatsURL;
        Firebase statsRef = new Firebase(userStatsURL);
        statsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
                final String day = dateformat.format(new Date());
                double tCal = 0.0;

                for (DataSnapshot foodItemLogSnapshot : dataSnapshot.getChildren()) {
                    FoodItemLog oneLog = foodItemLogSnapshot.getValue(FoodItemLog.class);
                    if (day.equals(oneLog.getDate())) {
                        tCal += (oneLog.getFood().getKilocalorie());
                    }
                }

                if (tCal < 0.0) {
                    tCal = 0.0;
                }

                mTextCalories.setText(tCal + "");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // Indicator
        String baseURL2 = getString(R.string.firebase_url);


        //add by weishi






        monitor=new Monitor(3);
        monitorP=new MonitorPressure(3);
        String userIndicatorURL = "userstats/" + session.getUserDetails().get(SessionManager.KEY_EMAIL).toString().replace('.', '!');
        userIndicatorURL = baseURL2 + userIndicatorURL;
        Firebase indicatorRef = new Firebase(userIndicatorURL);
        indicatorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                final String day = dateformat.format(new Date());

                //may not necessary to be blood sugar
                double bloodSugar = 0.0;



                System.out.println(toDisplay);
                if (countMo!=-1) {
                    monitor.count = countMo;
                    monitorP.count = countMo;
                }
                units = (TextView) findViewById(R.id.unit);

                if (toDisplay==null) toDisplay="bloodSugar";
                if (toDisplay.equals("bloodSugar")){

                      units.setText("mmol/L");
                    for (DataSnapshot indicatorItemLogSnapshot: dataSnapshot.getChildren()) {

                        IndicatorItemLog oneLog = indicatorItemLogSnapshot.getValue(IndicatorItemLog.class);
                        if (day.equals(oneLog.getDate())) {

                            bloodSugar = (oneLog.getIndicator().getBloodSugar());

                        }

                        monitor.addBloodSugar(oneLog.getIndicator().getBloodSugar());
                        monitorP.addBloodPressure(oneLog.getIndicator().getBloodPressure());
                    }
                }

                if (toDisplay.equals("bloodPressure")){
                    units.setText("mmHg");
                    //
                    for (DataSnapshot indicatorItemLogSnapshot: dataSnapshot.getChildren()) {

                        IndicatorItemLog oneLog = indicatorItemLogSnapshot.getValue(IndicatorItemLog.class);
                        if (day.equals(oneLog.getDate())) {

                            bloodSugar = (oneLog.getIndicator().getBloodPressure());

                        }

                        monitor.addBloodSugar(oneLog.getIndicator().getBloodSugar());
                        monitorP.addBloodPressure(oneLog.getIndicator().getBloodPressure());
                    }
                }

                if (toDisplay.equals("weight")){
                    units.setText("kg");
                    //
                    for (DataSnapshot indicatorItemLogSnapshot: dataSnapshot.getChildren()) {
                        IndicatorItemLog oneLog = indicatorItemLogSnapshot.getValue(IndicatorItemLog.class);
                        if (day.equals(oneLog.getDate())) {
                            bloodSugar = (oneLog.getIndicator().getWeight());

                        }

                        monitor.addBloodSugar(oneLog.getIndicator().getBloodSugar());
                        monitorP.addBloodPressure(oneLog.getIndicator().getBloodPressure());
                    }
                }
                monitorP.detectWarning();
                monitor.detectWarning();
                String toOutput="";
                boolean need=false;
                if (toMonitor==null) toMonitor=new ArrayList<String>();
                if (toMonitor.contains("bloodSugar")&&monitor.warning){
                    toOutput+="Blood Sugar Stats";
                    need=true;

                }

                if (toMonitor.contains("bloodPressure")&&monitorP.warning){
                    if (toOutput=="") toOutput+="Blood Pressure Stats";
                    else toOutput+=" and Blood Pressure Stats";
                    need=true;
                }

                // need to modify warning message!
                if (need) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("A KIND REMINDER");
                    alertDialog.setMessage("Your "+ toOutput+" may indicate that you are in a bad health condition!");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    alertDialog.setIcon(R.drawable.cross);
                    alertDialog.show();
                }

                //



                //

                if(bloodSugar < 0.0){
                    bloodSugar = 0.0;
                }

                mTextBloodSugar.setText(bloodSugar+"");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }


        });


        String userMonitorSettingURL = "monitorsetting/" + session.getUserDetails().get(SessionManager.KEY_EMAIL).toString().replace('.', '!');
        userMonitorSettingURL = baseURL2 + userMonitorSettingURL;
        Firebase monitorSettingRef = new Firebase(userMonitorSettingURL);
        monitorSettingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MonitorSetting sets = dataSnapshot.getValue(MonitorSetting.class);
                if (sets==null) {
                    Firebase mRef = new Firebase(getString(R.string.firebase_url));
                    String userSettingURL = "monitorsetting/" + session.getUserDetails().get(SessionManager.KEY_EMAIL);
                    userSettingURL = userSettingURL.replace('.', '!');
                    MonitorSetting ms = new MonitorSetting(3, "bloodSugar", new ArrayList<String>());
                    mRef = mRef.child(userSettingURL);
                    mRef.setValue(ms);


                }
                sets = dataSnapshot.getValue(MonitorSetting.class);
                countMo=sets.numDaysMonitor;
                toDisplay=sets.indicatorType;
                toMonitor=sets.warningMessage;



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
