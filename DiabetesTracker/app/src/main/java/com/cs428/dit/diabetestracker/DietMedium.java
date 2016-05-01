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

public class DietMedium extends AppCompatActivity {

    ExpandableListAdapterWithImages listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, Drawable> listImageChild;
    Intent intent;
    private Button saveButton;
    private Button checkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_low);

        saveButton = (Button) findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent saveDiet = new Intent(getApplicationContext(), SaveMediumGIActivity.class);
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

    private void prepareListData() {
        prepareListHeaderData();
        prepareListItemData();
        prepareImageListData();
    }

    private void prepareListItemData() {
        listDataChild = new HashMap<String, List<String>>();

        List<String> riceBrown = new ArrayList<String>();
        riceBrown.add("GI:55\nCalories per 100g:111");
        List<String> fruitCocktail = new ArrayList<String>();
        fruitCocktail.add("GI:55\nCalories per 100g:50");
        List<String> apricots = new ArrayList<String>();
        apricots.add("GI:57\nCalories per 100g:48");
        List<String> pizzaCheese = new ArrayList<String>();
        pizzaCheese.add("GI:60\nCalories per 100g:257");
        List<String> shortBread = new ArrayList<String>();
        shortBread.add("GI:64\nCalories per 100g:502");
        List<String> beetroot = new ArrayList<String>();
        beetroot.add("GI:64\nCalories per 100g:43");
        List<String> barleyFlakes = new ArrayList<String>();
        barleyFlakes.add("GI:66\nCalories per 100g:354");
        List<String> tacoShell = new ArrayList<String>();
        tacoShell.add("GI:68\nCalories per 100g:150");

        listDataChild.put(listDataHeader.get(0), riceBrown); // Header, Child data
        listDataChild.put(listDataHeader.get(1), fruitCocktail);
        listDataChild.put(listDataHeader.get(2), apricots);
        listDataChild.put(listDataHeader.get(3), pizzaCheese);
        listDataChild.put(listDataHeader.get(4), shortBread);
        listDataChild.put(listDataHeader.get(5), beetroot);
        listDataChild.put(listDataHeader.get(6), barleyFlakes);
        listDataChild.put(listDataHeader.get(7), tacoShell);
    }

    private void prepareListHeaderData() {
        listDataHeader = new ArrayList<String>();

        listDataHeader.add("Rice,brown");
        listDataHeader.add("Fruit cocktail");
        listDataHeader.add("Apricots");
        listDataHeader.add("Pizza,cheese");
        listDataHeader.add("shortbread");
        listDataHeader.add("Beetroot");
        listDataHeader.add("Barley,flakes");
        listDataHeader.add("Taco Shell");
    }

    private void prepareImageListData() {
        listImageChild = new HashMap<String, Drawable>();

        listImageChild.put(listDataHeader.get(0), getResources().getDrawable(R.drawable.ricebrown));
        listImageChild.put(listDataHeader.get(1), getResources().getDrawable(R.drawable.fruitcocktail));
        listImageChild.put(listDataHeader.get(2), getResources().getDrawable(R.drawable.apricots));
        listImageChild.put(listDataHeader.get(3), getResources().getDrawable(R.drawable.pizzacheese));
        listImageChild.put(listDataHeader.get(4), getResources().getDrawable(R.drawable.shortbread));
        listImageChild.put(listDataHeader.get(5), getResources().getDrawable(R.drawable.beetroot));
        listImageChild.put(listDataHeader.get(6), getResources().getDrawable(R.drawable.barleyflakes));
        listImageChild.put(listDataHeader.get(7), getResources().getDrawable(R.drawable.tacoshell));
    }
}
