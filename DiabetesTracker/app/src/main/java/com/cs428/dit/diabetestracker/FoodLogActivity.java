package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class FoodLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_log);
        FloatingActionButton addFoodItemFab = (FloatingActionButton) findViewById(R.id.fab_add_food_item);

        //go to AddFoodItemActivity
        addFoodItemFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFoodItemIntent = new Intent(getApplicationContext(), AddFoodItemActivity.class);
                startActivity(addFoodItemIntent);
            }
        });
    }
}
