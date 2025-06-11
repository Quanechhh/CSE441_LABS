package com.example.lab_18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class subactivity extends AppCompatActivity {

    TextView txtmasohat, txttenbaihat, txtloibaihat, txttacgia;
    ImageButton btnthichsub, btnkhongthichsub;
    String masoNhan; // Biến để lưu mã số bài hát được nhận từ MainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity);

        // Ánh xạ các Controls
        txtmasohat = findViewById(R.id.txtmasohat);
        txttenbaihat = findViewById(R.id.txttenbaihat);
        txtloibaihat = findViewById(R.id.txtloibaihat);
        txttacgia = findViewById(R.id.txttacgia);
        btnthichsub = findViewById(R.id.btnthichsub);
        btnkhongthichsub = findViewById(R.id.btnkhongthichsub);

        // Nhận Intent từ myarrayAdapter, lấy dữ liệu khỏi bundle
        Intent callerIntent = getIntent();
        Bundle bundle = callerIntent.getBundleExtra("package");
        masoNhan = bundle.getString("maso"); // Lấy mã số từ Bundle

        // Truy vấn dữ liệu từ masoNhan được nhận: Hiển thị tên bài hát, lời bài hát, tác giả, Trạng thái Thích lên activitysub
        Cursor c = MainActivity.database.rawQuery("SELECT * FROM ArirangSonglist WHERE MABH = ?", new String[]{masoNhan});
        c.moveToFirst();

        txtmasohat.setText("#" + c.getString(0)); // Mã bài hát
        txttenbaihat.setText(c.getString(1)); // Tên bài hát
        txtloibaihat.setText(c.getString(2)); // Lời bài hát
        txttacgia.setText(c.getString(3)); // Tác giả

        // Hiển thị nút Thích/Không thích dựa trên trạng thái YEUTHICH
        if (c.getInt(5) == 0) { // Nếu YEUTHICH = 0 (không thích)
            btnthichsub.setVisibility(View.VISIBLE);
            btnkhongthichsub.setVisibility(View.GONE);
        } else { // Nếu YEUTHICH = 1 (thích)
            btnthichsub.setVisibility(View.GONE);
            btnkhongthichsub.setVisibility(View.VISIBLE);
        }
        c.close();

        // Xử lý sự kiện khi click vào Button btnthichsub và btnkhongthichsub
        // Cập nhật dữ liệu vào CSDL, thay đổi trạng thái hiển thị cho Button btnthich và btnkhongthich
        btnthichsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 1); // Đánh dấu là thích
                MainActivity.database.update("ArirangSonglist", values,
                        "MABH = ?", new String[]{masoNhan});

                btnthichsub.setVisibility(View.GONE);
                btnkhongthichsub.setVisibility(View.VISIBLE);
            }
        });

        btnkhongthichsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 0); // Bỏ thích
                MainActivity.database.update("ArirangSonglist", values,
                        "MABH = ?", new String[]{masoNhan});

                btnthichsub.setVisibility(View.VISIBLE);
                btnkhongthichsub.setVisibility(View.GONE);
            }
        });
    }
}