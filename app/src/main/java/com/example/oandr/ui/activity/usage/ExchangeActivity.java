package com.example.oandr.ui.activity.usage;

import android.util.Log;
import android.view.View;

import com.example.oandr.R;
import com.example.oandr.ui.activity.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ExchangeActivity extends BaseActivity {

    private ColumnChartView columnChartViewExchange;

    @Override
    public int getLayoutId() {
        return R.layout.activity_use_exchange;
    }

    @Override
    public void initView() {
        columnChartViewExchange = (ColumnChartView) findViewById(R.id.ccv_column_exchange);
    }

    @Override
    public void initData() {
        String uri = "https://op.juhe.cn/onebox/exchange/query?key=2e14cec408860a7958f2a7791b1e6b58";

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(uri)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                try{
                    JSONObject jsonObject1 = new JSONObject(str);
                    /**
                     * 为什么要使用jsonObject.optString， 不使用jsonObject.getString
                     * 因为jsonObject.optString获取null不会报错
                     */
                    String result = jsonObject1.optString("result", null);
                    JSONObject jsonObject2 = new JSONObject(result);
                    String list = jsonObject2.optString("list");
                    JSONArray jsa1=new JSONArray(list);
                    ArrayList<Column> columns = new ArrayList<>();
                    for (int i=0; i<jsa1.length(); i++){
                        List<SubcolumnValue> values = new ArrayList<>();
                        Object o = jsa1.get(i);
                        JSONArray jsa=new JSONArray(o.toString());
                        SubcolumnValue e = new SubcolumnValue((float) jsa.getDouble(2), ChartUtils.pickColor());
                        e.setLabel(jsa.getString(0)+" "+e.getValue());
                        values.add(e);
                        Column column = new Column(values);

                        column.setHasLabels(true);
                        columns.add(column);
                    }
                    ColumnChartData columnChartData = new ColumnChartData(columns);
                    Axis axisX = new Axis();

                    columnChartViewExchange.setColumnChartData(columnChartData);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View v) {

    }
}
