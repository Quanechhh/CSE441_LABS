package com.example.lab_13_03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Phone> phoneList;
    MyArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        phoneList = new ArrayList<>();
        phoneList.add(new Phone("Điện thoại Iphone 12", R.drawable.ip));
        phoneList.add(new Phone("Điện thoại Samsung S20", R.drawable.samsung));
        phoneList.add(new Phone("Điện thoại Nokia 6", R.drawable.wp));
        phoneList.add(new Phone("Điện thoại Bphone 2020", R.drawable.cellphone));
        phoneList.add(new Phone("Điện thoại Oppo 5", R.drawable.sky));
        phoneList.add(new Phone("Điện thoại VSmart joy2", R.drawable.lg));

        adapter = new MyArrayAdapter(this, R.layout.layout_listview, phoneList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Intent intent = new Intent(MainActivity.this, SubActivity.class);
            intent.putExtra("ten", phoneList.get(position).name);
            startActivity(intent);
        });
    }
}
