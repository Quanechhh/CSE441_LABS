package com.example.lab_05;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editNamDuongLich;
    Button btnChuyenDoi;
    TextView txtNamAmLich;

    String[] can = {"Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ"};
    String[] chi = {"Thân", "Dậu", "Tuất", "Hợi", "Tí", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        editNamDuongLich = findViewById(R.id.editnamduonglich);
        btnChuyenDoi = findViewById(R.id.btnChuyen);
        txtNamAmLich = findViewById(R.id.textView5);

        btnChuyenDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namStr = editNamDuongLich.getText().toString().trim();

                if (namStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập năm dương lịch", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int nam = Integer.parseInt(namStr);
                    String canChi = getCanChi(nam);
                    txtNamAmLich.setText(canChi);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Năm không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getCanChi(int nam) {
        String tenCan = can[nam % 10];
        String tenChi = chi[nam % 12];
        return tenCan + " " + tenChi;
    }
}
