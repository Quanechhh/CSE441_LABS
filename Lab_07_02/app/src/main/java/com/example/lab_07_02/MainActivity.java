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

public class MainActivity extends AppCompatActivity {
    EditText edtA, edtB;
    Button btnRS;
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
            btnRS = findViewById(R.id.btnRS);
            btnRS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myintent = new Intent(MainActivity.this, result_activity.class);
                    Bundle bundle1 = new Bundle();
                    int a = Integer.parseInt(edtA.getText()+"");
                    int b = Integer.parseInt(edtB.getText()+"");
                    bundle1.putInt("soa", a);
                    bundle1.putInt("sob", b);
                    myintent.putExtra("mybackage", bundle1);
                    startActivity(myintent);
                }
            });

            return insets;
        });
    }
}