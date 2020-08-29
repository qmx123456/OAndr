package com.example.oandr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import org.apache.http.util.EncodingUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        try {
            InputStream is = getAssets().open("data.txt");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            String rel = baos.toString();
            Log.d("oil", rel);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void updateAppTitle() {
        MainActivity.this.setTitle(oilWells[(int) oilWellChoices.getSelectedItemId()]+"-"+charts[(int) chartChoices.getSelectedItemId()]);
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