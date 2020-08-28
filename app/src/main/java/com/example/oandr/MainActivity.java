package com.example.oandr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

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

    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignValues();
        initWidget();
        try (AssetManager assets = getResources().getAssets()) {
            InputStream in = assets.open("file:///android_asset/data.txt");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int len = -1;
            List<Byte> outB = new ArrayList<Byte>();
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
//                outB.
                baos.write(buffer, 0, len);
            }
            String rel = baos.toString();
            Log.d("xl", rel);
            in.close();
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