package com.example.lab_13_03;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {

    TextView txtSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        txtSub = findViewById(R.id.txt_subphone);

        String name = getIntent().getStringExtra("ten");
        txtSub.setText(name);
    }
}

