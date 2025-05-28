package com.example.lab_07_02;

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

import java.text.DecimalFormat;

public class result_activity extends AppCompatActivity {
    EditText edtRS;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            edtRS = findViewById(R.id.edtRS);
            btnBack = findViewById(R.id.btnBack);
            Intent yourintent = getIntent();
            Bundle yourbundle = yourintent.getBundleExtra("mybackage");
            int a = yourbundle.getInt("soa");
            int b = yourbundle.getInt("sob");
            String rs = "";
            if (a == 0 && b == 0)
                rs = "Vô số nghiệm";
            else if (a == 0 && b != 0) {
                rs = "Vô nghiệm";
            }
            else
            {
                DecimalFormat dcf = new DecimalFormat("0.##");
                rs = dcf.format(-b*1.0/a);
            }
            edtRS.setText(rs);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            return insets;
        });
    }
}