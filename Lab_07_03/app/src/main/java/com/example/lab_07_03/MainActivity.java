package com.example.lab_07_03;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtA, edtB, edtRS;
    Button btnRS;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), this::onApplyWindowInsets);
    }

    private WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        edtRS = findViewById(R.id.edtRS);
        btnRS = findViewById(R.id.btnRS);
        btnRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, sub_activity.class);
                int a = Integer.parseInt(edtA.getText().toString());
                int b = Integer.parseInt(edtB.getText().toString());
                myintent.putExtra("soa", a);
                myintent.putExtra("sob", b);
                startActivityForResult(myintent, 99);
            }
        });

        return insets;
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode, data);
        if (requestCode == 99 && resultCode == 33){
            int sum = data.getIntExtra("kq", 0);
            edtRS.setText("Tổng 2 số là: " + sum);
        }
        if (requestCode == 99 && resultCode == 34){
            int sub = data.getIntExtra("kq", 0);
            edtRS.setText("Hiệu 2 số là: " + sub);
        }
    }
}