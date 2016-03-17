package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.Indicator;
import com.cs428.dit.diabetestracker.helpers.IndicatorItemLog;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.HashMap;

public class IndicatorLogActivity extends AppCompatActivity {

    Firebase mRef;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SessionManager session;
    public String date;
    public HashMap<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_log);
        session = new SessionManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);
        FloatingActionButton updateIndicatorButton = (FloatingActionButton) findViewById(R.id.fab_update_indicator);
        setRefToUserStats();
        //Go to AddFoodItemActivity
        updateIndicatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIndicatorIntent = new Intent(getApplicationContext(), AddIndicatorActivity.class);
                startActivity(updateIndicatorIntent);
            }
        });
    }

    private void setRefToUserStats() {
        mRef = new Firebase(getString(R.string.firebase_url));
        String userStatsURL = "userstats/"+session.getUserDetails().get(SessionManager.KEY_EMAIL);
        userStatsURL = userStatsURL.replace('.', '!');
        Log.d("USER_EMAIL", userStatsURL);
        mRef = mRef.child(userStatsURL);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for (DataSnapshot indicatorSnapshot: snapshot.getChildren()) {
//                    map.put(indicatorSnapshot.getKey().toString(), indicatorSnapshot.getValue());
//
//                }
//            }
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                System.out.println("The read failed: " + firebaseError.getMessage());
//            }
//        });

//        FirebaseRecyclerAdapter<IndicatorItemLog, MessageViewHolder> adapter =
//                new FirebaseRecyclerAdapter<IndicatorItemLog, MessageViewHolder>(
//                        IndicatorItemLog.class, R.layout.recycler_list_indicator_section, MessageViewHolder.class, mRef) {
//                    @Override
//                    protected void populateViewHolder(MessageViewHolder messageViewHolder, IndicatorItemLog indicatorItemLog, int i) {
//                        messageViewHolder.mDate.setText(indicatorItemLog.getDate());
//                        Indicator indicator = indicatorItemLog.getIndicator();
//                        messageViewHolder.mWeight.setText(indicator.getWeight() + " kg");
//                        messageViewHolder.mBloodPressure.setText(indicator.getBloodPressure()+" mmHg");
//                        messageViewHolder.mBloodSugar.setText(indicator.getBloodSugar()+" mmol/L");
//
//                    }
//                };
//
//        mRecyclerView.setAdapter(adapter);

    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView mDate;
        TextView mWeight;
        TextView mBloodPressure;
        TextView mBloodSugar;
        public MessageViewHolder(View v) {
            super(v);
            mDate = (TextView) v.findViewById(R.id.date_view_recycler);
            mWeight = (TextView) v.findViewById(R.id.weight_view_recycler);
            mBloodPressure = (TextView) v.findViewById(R.id.blood_pressure_view_recycler);
            mBloodSugar = (TextView) v.findViewById(R.id.blood_sugar_view_recycler);
        }
    }
}
