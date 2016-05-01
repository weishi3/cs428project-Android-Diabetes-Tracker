package com.cs428.dit.diabetestracker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.cs428.dit.diabetestracker.helpers.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class CheckSavedDietActivity extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private Context applicationContext;
    private SessionManager session;
    private HashMap<String, Object> userDetails;
    static ArrayList<String[]> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_saved);
        applicationContext = this;
        session = new SessionManager(getApplicationContext());
        userDetails = session.getUserDetails();
        String userEmail = userDetails.get("email").toString().replace('.', '!');

        Firebase foodListFirebase = getFirebase(userEmail);

        foodListFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    foodList = new ArrayList<String[]>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        foodList.add(((String) child.getValue()).split(","));
                    }


                    prepareListData(foodList);

                    popDataIntoExpandableList();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private Firebase getFirebase(String userEmail) {
        Firebase.setAndroidContext(applicationContext);
        Firebase base = new Firebase("https://brilliant-fire-9755.firebaseio.com");
        Firebase user = base.child("users").child(userEmail);
        return user.child("foodlist");
    }

    private void popDataIntoExpandableList() {
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        listAdapter = new ExpandableListAdapter(applicationContext, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void prepareListData(ArrayList<String[]> foodLists) {
        if (foodLists.isEmpty()) {
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
            for (int i = 1; i < foodList.length; i++) {
                foods.add(foodList[i]);
            }
            listDataChild.put(foodList[0], foods);
        }
    }
}