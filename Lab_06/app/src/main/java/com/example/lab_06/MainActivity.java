package com.example.lab_06;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editHoten, editCMND, editBosung;
    RadioButton radtc, radcd, raddh;
    CheckBox chkdocbao, chkdocsach, chkdoccoding;
    Button btnGuiTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // XML layout chứa các View đã thiết kế

        // Ánh xạ các view
        editHoten = findViewById(R.id.editHoten);
        editCMND = findViewById(R.id.editCMND);
        editBosung = findViewById(R.id.editBosung);

        radtc = findViewById(R.id.radtc);
        radcd = findViewById(R.id.radcd);
        raddh = findViewById(R.id.raddh);

        chkdocbao = findViewById(R.id.chkdocbao);
        chkdocsach = findViewById(R.id.chkdocsach);
        chkdoccoding = findViewById(R.id.chkdoccoding);

        btnGuiTT = findViewById(R.id.btnGuiTT);

        // Bắt sự kiện nút "Gửi thông tin"
        btnGuiTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các EditText
                String hoten = editHoten.getText().toString();
                String cmnd = editCMND.getText().toString();
                String bosung = editBosung.getText().toString();

                // Xác định bằng cấp được chọn
                String bangcap = "";
                if (radtc.isChecked()) bangcap = "Trung cấp";
                else if (radcd.isChecked()) bangcap = "Cao đẳng";
                else if (raddh.isChecked()) bangcap = "Đại học";

                // Xác định sở thích
                String sothich = "";
                if (chkdocbao.isChecked()) sothich += "Đọc báo; ";
                if (chkdocsach.isChecked()) sothich += "Đọc sách; ";
                if (chkdoccoding.isChecked()) sothich += "Đọc coding; ";

                // Gộp thông tin thành chuỗi
                String thongtin = "Họ tên: " + hoten +
                        "\nCMND: " + cmnd +
                        "\nBằng cấp: " + bangcap +
                        "\nSở thích: " + sothich +
                        "\nThông tin bổ sung:\n" + bosung;

                // Hiển thị thông tin bằng AlertDialog
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Thông tin cá nhân")
                        .setMessage(thongtin)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }
}
