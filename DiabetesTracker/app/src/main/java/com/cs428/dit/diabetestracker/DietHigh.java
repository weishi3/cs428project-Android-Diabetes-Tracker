package com.cs428.dit.diabetestracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.drawable.Drawable;

public class DietHigh extends AppCompatActivity {

    ExpandableListAdapter1 listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, Drawable> listImageChild;
    Intent intent;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_low);


        saveButton = (Button) findViewById(R.id.saveBtn);
        final Context x = this;

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent saveDiet = new Intent(getApplicationContext(), SaveHighGIActivity.class);
                startActivity(saveDiet);
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
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

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
        listImageChild = new HashMap<String, Drawable>();
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
        pitabread.add("Calories per 100g:275");
        List<String> melbatoast = new ArrayList<String>();
        melbatoast.add("GI:71");
        melbatoast.add("Calories per 100g:390");
        List<String> millet = new ArrayList<String>();
        millet.add("GI:71");
        millet.add("Calories per 100g:378");
        List<String> couscous = new ArrayList<String>();
        couscous.add("GI:65");
        couscous.add("Calories per 100g:112");
        List<String> mango = new ArrayList<String>();
        mango.add("GI:56");
        mango.add("Calories per 100g:60");
        List<String> pineapple = new ArrayList<String>();
        pineapple.add("GI:66");
        pineapple.add("Calories per 100g:50");
        List<String> favabeans = new ArrayList<String>();
        favabeans.add("GI:80");
        favabeans.add("Calories per 100g:88");
        List<String> puffedrice = new ArrayList<String>();
        puffedrice.add("GI:90");
        puffedrice.add("Calories per 100g:402");


        listDataChild.put(listDataHeader.get(0), pitabread); // Header, Child data
        listDataChild.put(listDataHeader.get(1), melbatoast);
        listDataChild.put(listDataHeader.get(2), millet);
        listDataChild.put(listDataHeader.get(3), couscous);
        listDataChild.put(listDataHeader.get(4), mango);
        listDataChild.put(listDataHeader.get(5), pineapple);
        listDataChild.put(listDataHeader.get(6), favabeans);
        listDataChild.put(listDataHeader.get(7), puffedrice);


        listImageChild.put(listDataHeader.get(0), getResources().getDrawable(R.drawable.pitabread));
        listImageChild.put(listDataHeader.get(1), getResources().getDrawable(R.drawable.melbatoast));
        listImageChild.put(listDataHeader.get(2), getResources().getDrawable(R.drawable.millet));
        listImageChild.put(listDataHeader.get(3), getResources().getDrawable(R.drawable.couscous));
        listImageChild.put(listDataHeader.get(4), getResources().getDrawable(R.drawable.mango));
        listImageChild.put(listDataHeader.get(5), getResources().getDrawable(R.drawable.pineapple));
        listImageChild.put(listDataHeader.get(6), getResources().getDrawable(R.drawable.favabeans));
        listImageChild.put(listDataHeader.get(7), getResources().getDrawable(R.drawable.puffedrice));
    }

}
