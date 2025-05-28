package com.example.lab_07_03;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class sub_activity extends AppCompatActivity {
    EditText edtAA, edtBB;
    Button btnTong, btnHieu;
    Intent myintent;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            edtAA = findViewById(R.id.edtAA);
            edtBB = findViewById(R.id.edtBB);
            btnTong = findViewById(R.id.btnTong);
            btnHieu = findViewById(R.id.btnHieu);
            myintent = getIntent();
            int a = myintent.getIntExtra("soa", 0);
            int b = myintent.getIntExtra("sob", 0);
            edtAA.setText(a+"");
            edtBB.setText(b+"");
            btnTong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int sum = a + b;
                    myintent.putExtra("kq", sum);
                    setResult(33, myintent);
                    finish();
                }
            });

            btnHieu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int sub = a - b;
                    myintent.putExtra("kq", sub);
                    setResult(34, myintent);
                    finish();
                }
            });

            return insets;
        });
    }
}