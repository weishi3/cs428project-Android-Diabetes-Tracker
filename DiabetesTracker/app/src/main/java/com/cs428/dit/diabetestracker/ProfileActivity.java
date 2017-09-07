package com.cs428.dit.diabetestracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.SessionManager;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private SessionManager session;
    private HashMap<String, Object> userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        session = new SessionManager(getApplicationContext());

        //set button to take you to edit profile page
        Button editBtn = (Button) findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editProfile = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(editProfile);
            }
        });

        userDetails = session.getUserDetails();
        editText("age", R.id.age);
        editText("waistline", R.id.waistline);
        editText("BMI", R.id.BMI);
        editText("familyHistory", R.id.familyHistory);
        editText("bloodPressure", R.id.bloodPressure);
        TextView view = (TextView) findViewById(R.id.gender);
        view.setText((boolean)userDetails.get("gender") ? "Male" : "Female");
        editText("sedentaryJob", R.id.sedentaryJob);
        editText("exerciseT", R.id.exerciseT);
        editText("diagnosedD", R.id.diagnosedD);
        editText("GDM", R.id.GDM);
        editText("weightB", R.id.weightB);
        editText("CCVD", R.id.CCVD);
        editText("PCOS", R.id.PCOS);
        editText("psychotropic", R.id.psychotropic);
        editText("HDL_C", R.id.HDL_C);
        editText("TG", R.id.TG);

    }

    /**
     * Grabs a value from our SessionManager hashmap and overwrites
     * it in the layout object
     * @param key the key to the value we're writing
     * @param id the id of the layout object we're overwriting
     */
    private void editText(String key, int id) {
        TextView view = (TextView) findViewById(id);
        Object text = userDetails.get(key);
        if( text == null ){
            view.setText( "N/A" );
            return;
        }
        view.setText( text.toString() );
    }
}