package com.example.oandr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Spinner oilWellChoices;
    private String[] oilWells;
    private String[] charts;
    private Spinner chartChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignValues();
        initWidget();
    }

    private void initWidget() {
        oilWellChoices = (Spinner) this.findViewById(R.id.oilWellChoices);
        oilWellChoices.setOnItemSelectedListener(new oidWellsItemSelectedListener());

        chartChoices = (Spinner) this.findViewById(R.id.chartChoices);
        chartChoices.setOnItemSelectedListener(new chartItemSelectedListener());
    }

    private void assignValues() {
        oilWells = getResources().getStringArray(R.array.oil_wells);
        charts = getResources().getStringArray(R.array.charts);
    }

    private class oidWellsItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            updateAppTitle();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private class chartItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            updateAppTitle();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private void updateAppTitle() {
        MainActivity.this.setTitle(oilWells[(int) oilWellChoices.getSelectedItemId()]+"-"+charts[(int) chartChoices.getSelectedItemId()]);
    }
}