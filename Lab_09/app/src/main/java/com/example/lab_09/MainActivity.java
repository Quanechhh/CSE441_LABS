package com.example.lab_09;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton imgstart, imgstop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgstart = findViewById(R.id.imgstart);
        imgstop = findViewById(R.id.imgstop);

        imgstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MusicService.class);
                startService(intent);
            }
        });

        imgstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MusicService.class);
                stopService(intent);
                finish(); // Thoát app
            }
        });
    }
}
