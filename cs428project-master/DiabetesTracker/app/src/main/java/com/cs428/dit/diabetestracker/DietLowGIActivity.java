package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DietLowGIActivity extends AppCompatActivity {

    ExpandableListAdapterWithImages listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, Drawable> listImageChild;
    Intent intent;
    private Button saveButton;
    private Button checkButton;

    /**
     * The method is called when this activity is created,savedInstanceState is used to restore activity state when exited unexpectedly,not used here
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_low);

        saveButton = (Button) findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent saveDiet = new Intent(getApplicationContext(), SaveLowGIDietActivity.class);
                startActivity(saveDiet);
            }
        });

        checkButton = (Button) findViewById(R.id.checkBtn);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkSavedDiet = new Intent(getApplicationContext(), CheckSavedDietActivity.class);
                startActivity(checkSavedDiet);
            }
        });

        prepareListData();

        popDataIntoExpandableList();
    }

    /**
     * Get the list from listDataHeader and listDataChild
     * Get the expandable list view and set the listAdaptor for it based on the list retrieved from listDataHeader and listDataChild
     */
    private void popDataIntoExpandableList() {
        expListView = (ExpandableListView) findViewById(R.id.lvExp2);
        listAdapter = new ExpandableListAdapterWithImages(this, listDataHeader, listDataChild, listImageChild);
        expListView.setAdapter(listAdapter);
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

    /**
     * Call the other three data preparation functions,this function will ready up the data and allow popDataIntoExpandableList() to be called
     */
    private void prepareListData() {
        prepareListHeaderData();
        prepareListItemData();
        prepareListImageData();
    }

    /**
     * Update the listDataHeader,a helper for prepareListData()
     */
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

    /**
     * Update listDataChild,a helper for prepareListData()
     */
    private void prepareListItemData() {
        listDataChild = new HashMap<String, List<String>>();

        List<String> riceNoodles = new ArrayList<String>();
        riceNoodles.add("GI:40\nCalories per 100g:109");
        List<String> sweetCorn = new ArrayList<String>();
        sweetCorn.add("GI:47\nCalories per 100g:86");
        List<String> lentils = new ArrayList<String>();
        lentils.add("GI:21\nCalories per 100g:116");
        List<String> beans = new ArrayList<String>();
        beans.add("GI:30\nCalories per 100g:347");
        List<String> yogurt = new ArrayList<String>();
        yogurt.add("GI:19\nCalories per 100g:59");
        List<String> greekYorgurt = new ArrayList<String>();
        greekYorgurt.add("GI:19\nCalories per 100g:59");
        List<String> plums = new ArrayList<String>();
        plums.add("GI:24\nCalories per 100g:46");
        List<String> oranges = new ArrayList<String>();
        oranges.add("GI:40\nCalories per 100g:47");

        listDataChild.put(listDataHeader.get(0), riceNoodles); // Header, Child data
        listDataChild.put(listDataHeader.get(1), sweetCorn);
        listDataChild.put(listDataHeader.get(2), lentils);
        listDataChild.put(listDataHeader.get(3), beans);
        listDataChild.put(listDataHeader.get(4), yogurt);
        listDataChild.put(listDataHeader.get(5), greekYorgurt);
        listDataChild.put(listDataHeader.get(6), plums);
        listDataChild.put(listDataHeader.get(7), oranges);
    }

    /**
     * update listImageChild,a helper for prepareListData()
     */
    private void prepareListImageData() {
        listImageChild = new HashMap<String, Drawable>();

        listImageChild.put(listDataHeader.get(0), getResources().getDrawable(R.drawable.ricenoodles));
        listImageChild.put(listDataHeader.get(1), getResources().getDrawable(R.drawable.sweetcorn));
        listImageChild.put(listDataHeader.get(2), getResources().getDrawable(R.drawable.lentils));
        listImageChild.put(listDataHeader.get(3), getResources().getDrawable(R.drawable.beans));
        listImageChild.put(listDataHeader.get(4), getResources().getDrawable(R.drawable.yogurt));
        listImageChild.put(listDataHeader.get(5), getResources().getDrawable(R.drawable.greekyogurt));
        listImageChild.put(listDataHeader.get(6), getResources().getDrawable(R.drawable.plums));
        listImageChild.put(listDataHeader.get(7), getResources().getDrawable(R.drawable.oranges));
    }


}