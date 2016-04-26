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

public class DietMedium extends AppCompatActivity {

    ExpandableListAdapter1 listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String,Drawable> listImageChild;
    Intent intent;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_low);


        mButton = (Button) findViewById(R.id.saveBtn);
        final Context x = this;

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent saveDiet = new Intent(getApplicationContext(), SaveMediumGIActivity.class);
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
        listImageChild = new HashMap<String,Drawable>();
        // Adding child data
        listDataHeader.add("Rice,brown");
        listDataHeader.add("Fruit cocktail");
        listDataHeader.add("Apricots");
        listDataHeader.add("Pizza,cheese");
        listDataHeader.add("shortbread");
        listDataHeader.add("Beetroot");
        listDataHeader.add("Barley,flakes");
        listDataHeader.add("Taco Shell");

        // Adding child data
        List<String> ricebrown = new ArrayList<String>();
        ricebrown.add("GI:55");
        ricebrown.add("Calories per 100g:111");
        List<String> fruitcocktail = new ArrayList<String>();
        fruitcocktail.add("GI:55");
        fruitcocktail.add("Calories per 100g:50");
        List<String> apricots = new ArrayList<String>();
        apricots.add("GI:57");
        apricots.add("Calories per 100g:48");
        List<String> pizzacheese = new ArrayList<String>();
        pizzacheese.add("GI:60");
        pizzacheese.add("Calories per 100g:257");
        List<String> shortbread = new ArrayList<String>();
        shortbread.add("GI:64");
        shortbread.add("Calories per 100g:502");
        List<String> beetroot = new ArrayList<String>();
        beetroot.add("GI:64");
        beetroot.add("Calories per 100g:43");
        List<String> barleyflakes = new ArrayList<String>();
        barleyflakes.add("GI:66");
        barleyflakes.add("Calories per 100g:354");
        List<String> tacoshell = new ArrayList<String>();
        tacoshell.add("GI:68");
        tacoshell.add("Calories per 100g:150");


        listDataChild.put(listDataHeader.get(0), ricebrown); // Header, Child data
        listDataChild.put(listDataHeader.get(1), fruitcocktail);
        listDataChild.put(listDataHeader.get(2), apricots);
        listDataChild.put(listDataHeader.get(3), pizzacheese);
        listDataChild.put(listDataHeader.get(4), shortbread);
        listDataChild.put(listDataHeader.get(5), beetroot);
        listDataChild.put(listDataHeader.get(6), barleyflakes);
        listDataChild.put(listDataHeader.get(7), tacoshell);

        listImageChild.put(listDataHeader.get(0),getResources().getDrawable(R.drawable.ricebrown));
        listImageChild.put(listDataHeader.get(1),getResources().getDrawable(R.drawable.fruitcocktail));
        listImageChild.put(listDataHeader.get(2),getResources().getDrawable(R.drawable.apricots));
        listImageChild.put(listDataHeader.get(3),getResources().getDrawable(R.drawable.pizzacheese));
        listImageChild.put(listDataHeader.get(4),getResources().getDrawable(R.drawable.shortbread));
        listImageChild.put(listDataHeader.get(5),getResources().getDrawable(R.drawable.beetroot));
        listImageChild.put(listDataHeader.get(6),getResources().getDrawable(R.drawable.barleyflakes));
        listImageChild.put(listDataHeader.get(7),getResources().getDrawable(R.drawable.tacoshell));
    }



}
