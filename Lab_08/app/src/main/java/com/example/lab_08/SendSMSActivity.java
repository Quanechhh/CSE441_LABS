package com.example.lab_08;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SendSMSActivity extends AppCompatActivity {
    EditText edtsms;
    Button btnback2;
    ImageButton btnsendsms;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_smsactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_sms), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            edtsms = (EditText) findViewById(R.id.edtsms);
            btnback2 = (Button) findViewById(R.id.btnback2);
            btnsendsms = (ImageButton) findViewById(R.id.btnsms);
            btnsendsms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callintent = new Intent(Intent.ACTION_SENDTO,
                            Uri.parse("smsto:"+edtsms.getText().toString()));
                    startActivity(callintent);
                }
            });
            btnback2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            return insets;
        });
    }
}