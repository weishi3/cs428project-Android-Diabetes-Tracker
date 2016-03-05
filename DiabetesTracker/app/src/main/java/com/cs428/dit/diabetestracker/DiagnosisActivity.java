package com.cs428.dit.diabetestracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.cs428.dit.diabetestracker.helpers.User;

public class DiagnosisActivity extends AppCompatActivity {

    public float BMI = 25;
    public int waistline = 80;
    public int age = 22;
    public int bloodPressure = 130;
    public boolean familyHistory = false;
    public boolean gender = false;

    private LinearLayout mLayout;
    private EditText mEditText;
    private Button mButton;

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
                mLayout.addView(createNewTextView(score));

            }
        });
    }

    private View createNewTextView(String score) {
        final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText("The score is: " + score);
        return textView;
    }

}
