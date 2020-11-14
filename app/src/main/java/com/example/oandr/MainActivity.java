package com.example.oandr;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_learn);
        textView = (TextView) findViewById(R.id.text_qmx);
        this.registerForContextMenu(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu fileMenu = menu.addSubMenu("查看文件");
        SubMenu editMenu = menu.addSubMenu("输入文件");

        fileMenu.add(1,1,1, "文件1");
        fileMenu.add(1,2,1,"文件2");

        editMenu.add(2,1,1,"输入1");
        editMenu.add(2,2,1,"输入2");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getGroupId() == 1){
            switch (item.getItemId()){
                case 1:Toast.makeText(this, "点击了文件1",
                        Toast.LENGTH_SHORT).show();break;
                case 2:Toast.makeText(this, "点击了文件2",
                        Toast.LENGTH_SHORT).show();break;
            }
        }else if(item.getGroupId() == 2){
            switch (item.getItemId()){
                case 1:Toast.makeText(this, "点击了输入1",
                        Toast.LENGTH_SHORT).show();break;
                case 2:Toast.makeText(this, "点击了输入2",
                        Toast.LENGTH_SHORT).show();break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showListView() {
        listView = (ListView) findViewById(R.id.menu_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getData());
        listView.setAdapter(adapter);
    }

    private List<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i< 10; i++){
            list.add("菜单"+i);
        }
        return list;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("上下文菜单");
        menu.add(1,1,1,"保存");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                Toast.makeText(this, "点击了保存",
                        Toast.LENGTH_SHORT).show();break;
        }
        return super.onContextItemSelected(item);
    }
}