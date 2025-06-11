package com.example.lab_13_04_02;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String[] ten = {"1-Nguyễn Văn Nam", "2-Trần Văn Tú", "3-Nguyễn Thị Lan", "4-Nguyễn Kim Ngân"};
    String[] sdt = {"0932588634", "0932588635", "0932588636", "0932588637"};

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        CustomAdapter adapter = new CustomAdapter(this, ten, sdt);
        listView.setAdapter(adapter);
    }
}

