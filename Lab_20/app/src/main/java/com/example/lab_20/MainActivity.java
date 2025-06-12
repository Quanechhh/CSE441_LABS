package com.example.lab_20;

import android.os.Bundle; //
import android.view.View; //
import android.widget.Button; //
import android.widget.ProgressBar; // Thêm import cho ProgressBar
import android.widget.TextView; // Thêm import cho TextView

import androidx.appcompat.app.AppCompatActivity; //

public class MainActivity extends AppCompatActivity { //
    Button btnStart; //
    MyAsyncTask mytt; //

    // Thêm khai báo ProgressBar và TextView để truyền vào AsyncTask
    ProgressBar progressBar1;
    TextView textView1;

    @Override //
    protected void onCreate(Bundle savedInstanceState) { //
        super.onCreate(savedInstanceState); //
        setContentView(R.layout.activity_main); //

        btnStart = findViewById(R.id.button1); //
        progressBar1 = findViewById(R.id.progressBar1); // Ánh xạ ProgressBar
        textView1 = findViewById(R.id.textView1); // Ánh xạ TextView

        btnStart.setOnClickListener(new View.OnClickListener() { //
            @Override //
            public void onClick(View arg0) { //
                doStart(); //
            }
        });
    }

    private void doStart() { //
        // truyền this (chính là MainActivity hiện tại) qua backgroud Thread
        mytt = new MyAsyncTask(this); //
        //Kích hoạt Tiến trình
        //khi gọi hàm này thì onPreExecute của mytt sẽ thực thi trước
        mytt.execute(); //
    }
}