package com.cs428.dit.diabetestracker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.cs428.dit.diabetestracker.helpers.FoodList;
import com.cs428.dit.diabetestracker.helpers.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.util.Log;

public class CheckSavedDietActivity extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private Context ac;
    private SessionManager session;
    private HashMap<String, Object> userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_saved);
        ac = this;
        session = new SessionManager(getApplicationContext());
        userDetails = session.getUserDetails();
        String userEmail = userDetails.get("email").toString().replace('.', '!');

        ArrayList<String[]> dietLists = FoodList.retrieve(ac, userEmail);
        if(dietLists == null){
            Log.d("tag","null here");
        }

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData(dietLists);

        // get the listview

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });
        final Context x = this;
    }

    /*
     * Preparing the list data
     */

    private void prepareListData(ArrayList<String[]> foodLists) {
        if(foodLists.isEmpty()){
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();
            listDataHeader.add("N/A");
            List<String> foodList = new ArrayList<String>();
            foodList.add("N/A");
            listDataChild.put(listDataHeader.get(0), foodList);
            return;
        }

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        for (String[] foodList : foodLists) {
            listDataHeader.add(foodList[0]);
            List<String> foods = new ArrayList<String>();
            for (int i = 1; i < foodList.length; i ++ ) {
                foods.add(foodList[i]);
            }
            listDataChild.put(foodList[0], foods);
        }
        /*
        // Adding child data
        listDataHeader.add(diagnosisTitle);
        listDataHeader.add(foodSug);

        // Adding child data
        List<String> suggestions = suggestionContents;
        if (suggestions==null){
            suggestions = new ArrayList<String>();
            suggestions.add("N/A");
        }

        List<String> suggestedDiets = new ArrayList<String>();

        suggestedDiets.add("lowGI");
        suggestedDiets.add("mediumGI");
        suggestedDiets.add("highGI");

        listDataChild.put(listDataHeader.get(0), suggestions); // Header, Child data
        listDataChild.put(listDataHeader.get(1), suggestedDiets);
        */
    }
}
