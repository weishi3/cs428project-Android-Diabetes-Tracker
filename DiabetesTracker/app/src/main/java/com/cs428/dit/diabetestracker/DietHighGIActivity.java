package com.cs428.dit.diabetestracker;

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

public class DietHighGIActivity extends AppCompatActivity {

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
                Intent saveDiet = new Intent(getApplicationContext(), SaveHighGIDietActivity.class);
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

    /**
     * Call the other three data preparation functions,this function will ready up the data and allow popDataIntoExpandableList() to be called
     */
    private void prepareListData() {
        prepareListHeaderData();
        prepareListItemData();
        prepareListImageData();
    }

    /**
     * update listImageChild,a helper for prepareListData()
     */
    private void prepareListImageData() {
        listImageChild = new HashMap<String, Drawable>();

        listImageChild.put(listDataHeader.get(0), getResources().getDrawable(R.drawable.pitabread));
        listImageChild.put(listDataHeader.get(1), getResources().getDrawable(R.drawable.melbatoast));
        listImageChild.put(listDataHeader.get(2), getResources().getDrawable(R.drawable.millet));
        listImageChild.put(listDataHeader.get(3), getResources().getDrawable(R.drawable.couscous));
        listImageChild.put(listDataHeader.get(4), getResources().getDrawable(R.drawable.mango));
        listImageChild.put(listDataHeader.get(5), getResources().getDrawable(R.drawable.pineapple));
        listImageChild.put(listDataHeader.get(6), getResources().getDrawable(R.drawable.favabeans));
        listImageChild.put(listDataHeader.get(7), getResources().getDrawable(R.drawable.puffedrice));
    }

    /**
     * Update listDataChild,a helper for prepareListData()
     */
    private void prepareListItemData() {
        listDataChild = new HashMap<String, List<String>>();

        List<String> pitaBread = new ArrayList<String>();
        pitaBread.add("GI:58\nCalories per 100g:275");
        List<String> melbaToast = new ArrayList<String>();
        melbaToast.add("GI:71\nCalories per 100g:390");
        List<String> millet = new ArrayList<String>();
        millet.add("GI:71\nCalories per 100g:378");
        List<String> couscous = new ArrayList<String>();
        couscous.add("GI:65\nCalories per 100g:112");
        List<String> mango = new ArrayList<String>();
        mango.add("GI:56\nCalories per 100g:60");
        List<String> pineapple = new ArrayList<String>();
        pineapple.add("GI:66\nCalories per 100g:50");
        List<String> favaBeans = new ArrayList<String>();
        favaBeans.add("GI:80\nCalories per 100g:88");
        List<String> puffedRice = new ArrayList<String>();
        puffedRice.add("GI:90\nCalories per 100g:402");

        listDataChild.put(listDataHeader.get(0), pitaBread);
        listDataChild.put(listDataHeader.get(1), melbaToast);
        listDataChild.put(listDataHeader.get(2), millet);
        listDataChild.put(listDataHeader.get(3), couscous);
        listDataChild.put(listDataHeader.get(4), mango);
        listDataChild.put(listDataHeader.get(5), pineapple);
        listDataChild.put(listDataHeader.get(6), favaBeans);
        listDataChild.put(listDataHeader.get(7), puffedRice);
    }

    /**
     * Update the listDataHeader,a helper for prepareListData()
     */
    private void prepareListHeaderData() {
        listDataHeader = new ArrayList<String>();

        listDataHeader.add("Pita Bread");
        listDataHeader.add("Melba Toast");
        listDataHeader.add("Bulgar");
        listDataHeader.add("Couscous");
        listDataHeader.add("Mango");
        listDataHeader.add("Pineapple");
        listDataHeader.add("Fava Beans");
        listDataHeader.add("Puffed Rice");
    }
}
