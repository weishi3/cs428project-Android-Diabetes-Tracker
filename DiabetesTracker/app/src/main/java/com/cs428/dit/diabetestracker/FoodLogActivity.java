package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.Food;
import com.cs428.dit.diabetestracker.helpers.FoodItemLog;
import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * This activity is the page that displays a list of food items to users.
 */
public class FoodLogActivity extends AppCompatActivity {
    // Reference to Firebase
    Firebase mRef;
    // RecyclerView
    RecyclerView mRecyclerView;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_log);
        FloatingActionButton addFoodItemFab = (FloatingActionButton) findViewById(R.id.fab_add_food_item);
        session = new SessionManager(this);

        // Set RecyclerView and define its position and size
        mRecyclerView = (RecyclerView) findViewById(R.id.food_log_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setRefToUserStats();

        // Go to AddFoodItemActivity
        addFoodItemFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFoodItemIntent = new Intent(getApplicationContext(), AddFoodItemActivity.class);
                startActivity(addFoodItemIntent);
            }
        });
    }

    /**
     * Initialize Firebase ref to user stats.
     * Change '.' to '!'.
     */
    private void setRefToUserStats() {
        mRef = new Firebase(getString(R.string.firebase_url));
        String userStatsURL = getString(R.string.food_item_storage_endpoint) + session.getUserDetails().get(SessionManager.KEY_EMAIL);
        userStatsURL = userStatsURL.replace('.', '!');
        mRef = mRef.child(userStatsURL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Use data from Firebase to fill the RecyclerView
        FirebaseRecyclerAdapter<FoodItemLog, MessageViewHolder> adapter =
                new FirebaseRecyclerAdapter<FoodItemLog, MessageViewHolder>(
                        FoodItemLog.class,
                        R.layout.recycler_list_food_section,
                        MessageViewHolder.class,
                        mRef
                ) {
                    @Override
                    protected void populateViewHolder(MessageViewHolder messageViewHolder, FoodItemLog d, int i) {
                        messageViewHolder.mDate.setText(d.getDate());
                        Food f = d.getFood();
                        messageViewHolder.mFoodNameText.setText(f.getName());
                        messageViewHolder.mSugar.setText(f.getSugarInGram() + " " + getString(R.string.sugar_unit));
                        messageViewHolder.mCalories.setText(f.getKilocalorie() + " " + getString(R.string.calories_unit));
                    }
                };
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * MessageViewHolder Class is used to hold the information of a food item.
     */
    public static class MessageViewHolder
            extends RecyclerView.ViewHolder {
        TextView mDate;
        TextView mCalories;
        TextView mSugar;
        TextView mFoodNameText;

        /**
         * Define how to fill the food information given a view.
         *
         * @param v the view to be populate
         */
        public MessageViewHolder(View v) {
            super(v);
            mCalories = (TextView) v.findViewById(R.id.calories_view_recycler);
            mSugar = (TextView) v.findViewById(R.id.sugar_view_recycler);
            mDate = (TextView) v.findViewById(R.id.date_view_recycler);
            mFoodNameText = (TextView) v.findViewById(R.id.food_name_view_recycler);
        }
    }
}
