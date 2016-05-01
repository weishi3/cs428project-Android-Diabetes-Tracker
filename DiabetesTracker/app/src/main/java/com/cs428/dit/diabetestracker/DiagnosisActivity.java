package com.cs428.dit.diabetestracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class DiagnosisActivity extends AppCompatActivity {

    //float BMI, float waistline, int age, int blood_pressure, boolean gender, boolean familyHistory
    final Activity thisAct = this;
    public HashMap<String, Object> userMap;
    // instances for expandable lists
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<String> suggestionContents = null;
    HashMap<String, List<String>> listDataChild;
    private String foodSug = "Generally Suggested Diets";
    private String diagnosisTitle = "N/A Before Running Diagnosis!";
    private LinearLayout mLayout;
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

        // preparing list data
        prepareListData();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = createUser();

                boolean sedentary = false;
                int ExerciseT = 80;
                float HDL_C = (float)0.9;
                float TG = (float)2.21;
                int weightB = 3;
                boolean GDM = false;
                boolean diagnosedD = false;
                boolean psychotropic = false;
                //Atherosclerotic CCVd: stands for chronic cerebrovascular disease
                boolean CCVD = false;

                //does a female user has PCOS? stands for Polycystic ovary syndrome
                boolean PCOS = false;

                // does user have psychotropic problems.

                if (userMap.containsKey("sedentaryJob") && userMap.get("sedentaryJob") != null)
                    sedentary = (boolean) userMap.get("sedentaryJob");
                if (userMap.containsKey("exerciseT") && userMap.get("exerciseT") != null)
                    ExerciseT = (int) userMap.get("exerciseT");
                if (userMap.containsKey("HDL_C") && userMap.get("HDL_C") != null)
                    HDL_C = (float) userMap.get("HDL_C");
                if (userMap.containsKey("TG") && userMap.get("TG") != null)
                    TG = (float) userMap.get("TG");
                if (userMap.containsKey("weightB") && userMap.get("weightB") != null)
                    weightB = (int) userMap.get("weightB");
                if (userMap.containsKey("GDM") && userMap.get("GDM") != null)
                    GDM = (boolean) userMap.get("GDM");
                if (userMap.containsKey("diagnosedD") && userMap.get("diagnosedD") != null)
                    diagnosedD = (boolean) userMap.get("diagnosedD");
                if (userMap.containsKey("psychotropic") && userMap.get("psychotropic") != null)
                    psychotropic = (boolean) userMap.get("psychotropic");
                if (userMap.containsKey("CCVD") && userMap.get("CCVD") != null)
                    CCVD = (boolean) userMap.get("CCVD");

                if (userMap.containsKey("PCOS") && userMap.get("PCOS") != null)
                    PCOS = (boolean) userMap.get("PCOS");

                setU(u, sedentary, ExerciseT, HDL_C, TG, weightB, GDM, diagnosedD, psychotropic, CCVD, PCOS);

                String score = u.getScore();
                foodSug = u.generateSuggestionD();
                suggestionContents = u.generateSuggestion();
                diagnosisTitle = u.getSuggestionsSummary();

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

        popDataIntoExpandableList();
    }

    private void popDataIntoExpandableList() {
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        setUpGroupListener();
        setUpChildListener();
    }

    private void setUpChildListener() {
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) listAdapter.getChild(groupPosition, childPosition);
                switch (selected) {
                    case "lowGI":
                        Intent intent = new Intent(DiagnosisActivity.this, DietLow.class);
                        startActivity(intent);
                        break;
                    case "mediumGI":
                        intent = new Intent(DiagnosisActivity.this, DietMedium.class);
                        startActivity(intent);
                        break;
                    case "highGI":
                        intent = new Intent(DiagnosisActivity.this, DietHigh.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    private void setUpGroupListener() {
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return false;
            }
        });

        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void setU(User u, boolean sedentary, int exerciseT, float HDL_C, float TG, int weightB, boolean GDM, boolean diagnosedD, boolean psychotropic, boolean CCVD, boolean PCOS) {
        u.setSedentaryJob(sedentary);
        u.setExerciseT(exerciseT);
        u.setDiagnosedD(diagnosedD);
        u.setGDM(GDM);
        u.setWeightB(weightB);
        u.setHDL_C(HDL_C);
        u.setTG(TG);
        u.setPsychotropic(psychotropic);
        u.setCCVD(CCVD);
        u.setPCOS(PCOS);
    }

    @NonNull
    public User createUser() {
        float BMI = (float) userMap.get("BMI");
        float waistline = (float) userMap.get("waistline");
        int age = (int) userMap.get("age");
        int bloodPressure = (int) userMap.get("bloodPressure");
        boolean familyHistory = (boolean) userMap.get("familyHistory");
        boolean gender = (boolean) userMap.get("gender");
        return new User(BMI, waistline, age, bloodPressure, familyHistory, gender);
    }

    private void prepareListData() {
        prepareListDataHeader();
        prepareListItemData();
    }

    private void prepareListItemData() {
        listDataChild = new HashMap<String, List<String>>();

        List<String> suggestions = suggestionContents;
        if (suggestions == null) {
            suggestions = new ArrayList<String>();
            suggestions.add("N/A");
        }

        List<String> suggestedDiets = new ArrayList<String>();

        suggestedDiets.add("lowGI");
        suggestedDiets.add("mediumGI");
        suggestedDiets.add("highGI");

        listDataChild.put(listDataHeader.get(0), suggestions);
        listDataChild.put(listDataHeader.get(1), suggestedDiets);
    }

    private void prepareListDataHeader() {
        listDataHeader = new ArrayList<String>();

        listDataHeader.add(diagnosisTitle);
        listDataHeader.add(foodSug);
    }
}
