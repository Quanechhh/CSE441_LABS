package com.example.lab_13_04;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        imageView = findViewById(R.id.imageView_sub);
        textView = findViewById(R.id.textView_sub);

        Intent intent = getIntent();
        String ten = intent.getStringExtra("ten");
        int hinh = intent.getIntExtra("hinh", 0);

        imageView.setImageResource(hinh);
        textView.setText(ten);
    }
}

