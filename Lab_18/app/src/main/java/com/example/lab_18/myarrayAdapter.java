package com.example.lab_18;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class myarrayAdapter extends ArrayAdapter<Item> {
    Activity context = null;
    ArrayList<Item> myArray = null;
    int LayoutId;

    public myarrayAdapter(Activity context, int LayoutId, ArrayList<Item> arr) {
        super(context, LayoutId, arr);
        this.context = context;
        this.LayoutId = LayoutId;
        this.myArray = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(LayoutId, null);

        // Lấy Item tại vị trí hiện tại
        final Item myItem = myArray.get(position);

        // Ánh xạ và thiết lập dữ liệu cho TextViews
        TextView tieude = convertView.findViewById(R.id.txttieude);
        tieude.setText(myItem.getTieude());
        TextView maso = convertView.findViewById(R.id.txtmaso);
        maso.setText(myItem.getMaso());

        // Ánh xạ ImageButtons
        final ImageButton btnthich = convertView.findViewById(R.id.btnthich);
        final ImageButton btnkhongthich = convertView.findViewById(R.id.btnkhongthich);

        // Xử lý hiển thị cho ImageButton btnthich và btnkhongthich
        if (myItem.getThich() == 0) { // Không thích
            btnthich.setVisibility(View.VISIBLE); // Hiển thị nút "Thích"
            btnkhongthich.setVisibility(View.GONE); // Ẩn nút "Không thích"
        } else { // Thích
            btnthich.setVisibility(View.GONE); // Ẩn nút "Thích"
            btnkhongthich.setVisibility(View.VISIBLE); // Hiển thị nút "Không thích"
        }

        // Xử lý sự kiện khi click vào ImageButton btnthich
        btnthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 1); // Đánh dấu là thích (1)
                // Cập nhật CSDL
                MainActivity.database.update("ArirangSonglist", values,
                        "MABH = ?", new String[]{maso.getText().toString()});

                // Cập nhật giao diện ngay lập tức
                btnthich.setVisibility(View.GONE);
                btnkhongthich.setVisibility(View.VISIBLE);
                // Cập nhật dữ liệu trong adapter để listview được refresh
                myItem.setThich(1); // Cập nhật trạng thái yêu thích trong đối tượng Item
                notifyDataSetChanged(); // Yêu cầu Adapter cập nhật lại ListView
            }
        });

        // Xử lý sự kiện khi click vào ImageButton btnkhongthich
        btnkhongthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 0); // Bỏ thích (0)
                // Cập nhật CSDL
                MainActivity.database.update("ArirangSonglist", values,
                        "MABH = ?", new String[]{maso.getText().toString()});

                // Cập nhật giao diện ngay lập tức
                btnthich.setVisibility(View.VISIBLE);
                btnkhongthich.setVisibility(View.GONE);
                // Cập nhật dữ liệu trong adapter để listview được refresh
                myItem.setThich(0); // Cập nhật trạng thái yêu thích trong đối tượng Item
                notifyDataSetChanged(); // Yêu cầu Adapter cập nhật lại ListView
            }
        });

        // Xử lý sự kiện khi Click vào mỗi dòng tiêu đề bài hát từ ListView
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay đổi màu sắc của tiêu đề khi click
                tieude.setTextColor(Color.RED);

                // Khởi tạo Intent và truyền dữ liệu sang subactivity
                Intent intent1 = new Intent(context, subactivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("maso", myItem.getMaso());
                bundle.putString("tieude", myItem.getTieude());
                intent1.putExtra("package", bundle); // Truyền Bundle qua Intent
                context.startActivity(intent1);
            }
        });

        return convertView;
    }
}
