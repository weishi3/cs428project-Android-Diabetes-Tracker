package com.cs428.dit.diabetestracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.drawable.Drawable;

import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.cs428.dit.diabetestracker.helpers.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cs428.dit.diabetestracker.helpers.StringRunner;

public class DietLow extends AppCompatActivity {

    ExpandableListAdapter1 listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String,Drawable> listImageChild;
    Intent intent;
    private Button saveButton;
    private Button checkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_low);


        saveButton = (Button) findViewById(R.id.saveBtn);
        checkButton = (Button) findViewById(R.id.checkBtn);
        final Context x = this;

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent saveDiet = new Intent(getApplicationContext(), SaveLowGIDietActivity.class);
                startActivity(saveDiet);
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkSavedDiet = new Intent(getApplicationContext(), CheckSavedDietActivity.class);
                startActivity(checkSavedDiet);
            }
        });


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp2);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter1(this, listDataHeader, listDataChild, listImageChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();

                return false;
            }
        });

    }

    private void prepareListData() {
        prepareListHeaderData();
        prepareListItemData();
        prepareListImageData();
    }

    private void prepareListHeaderData() {
        listDataHeader = new ArrayList<String>();

        listDataHeader.add("Rice Noodles");
        listDataHeader.add("Sweet Corn");
        listDataHeader.add("Lentils");
        listDataHeader.add("Beans");
        listDataHeader.add("Yogurt");
        listDataHeader.add("Greek Yogurt");
        listDataHeader.add("Plums");
        listDataHeader.add("Oranges");
    }

    private void prepareListItemData() {
        listDataChild = new HashMap<String, List<String>>();

        List<String> ricenoodles = new ArrayList<String>();
        ricenoodles.add("GI:40\nCalories per 100g:109");
        List<String> sweetcorn = new ArrayList<String>();
        sweetcorn.add("GI:47\nCalories per 100g:86");
        List<String> lentils = new ArrayList<String>();
        lentils.add("GI:21\nCalories per 100g:116");
        List<String> beans = new ArrayList<String>();
        beans.add("GI:30\nCalories per 100g:347");
        List<String> yogurt = new ArrayList<String>();
        yogurt.add("GI:19\nCalories per 100g:59");
        List<String> greekyogurt = new ArrayList<String>();
        greekyogurt.add("GI:19\nCalories per 100g:59");
        List<String> plums = new ArrayList<String>();
        plums.add("GI:24\nCalories per 100g:46");
        List<String> oranges = new ArrayList<String>();
        oranges.add("GI:40\nCalories per 100g:47");

        listDataChild.put(listDataHeader.get(0), ricenoodles); // Header, Child data
        listDataChild.put(listDataHeader.get(1), sweetcorn);
        listDataChild.put(listDataHeader.get(2), lentils);
        listDataChild.put(listDataHeader.get(3), beans);
        listDataChild.put(listDataHeader.get(4), yogurt);
        listDataChild.put(listDataHeader.get(5), greekyogurt);
        listDataChild.put(listDataHeader.get(6), plums);
        listDataChild.put(listDataHeader.get(7), oranges);
    }

    private void prepareListImageData() {
        listImageChild = new HashMap<String,Drawable>();

        listImageChild.put(listDataHeader.get(0),getResources().getDrawable(R.drawable.ricenoodles));
        listImageChild.put(listDataHeader.get(1), getResources().getDrawable(R.drawable.sweetcorn));
        listImageChild.put(listDataHeader.get(2), getResources().getDrawable(R.drawable.lentils));
        listImageChild.put(listDataHeader.get(3),getResources().getDrawable(R.drawable.beans));
        listImageChild.put(listDataHeader.get(4), getResources().getDrawable(R.drawable.yogurt));
        listImageChild.put(listDataHeader.get(5),getResources().getDrawable(R.drawable.greekyogurt));
        listImageChild.put(listDataHeader.get(6), getResources().getDrawable(R.drawable.plums));
        listImageChild.put(listDataHeader.get(7), getResources().getDrawable(R.drawable.oranges));
    }



}