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

import com.cs428.dit.diabetestracker.helpers.SessionManager;
import com.cs428.dit.diabetestracker.helpers.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DietInDetailLowActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Intent intent;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_in_detail);


        mButton = (Button) findViewById(R.id.back);
        final Context x = this;
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(x,DiagnosisActivity.class);
                startActivity(i);
            }
        });


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp2);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

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
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        // Adding child data
        listDataHeader.add("Rice Noodles");
        listDataHeader.add("Sweet Corn");
        listDataHeader.add("Lentils");
        listDataHeader.add("Beans");
        listDataHeader.add("Yogurt");
        listDataHeader.add("Greek Yogurt");
        listDataHeader.add("Plums");
        listDataHeader.add("Oranges");

        // Adding child data
        List<String> ricenoodles = new ArrayList<String>();
        ricenoodles.add("GI:40");
        List<String> sweetcorn = new ArrayList<String>();
        sweetcorn.add("GI:47");
        List<String> lentils = new ArrayList<String>();
        lentils.add("GI:21");
        List<String> beans = new ArrayList<String>();
        beans.add("GI:30");
        List<String> yogurt = new ArrayList<String>();
        yogurt.add("GI:19");
        List<String> greekyogurt = new ArrayList<String>();
        greekyogurt.add("GI:19");
        List<String> plums = new ArrayList<String>();
        plums.add("GI:24");
        List<String> oranges = new ArrayList<String>();
        oranges.add("GI:40");


        listDataChild.put(listDataHeader.get(0), ricenoodles); // Header, Child data
        listDataChild.put(listDataHeader.get(1), sweetcorn);
        listDataChild.put(listDataHeader.get(2), lentils);
        listDataChild.put(listDataHeader.get(3), beans);
        listDataChild.put(listDataHeader.get(4), yogurt);
        listDataChild.put(listDataHeader.get(5), greekyogurt);
        listDataChild.put(listDataHeader.get(6), plums);
        listDataChild.put(listDataHeader.get(7), oranges);
    }



}