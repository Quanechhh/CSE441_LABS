package com.example.lab_13_02_02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Các ảnh minh hoạ – đổi lại đúng tên file của bạn
    public static final int[] images = {
            R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
            R.drawable.pic4, R.drawable.pic5, R.drawable.pic6,
            R.drawable.pic7, R.drawable.pic8, R.drawable.pic9
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView grid = findViewById(R.id.gridview);
        grid.setAdapter(new ImageAdapter(this, images));

        grid.setOnItemClickListener((AdapterView<?> parent, android.view.View v, int position, long id) -> {
            Intent i = new Intent(MainActivity.this, FullImageActivity.class);
            i.putExtra("img_res_id", images[position]);
            startActivity(i);
        });
    }
}
