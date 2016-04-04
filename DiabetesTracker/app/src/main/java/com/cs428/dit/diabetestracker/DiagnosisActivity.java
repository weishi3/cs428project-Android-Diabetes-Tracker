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

public class DiagnosisActivity extends AppCompatActivity{

    //float BMI, float waistline, int age, int blood_pressure, boolean gender, boolean familyHistory

    final Activity thisAct= this;
    public HashMap<String, Object> userMap;
    // instances for expandable lists
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<String> suggestionContents=null;
    HashMap<String, List<String>> listDataChild;
    private String foodSug="Generally Suggested Diets";
    private String diagnosisTitle="N/A Before Running Diagnosis!";
    private LinearLayout mLayout;
    private EditText mEditText;
    private Button mButton;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        mLayout = (LinearLayout) findViewById(R.id.linearLayout);
        session = new SessionManager(getApplicationContext());
        userMap = session.getUserDetails();

        //mEditText = (EditText) findViewById(R.id.)
        mButton = (Button) findViewById(R.id.button);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float BMI = (float) userMap.get("BMI");
                float waistline = (float) userMap.get("waistline");
                int age = (int) userMap.get("age");
                int bloodPressure = (int) userMap.get("bloodPressure");
                boolean familyHistory = (boolean) userMap.get("familyHistory");
                boolean gender = (boolean) userMap.get("gender");
                User u= new User(BMI,waistline,age,bloodPressure,familyHistory,gender);

                boolean sedentary=false;
                int ExerciseT=60;
                Double HDL_C=0.88;
                Double TG=2.3;
                int weightB=3;
                boolean GDM =false;
                boolean diagnosedD=false;


                if (userMap.containsKey("sedentaryJob"))
                    sedentary = (boolean) userMap.get("sedentaryJob");

                u.setSedentaryJob(sedentary);

                u.setExerciseT(ExerciseT);
                u.setDiagnosedD(diagnosedD);
                u.setGDM(GDM);
                u.setWeightB(weightB);
                u.setHDL_C(HDL_C);
                u.setTG(TG);

                String score = u.getScore();
                foodSug=u.generateSuggestionD();
                suggestionContents= u.generateSuggestion();
                diagnosisTitle="Diagnosis is over here";

                mLayout.removeAllViews();
                mLayout.addView(createNewTextView(score));


                prepareListData();
                expListView.deferNotifyDataSetChanged();
                listAdapter = new ExpandableListAdapter(thisAct, listDataHeader, listDataChild);
                expListView.setAdapter(listAdapter);

            }

            //for the button running diag
            private View createNewTextView(String score) {
                final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                final TextView textView = new TextView(thisAct);
                textView.setTextSize(50);
                textView.setLayoutParams(lparams);
                textView.setText("The score is: " + score);
                return textView;
            }






        });





        //add yiyu's
        // get the listview


        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });
        final Context x = this;
        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) listAdapter.getChild(groupPosition, childPosition);
                switch(selected){
                    case "lowGI":
                        Intent intent = new Intent(DiagnosisActivity.this, DietLow.class);
                        startActivity(intent);
                        break;
                    case "mediumGI":
                        intent = new Intent(DiagnosisActivity.this,DietMedium.class);
                        startActivity(intent);
                        break;
                    case "highGI":
                        intent = new Intent(DiagnosisActivity.this,DietHigh.class);
                        startActivity(intent);
                        break;
                }
                /*
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
               */
                return false;
            }
        });
    }



    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();



        // Adding child data
        listDataHeader.add(diagnosisTitle);

        listDataHeader.add(foodSug);


        // Adding child data
        List<String> suggestions = suggestionContents;
        if (suggestions==null){
            suggestions = new ArrayList<String>();
            suggestions.add("N/A");
        }
       // suggestions.add("suggestions1");
       // suggestions.add("suggestions2");
       // suggestions.add("suggestions3");
       // suggestions.add("suggestions4");


        List<String> suggestedDiets = new ArrayList<String>();


        suggestedDiets.add("lowGI");
        suggestedDiets.add("mediumGI");
        suggestedDiets.add("highGI");


        listDataChild.put(listDataHeader.get(0), suggestions); // Header, Child data
        listDataChild.put(listDataHeader.get(1), suggestedDiets);
    }


}
