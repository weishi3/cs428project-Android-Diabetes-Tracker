package com.cs428.dit.diabetestracker;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.FoodItemLog;
import com.cs428.dit.diabetestracker.helpers.IndicatorItemLog;
import com.cs428.dit.diabetestracker.helpers.Monitor;
import com.cs428.dit.diabetestracker.helpers.MonitorPressure;
import com.cs428.dit.diabetestracker.helpers.MonitorSetting;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.cs428.dit.diabetestracker.helpers.ShowcaseManager;
import com.cs428.dit.diabetestracker.helpers.ShowcaseParam;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/*
 * This activity showes the main page and does all the navigation to different activities
 * In this page, user will be able to view the calories and the indicator level in a card view
 */

public class MainActivity extends AppCompatActivity {
    /*
     * Textview mTextColories, units, mTextBloodSugar: The text appears on the card view
     * Session: The session object which stores all the user information
     * ShowcaseManager: The tutorial object
     * notificationManager: the notification on top for the monitor plan
     * alertDialog: the alert window for monitor plan
     */
    private static final String TAG = "MAIN_ACTIVITY";
    private SessionManager session;
    private TextView mTextCalories;
    private TextView units;
    private TextView mTextBloodSugar;
    public MonitorPressure monitorP;
    public Monitor monitor;
    public int countPeriod =-1;
    public String toDisplay;
    public ArrayList<String> toMonitor;
    private ShowcaseManager showcaseManager;
    public NotificationCompat.Builder mBuilder;
    public  NotificationManager mNotificationManager;
    public AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        ImageButton monitorPlanButton = (ImageButton) findViewById(R.id.button_monitor_plan);
        ImageButton indicatorHistoryButton = (ImageButton) findViewById(R.id.button_see_indicator_history);
        LinearLayout indicatorLayout = (LinearLayout) findViewById(R.id.layout_indicator_stats);
        mTextBloodSugar = (TextView) findViewById(R.id.total_indicator_main);

        //step counter
        ImageButton walkStepButton= (ImageButton)findViewById(R.id.button_step_counter);
        walkStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stepCounterIntent=new Intent(getApplicationContext(),StepCounterActivity.class);
                startActivity(stepCounterIntent);
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

        //Set monitor plan
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

        //Go to indicator log page
        indicatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent indicatorLogIntent = new Intent(getApplicationContext(), IndicatorLogActivity.class);
                startActivity(indicatorLogIntent);
            }
        });

        // Show tutorial
        showTutorial();

    }

    /**
     * Show tutorial on views
     */
    private void showTutorial() {
        showcaseManager = new ShowcaseManager();
        ShowcaseView.Builder showcaseView = null;
        showcaseManager.addNextShowcaseParam(new ShowcaseParam(this, R.id.profileAvatar, getString(R.string.tutorial_profile_avatar_title), getString(R.string.tutorial_profile_avatar_content), 0, showcaseView));
        showcaseManager.addNextShowcaseParam(new ShowcaseParam(this, R.id.diagnosis_card_view, getString(R.string.tutorial_diagnosis_title), getString(R.string.tutorial_diagnosis_content), 1, showcaseView));

        showcaseManager.addNextShowcaseParam(new ShowcaseParam(this, R.id.calories_card_view, getString(R.string.tutorial_calories_card_view_title), getString(R.string.tutorial_calories_card_view_content), 2, showcaseView));
        showcaseManager.addNextShowcaseParam(new ShowcaseParam(this, R.id.button_step_counter, getString(R.string.tutorial_calories_step_title), getString(R.string.tutorial_calories_step_content), 3, showcaseView));
        showcaseManager.addNextShowcaseParam(new ShowcaseParam(this, R.id.button_log_food, getString(R.string.tutorial_calories_add_food_title), getString(R.string.tutorial_calories_add_food_content), 4, showcaseView));
        showcaseManager.addNextShowcaseParam(new ShowcaseParam(this, R.id.button_see_calories_history, getString(R.string.tutorial_calories_see_history_title), getString(R.string.tutorial_calories_see_history_content), 5, showcaseView));

        showcaseManager.addNextShowcaseParam(new ShowcaseParam(this, R.id.indicator_card_view, getString(R.string.tutorial_indicator_card_view_title), getString(R.string.tutorial_indicator_card_view_content), 6, showcaseView));
        showcaseManager.addNextShowcaseParam(new ShowcaseParam(this, R.id.button_monitor_plan, getString(R.string.tutorial_indicator_settings_title), getString(R.string.tutorial_indicator_settings_content), 7, showcaseView));
        showcaseManager.addNextShowcaseParam(new ShowcaseParam(this, R.id.button_log_indicator, getString(R.string.tutorial_indicator_add_title), getString(R.string.tutorial_indicator_add_content), 8, showcaseView));
        showcaseManager.addNextShowcaseParam(new ShowcaseParam(this, R.id.button_see_indicator_history, getString(R.string.tutorial_indicator_history_title), getString(R.string.tutorial_indicator_history_content), 9, showcaseView));

        showcaseManager.buildAllShowcases();
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

        //initialize the notification for adding indicator here!
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_add_black_24dp)
                .setContentTitle(getString(R.string.indicator_reminder_title))
                .setAutoCancel(true)
                .setContentText(getString(R.string.indicator_reminder_text_body));
        Intent resultIntent = new Intent(this, AddIndicatorActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(AddIndicatorActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //initialize manager and set id
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(111, mBuilder.build());

        //set firebase url
        String baseURL = getString(R.string.firebase_url);
        String userStatsURL = "foodStats/" + (session.getUserDetails().get(SessionManager.KEY_EMAIL) + "").replace('.', '!');
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

        //set firebase url for monitor setting
        String userMonitorSettingURL = "monitorsetting/" + (session.getUserDetails().get(SessionManager.KEY_EMAIL)+"").replace('.', '!');
        userMonitorSettingURL = baseURL + userMonitorSettingURL;
        Firebase monitorSettingRef = new Firebase(userMonitorSettingURL);
        monitorSettingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MonitorSetting sets = dataSnapshot.getValue(MonitorSetting.class);
                if (sets==null) {
                    Firebase mRef = new Firebase(getString(R.string.firebase_url));
                    String userSettingURL = "monitorsetting/" + session.getUserDetails().get(SessionManager.KEY_EMAIL);
                    userSettingURL = userSettingURL.replace('.', '!');
                    ArrayList<String> tempToMonitor= new ArrayList<String>();
                    tempToMonitor.add("bloodSugar");
                    MonitorSetting thisSetting = new MonitorSetting(3, "bloodSugar", tempToMonitor);
                    mRef = mRef.child(userSettingURL);
                    mRef.setValue(thisSetting);
                    countPeriod = 3;
                    toDisplay = "bloodSugar";
                    toMonitor = tempToMonitor;
                }else {
                    countPeriod = sets.numDaysMonitor;
                    toDisplay = sets.indicatorType;
                    toMonitor = sets.warningMessage;
                }

                // Indicator
                String baseURL2 = getString(R.string.firebase_url);
                //add by weishi

                //what would be shown on card?
                //dialog
                monitor = new Monitor(3);
                monitorP = new MonitorPressure(3);
                String userIndicatorURL = "userstats/" + (session.getUserDetails().get(SessionManager.KEY_EMAIL) + "").replace('.', '!');
                userIndicatorURL = baseURL2 + userIndicatorURL;
                Firebase indicatorRef = new Firebase(userIndicatorURL);
                indicatorRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (alertDialog!=null)
                            alertDialog.dismiss();
                        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                        final String day = dateformat.format(new Date());
                        //may not necessary to be blood sugar
                        double toDisplayOnIndicatorCard = 0.0;
                        System.out.println(toDisplay);

                        //just for double check
                        if (countPeriod > -1) {
                            monitor.setCount(countPeriod);
                            monitorP.count = countPeriod;
                        }
                        units = (TextView) findViewById(R.id.unit);

                        //now check for what to display on the card view
                        if (toDisplay == null) toDisplay = "bloodSugar";

                        //firebase stuffs
                        toDisplayOnIndicatorCard = IndicatorCardController(dataSnapshot, day, toDisplayOnIndicatorCard);

                        monitorP.detectWarning();
                        monitor.detectWarning();
                        String toOutput = "";


                        //detect the condition when to display warnings

                        toOutput = checkIfNeedToShowAlert(toOutput);

                        if (!toOutput.equals("")) {
                            alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("A KIND REMINDER");
                            alertDialog.setMessage("Your " + toOutput + " may indicate that you are in a bad health condition!");
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });

                            alertDialog.setIcon(R.drawable.cross);

                            if (!isFinishing()) {
                                alertDialog.show();
                            }
                        }

                        //just for double check for invalid value

                        if (toDisplayOnIndicatorCard < 0.0) {
                            toDisplayOnIndicatorCard = 0.0;
                        }

                        mTextBloodSugar.setText(toDisplayOnIndicatorCard + "");

                        //cancel the notification when user has input indicator data today
                        if (toDisplayOnIndicatorCard!=0.0)
                            mNotificationManager.cancel(111);
                    }


                    /**
                     * WE WOULD CHECK toOutput to determine whether to display alert
                     * @param toOutput
                     * @return
                     */
                    private String checkIfNeedToShowAlert(String toOutput) {
                        if (toMonitor == null) toMonitor = new ArrayList<String>();
                        if (toMonitor.contains("bloodSugar") && monitor.getWarning()) {
                            toOutput += "Blood Sugar Stats";

                        }
                        if (toMonitor.contains("bloodPressure") && monitorP.warning) {
                            if (toOutput == "") toOutput += "Blood Pressure Stats";
                            else toOutput += " and Blood Pressure Stats";

                        }
                        return toOutput;
                    }

                    /**
                     * set what is on the indicator card view
                     * @param dataSnapshot what we get from firebase
                     * @param day date
                     * @param toDisplayOnIndicatorCard the string shown on indicator card view
                     * @return
                     */
                    private double IndicatorCardController(DataSnapshot dataSnapshot, String day, double toDisplayOnIndicatorCard) {
                        if (toDisplay.equals("bloodSugar")) {
                            units.setText("mmol/L");
                            for (DataSnapshot indicatorItemLogSnapshot : dataSnapshot.getChildren()) {
                                IndicatorItemLog oneLog = indicatorItemLogSnapshot.getValue(IndicatorItemLog.class);
                                if (day.equals(oneLog.getDate())) {
                                    toDisplayOnIndicatorCard = (oneLog.getIndicator().getBloodSugar());
                                }
                                monitor.addBloodSugar(oneLog.getIndicator().getBloodSugar());
                                monitorP.addBloodPressure(oneLog.getIndicator().getBloodPressure());
                            }
                        }
                        if (toDisplay.equals("bloodPressure")) {
                            units.setText("mmHg");
                            for (DataSnapshot indicatorItemLogSnapshot : dataSnapshot.getChildren()) {
                                IndicatorItemLog oneLog = indicatorItemLogSnapshot.getValue(IndicatorItemLog.class);
                                if (day.equals(oneLog.getDate())) {
                                    toDisplayOnIndicatorCard = (oneLog.getIndicator().getBloodPressure());
                                }
                                monitor.addBloodSugar(oneLog.getIndicator().getBloodSugar());
                                monitorP.addBloodPressure(oneLog.getIndicator().getBloodPressure());
                            }
                        }

                        if (toDisplay.equals("weight")) {
                            units.setText("kg");
                            for (DataSnapshot indicatorItemLogSnapshot : dataSnapshot.getChildren()) {
                                IndicatorItemLog oneLog = indicatorItemLogSnapshot.getValue(IndicatorItemLog.class);
                                if (day.equals(oneLog.getDate())) {
                                    toDisplayOnIndicatorCard = (oneLog.getIndicator().getWeight());
                                }
                                monitor.addBloodSugar(oneLog.getIndicator().getBloodSugar());
                                monitorP.addBloodPressure(oneLog.getIndicator().getBloodPressure());
                            }
                        }
                        return toDisplayOnIndicatorCard;
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
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
