package com.example.oandr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Spinner oidWellChoices;
    private String[] oilWells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignValues();
        initWidget();
    }

    private void initWidget() {
        oidWellChoices = (Spinner) this.findViewById(R.id.oilWellChoices);
        oidWellChoices.setOnItemSelectedListener(new oidWellsItemSelectedListener());
    }

    private void assignValues() {
        oilWells = getResources().getStringArray(R.array.oil_wells);
    }

    private class oidWellsItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            MainActivity.this.setTitle(oilWells[i]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}