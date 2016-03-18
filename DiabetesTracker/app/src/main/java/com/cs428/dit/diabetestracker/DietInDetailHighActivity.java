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

public class DietInDetailHighActivity extends AppCompatActivity {

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
        listDataHeader.add("Pita Bread");
        listDataHeader.add("Melba Toast");
        listDataHeader.add("Bulgar");
        listDataHeader.add("Couscous");
        listDataHeader.add("Mango");
        listDataHeader.add("Pineapple");
        listDataHeader.add("Fava Beans");
        listDataHeader.add("Puffed Rice");

        // Adding child data
        List<String> pitabread = new ArrayList<String>();
        pitabread.add("GI:58");
        List<String> melbatoast = new ArrayList<String>();
        melbatoast.add("GI:71");
        List<String> millet = new ArrayList<String>();
        millet.add("GI:71");
        List<String> couscous = new ArrayList<String>();
        couscous.add("GI:65");
        List<String> mango = new ArrayList<String>();
        mango.add("GI:56");
        List<String> pineapple = new ArrayList<String>();
        pineapple.add("GI:66");
        List<String> favabeans = new ArrayList<String>();
        favabeans.add("GI:80");
        List<String> puffedrice = new ArrayList<String>();
        puffedrice.add("GI:90");


        listDataChild.put(listDataHeader.get(0), pitabread); // Header, Child data
        listDataChild.put(listDataHeader.get(1), melbatoast);
        listDataChild.put(listDataHeader.get(2), millet);
        listDataChild.put(listDataHeader.get(3), couscous);
        listDataChild.put(listDataHeader.get(4), mango);
        listDataChild.put(listDataHeader.get(5), pineapple);
        listDataChild.put(listDataHeader.get(6), favabeans);
        listDataChild.put(listDataHeader.get(7), puffedrice);
    }



}