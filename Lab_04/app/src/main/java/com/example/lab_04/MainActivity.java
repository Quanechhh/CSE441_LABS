package com.example.lab_04;

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

public class MainActivity extends AppCompatActivity {
    EditText edtFar, edtCel;
    Button btnFar, btnCel, btnClear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            edtFar = findViewById(R.id.edtFar);
            edtCel = findViewById(R.id.edtCel);
            btnCel = findViewById(R.id.btnCel);
            btnFar = findViewById(R.id.btnFar);
            btnClear = findViewById(R.id.btnClear);

            btnFar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DecimalFormat Far = new DecimalFormat("#.00");
                    String Cel = edtCel.getText()+"";

                    int C = Integer.parseInt(Cel);
                    edtFar.setText(String.format("" + Far.format(C*1.8+32)));
                }
            });

            btnCel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DecimalFormat Cel = new DecimalFormat("#.00");
                    String Far = edtFar.getText()+"";

                    int F = Integer.parseInt(Far);
                    edtCel.setText(""+Cel.format((F - 32)/1.8));
                }
            });

            btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtFar.setText("");
                    edtCel.setText("");
                    edtFar.requestFocus();
                }
            });

            return insets;
        });
    }
}