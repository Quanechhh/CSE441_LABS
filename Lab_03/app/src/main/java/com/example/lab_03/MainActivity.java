package com.example.lab_03;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomappbar.BottomAppBar;

public class MainActivity extends AppCompatActivity {
    EditText edtA, edtB, edtC;
    Button btnTong, btnHieu, btnTich, btnThuong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            edtA = findViewById(R.id.edtA);
            edtB = findViewById(R.id.edtB);
            edtC = findViewById(R.id.edtC);
            btnTong = findViewById(R.id.btnTong);
            btnHieu = findViewById(R.id.btnHieu);
            btnTich = findViewById(R.id.btnTich);
            btnThuong = findViewById(R.id.btnThuong);

            btnTong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a = Integer.parseInt("0" + edtA.getText());
                    int b = Integer.parseInt("0" + edtB.getText());
                    edtC.setText("a + b = " +(a+b));
                }
            });

            btnHieu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a = Integer.parseInt("0" + edtA.getText());
                    int b = Integer.parseInt("0" + edtB.getText());
                    edtC.setText("a - b = " +(a-b));
                }
            });

            btnTich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a = Integer.parseInt("0" + edtA.getText());
                    int b = Integer.parseInt("0" + edtB.getText());
                    edtC.setText("a * b = " +(a*b));
                }
            });

            btnThuong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float a = Float.parseFloat("0" + edtA.getText());
                    float b = Float.parseFloat("0" + edtB.getText());
                    if (b == 0)
                        edtC.setText("B phải khác 0");
                    else
                        edtC.setText("a / b = " +(a/b));
                }
            });
            return insets;
        });
    }
}