package com.example.lab_05_2;

import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;

public class MainActivity extends Activity {
    EditText edita, editb, editc;
    Button btntieptuc, btngiaipt, btnthoat;
    TextView txtkq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        edita = findViewById(R.id.edita);
        editb = findViewById(R.id.editb);
        editc = findViewById(R.id.editc);
        btntieptuc = findViewById(R.id.btntieptuc);
        btngiaipt = findViewById(R.id.btngiaipt);
        btnthoat = findViewById(R.id.btnthoat);
        txtkq = findViewById(R.id.txtkq);

        // Xử lý nút Giải PT
        btngiaipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double a = Double.parseDouble(edita.getText().toString());
                    double b = Double.parseDouble(editb.getText().toString());
                    double c = Double.parseDouble(editc.getText().toString());

                    DecimalFormat df = new DecimalFormat("#.##");

                    if (a == 0) {
                        if (b == 0) {
                            txtkq.setText(c == 0 ? "Phương trình vô số nghiệm" : "Phương trình vô nghiệm");
                        } else {
                            double x = -c / b;
                            txtkq.setText("Phương trình có nghiệm x = " + x);
                        }
                    } else {
                        double delta = b * b - 4 * a * c;
                        if (delta < 0) {
                            txtkq.setText("Phương trình vô nghiệm");
                        } else if (delta == 0) {
                            double x = -b / (2 * a);
                            txtkq.setText("Phương trình có nghiệm kép x = " + x);
                        } else {
                            double x1 = (-b + Math.sqrt(delta)) / (2 * a);
                            double x2 = (-b - Math.sqrt(delta)) / (2 * a);
                            txtkq.setText("x1 = " + x1 + "\nx2 = " + x2);
                        }
                    }
                } catch (NumberFormatException e) {
                    txtkq.setText("Vui lòng nhập đầy đủ và đúng định dạng số.");
                }
            }
        });

        // Xử lý nút Tiếp tục
        btntieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edita.setText("");
                editb.setText("");
                editc.setText("");
                txtkq.setText("");
                edita.requestFocus();
            }
        });

        // Xử lý nút Thoát
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Thoát ứng dụng
            }
        });
    }
}
