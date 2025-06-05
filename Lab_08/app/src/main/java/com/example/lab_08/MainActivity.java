package com.example.lab_08;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btncallphone, btnsendsms;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            btncallphone = findViewById(R.id.btncallphone);
            btnsendsms = findViewById(R.id.btnsendsms);
            btncallphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub
//Tạo mới một đối tượng intent
                    Intent intent1 =new Intent(MainActivity.this,CallPhoneActivity.class);
//Thực thi Intent1
                    startActivity(intent1);
                }
            });

            btnsendsms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub
//Tạo mới một đối tượng intent
                    Intent intent2 =new Intent(MainActivity.this,SendSMSActivity.class);
//Thực thi Intent1
                    startActivity(intent2);
                }
            });

            return insets;
        });
    }
}