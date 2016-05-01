package com.cs428.dit.diabetestracker;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.Indicator;
import com.cs428.dit.diabetestracker.helpers.IndicatorItemLog;
import com.cs428.dit.diabetestracker.helpers.Monitor;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

/*
 * This activity is for user to view his/her indicator history records
 * User is able to see these data
 * Date: The date when the user add the indicator
 * Weight: The weight of user
 * Blood sugar: The blood sugar of user
 * Blood pressure: The blood pressure of user
 */
public class IndicatorLogActivity extends AppCompatActivity {
    /*
     * mRef: Firebase reference
     * mRecyclerView: The recyclerview for presenting the indicator history data
     * mLayoutManager: control the layout for recycler view
     * session: The session object which stores the user information
     * updateIndicatorButton: The button that navigates to addIndicatorActivity
     */
    private Firebase mRef;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SessionManager session;
    private FloatingActionButton updateIndicatorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_log);
        session = new SessionManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        /*
         * use this setting to improve performance if you know that changes
         * in content do not change the layout size of the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        updateIndicatorButton = (FloatingActionButton) findViewById(R.id.fab_update_indicator);
        setRefToUserIndicator();
        updateIndicatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIndicatorIntent = new Intent(getApplicationContext(), AddIndicatorActivity.class);
                startActivity(updateIndicatorIntent);
            }
        });
    }

    /*
     * The function set the firebase reference to the current user's indicator data
     */
    private void setRefToUserIndicator() {
        mRef = new Firebase(getString(R.string.firebase_url));
        String userStatsURL = "userstats/" + session.getUserDetails().get(SessionManager.KEY_EMAIL);
        userStatsURL = userStatsURL.replace('.', '!');
        Log.d("USER_EMAIL", userStatsURL);
        mRef = mRef.child(userStatsURL);
    }

    /*
     * In this function we create the recycler view and get all the indicator data
     * related to that user including weight, blood sugar and blood pressure
     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<IndicatorItemLog, MessageViewHolder> adapter =
                new FirebaseRecyclerAdapter<IndicatorItemLog, MessageViewHolder>(IndicatorItemLog.class,
                        R.layout.recycler_list_indicator_section,
                        MessageViewHolder.class,
                        mRef) {
                    @Override
                    protected void populateViewHolder(MessageViewHolder messageViewHolder, IndicatorItemLog d, int i) {
                        messageViewHolder.mDate.setText(d.getDate());
                        Indicator indicator = d.getIndicator();
                        messageViewHolder.mWeight.setText(indicator.getWeight() + " kg");
                        messageViewHolder.mBloodPressure.setText(indicator.getBloodPressure() + " mmHg");
                        messageViewHolder.mBloodSugar.setText(indicator.getBloodSugar() + " mmol/L");
                    }
                };
        mRecyclerView.setAdapter(adapter);
    }

    /*
     * The function creates a messageviewholder for the firebase recycler adapter
     * In the view holder, there are 4 kinds of information
     * date, weight, blood pressure and blood sugar
     */
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView mDate, mWeight, mBloodPressure, mBloodSugar;
        public MessageViewHolder(View v) {
            super(v);
            mDate = (TextView) v.findViewById(R.id.date_view_recycler);
            mWeight = (TextView) v.findViewById(R.id.weight_view_recycler);
            mBloodPressure = (TextView) v.findViewById(R.id.blood_pressure_view_recycler);
            mBloodSugar = (TextView) v.findViewById(R.id.blood_sugar_view_recycler);
        }
    }
}
