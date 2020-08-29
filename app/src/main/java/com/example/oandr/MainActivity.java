package com.example.oandr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    public static final int lengthEachOilWellInfo = 3;
    private Spinner oilWellChoices;
    private String[] oilWells;
    private String[] charts;
    private Spinner chartChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        charts = getResources().getStringArray(R.array.charts);
        loadData();
        initWidget();
    }

    private void loadData() {
        String res =  fromFile();
        String[] lines = res.split("\\r?\\n");
        oilWells = new String[lines.length / lengthEachOilWellInfo];
        for (int i=0;i<oilWells.length;i++){
            oilWells[i] = lines[i*lengthEachOilWellInfo].replace("-","");
        }
    }

    private String fromFile() {
        String res = null;
        try {
            InputStream is = getAssets().open("data.txt");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            res = baos.toString();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private void initWidget() {
        oilWellChoices = (Spinner) this.findViewById(R.id.oilWellChoices);
        initOilWellChoices(oilWells, oilWellChoices);
        oilWellChoices.setOnItemSelectedListener(new oidWellsItemSelectedListener());

        chartChoices = (Spinner) this.findViewById(R.id.chartChoices);
        chartChoices.setOnItemSelectedListener(new chartItemSelectedListener());
    }

    private void initOilWellChoices(String[] oilWells, Spinner oilWellChoices) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, oilWells);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oilWellChoices.setAdapter(adapter);
    }

    private void updateAppTitle() {
        MainActivity.this.setTitle(oilWells[(int) oilWellChoices.getSelectedItemId()] + "-" + charts[(int) chartChoices.getSelectedItemId()]);
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
}