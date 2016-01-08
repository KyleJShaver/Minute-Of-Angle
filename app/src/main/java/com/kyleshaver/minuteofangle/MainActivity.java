package com.kyleshaver.minuteofangle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText distanceToTarget;
    private EditText poiOffset;
    private EditText moaPerClick;
    private TextView MOADisplay;
    private TextWatcher watcher;
    private MOAPreferenceManager prefs;
    private MOACalc calc;

    private final static String PREFS_NAME = "com.kyleshaver.minuteofangle_preferences";
    public final static String PREFS_MESSAGE_ID = "com.kyleshaver.minuteofangle.PREFSFILELOC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkSettings();

        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateMOA();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        MOADisplay = (TextView) findViewById(R.id.textView);

        distanceToTarget = (EditText) findViewById(R.id.editText);
        distanceToTarget.addTextChangedListener(watcher);

        poiOffset = (EditText) findViewById(R.id.editText2);
        poiOffset.addTextChangedListener(watcher);

        moaPerClick = (EditText) findViewById(R.id.editText3);
        moaPerClick.addTextChangedListener(watcher);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkSettings();
        updateMOA();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }


    private void checkSettings() {
        prefs = new MOAPreferenceManager(getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE));
        ((TextView) findViewById(R.id.textView2)).setText("Distance to Target ("+prefs.distanceToTargetUnit()+")");
        ((TextView) findViewById(R.id.textView3)).setText("Point of Impact Offset ("+prefs.poiOffsetUnit()+")");
    }

    public void updateMOA() {
        try {
            calc = (calc == null ? calc = new MOACalc(prefs) : calc);
            double poiOffsetInput = Double.parseDouble(poiOffset.getText().toString());
            double distanceToTargetInput = Double.parseDouble(distanceToTarget.getText().toString());
            double moaDouble = calc.minuteOfAngle(distanceToTargetInput, poiOffsetInput);
            try {
                double moaPerClickInput = Double.parseDouble(moaPerClick.getText().toString());
                String newValue = calc.roundOff(moaDouble/moaPerClickInput, 0) + " Click(s)";
                MOADisplay.setText(newValue);
            } catch (Exception e) {
                e.printStackTrace();
                String newValue = calc.roundOff(moaDouble, 2) + " MOA";
                MOADisplay.setText(newValue);
            }
        } catch (Exception e) {
            MOADisplay.setText("-");
            e.printStackTrace();
        }
    }

    public void openSettings(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(PREFS_MESSAGE_ID, PREFS_NAME);
        startActivity(intent);
    }
}
