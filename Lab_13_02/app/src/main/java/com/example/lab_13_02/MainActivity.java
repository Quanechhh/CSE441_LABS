package com.example.lab_13_02;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    TextView selection;

    // Danh sách điện thoại
    String[] phones = {
            "Ipad", "Iphone", "New Ipad",
            "SamSung", "Nokia", "Sony Ericson",
            "LG", "Q-Mobile", "HTC",
            "Blackberry", "G Phone", "FPT",
            "HK Phone"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridview);
        selection = findViewById(R.id.selection);

        // Gắn adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                phones
        );

        gridView.setAdapter(adapter);

        // Xử lý sự kiện chọn item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedPhone = phones[position];
                selection.setText("Selected: " + selectedPhone);
            }
        });
    }
}

