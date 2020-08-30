package com.example.oandr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int lengthEachOilWellInfo = 3;
    private Spinner oilWellChoices;
    private String[] oilWells;
    private String[] charts;
    private Spinner chartChoices;
    private LinePaint chart;
    private ArrayList<float[]> x;
    private ArrayList<float[]> y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        charts = getResources().getStringArray(R.array.charts);
        loadData();
        initWidget();
        chart = (LinePaint) this.findViewById(R.id.chart);


        ViewTreeObserver vto = chart.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                int height = chart.getMeasuredHeight();
                int width = chart.getMeasuredWidth();
                chart.setSize(width,height);
                return true;
            }
        });
    }

    private void loadData() {
        String res =  fromFile();
        String[] lines = res.split("\\r?\\n");
        oilWells = new String[lines.length / lengthEachOilWellInfo];
        x = new ArrayList<float[]>();
        y = new ArrayList<float[]>();
        for (int i=0;i<oilWells.length;i++){
            oilWells[i] = lines[i*lengthEachOilWellInfo].replace("-","");
            x.add(getXValues(lines[i * lengthEachOilWellInfo + 1].replace("x", "").trim()));
            y.add(getXValues(lines[i * lengthEachOilWellInfo + 2].replace("y", "").trim()));
        }
    }

    private float[] getXValues(String valueString) {
        String[] xs = valueString.split(" ");
        float[] xValues = new float[xs.length];
        for (int j = 0; j<xs.length;j++) {
            xValues[j] = Float.valueOf(xs[j]);
        }
        return xValues;
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

    private void updateView() {
        int selectedItemId = (int) oilWellChoices.getSelectedItemId();
        MainActivity.this.setTitle(oilWells[selectedItemId] + "-" + charts[(int) chartChoices.getSelectedItemId()]);
        chart.setPoints(x.get(selectedItemId), y.get(selectedItemId));
    }

    private class oidWellsItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            updateView();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private class chartItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            updateView();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}