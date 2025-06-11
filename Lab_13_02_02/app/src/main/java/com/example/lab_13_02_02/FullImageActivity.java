package com.example.lab_13_02_02;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FullImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        ImageView imgFull = findViewById(R.id.imgFull);
        Button btnBack = findViewById(R.id.btnBack);

        // Lấy ID ảnh được gửi sang
        int resId = getIntent().getIntExtra("img_res_id", 0);
        imgFull.setImageResource(resId);

        btnBack.setOnClickListener(v -> finish());
    }
}

