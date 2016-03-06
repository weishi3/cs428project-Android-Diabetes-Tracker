package com.cs428.dit.diabetestracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;

import com.cs428.dit.diabetestracker.helpers.User;

public class DiagnosisActivity extends AppCompatActivity {

    //float BMI, float waistline, int age, int bloodPressure, boolean gender, boolean familyHistory

    public float BMI = 25;
    public int waistline = 80;
    public int age = 22;
    public int bloodPressure = 130;
    public boolean familyHistory = false;
    public boolean gender = false;

    private LinearLayout mLayout;
    private EditText mEditText;
    private Button mButton;

    // instances for expandable lists
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        mLayout = (LinearLayout) findViewById(R.id.linearLayout);
        //mEditText = (EditText) findViewById(R.id.)
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u= new User(BMI,waistline,age,bloodPressure,familyHistory,gender);
                String score = String.valueOf(u.getScore());
                mLayout.removeAllViews();
                mLayout.addView(createNewTextView(score));

            }
        });





        //add yiyu's
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

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

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
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




    //for the button running diag
    private View createNewTextView(String score) {
        final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setTextSize(50);
        textView.setLayoutParams(lparams);
        textView.setText("The score is: " + score);
        return textView;
    }







    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Suggestions");
        listDataHeader.add("Suggested Diets");

        // Adding child data
        List<String> suggestions = new ArrayList<String>();
        suggestions.add("suggestions1");
        suggestions.add("suggestions2");
        suggestions.add("suggestions3");
        suggestions.add("suggestions4");


       // List<String> suggestedDiets = new ArrayList<String>();
        List<Button> suggestedDiets = new ArrayList<Button>();

       // suggestedDiets.add("suggestedDiets1");
      //  suggestedDiets.add("suggestedDiets2");
      //  suggestedDiets.add("suggestedDiets3");
       // suggestedDiets.add("suggestedDiets4");

        listDataChild.put(listDataHeader.get(0), suggestions); // Header, Child data
       // listDataChild.put(listDataHeader.get(1), suggestedDiets);
    }


}
