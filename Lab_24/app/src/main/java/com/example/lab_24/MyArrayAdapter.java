package com.example.lab_24;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List; // Sử dụng java.util.List để tránh nhầm lẫn với com.example.lab_23.List cũ

public class MyArrayAdapter extends ArrayAdapter<Tygia> { // Kế thừa ArrayAdapter<Tygia>

    private Context context;
    private int resource; // resource = layoutResourceId
    private java.util.List<Tygia> objects; // Đổi từ ArrayList thành java.util.List

    public MyArrayAdapter(@NonNull Context context, int resource, @NonNull java.util.List<Tygia> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = convertView;
        ViewHolder holder;

        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(resource, parent, false); // resource = layout_listview.xml

            holder = new ViewHolder();
            holder.imgHinh = item.findViewById(R.id.imgHinh); // R.id.imgHinh
            holder.txtType = item.findViewById(R.id.txtType); // R.id.txtType
            holder.txtMuaTM = item.findViewById(R.id.txtMuaTM); // R.id.txtMuaTM
            holder.txtMuaCK = item.findViewById(R.id.txtMuaCK); // R.id.txtMuaCK
            holder.txtBanTM = item.findViewById(R.id.txtBanTM); // R.id.txtBanTM
            holder.txtBanCK = item.findViewById(R.id.txtBanCK); // R.id.txtBanCK

            item.setTag(holder);
        } else {
            holder = (ViewHolder) item.getTag();
        }

        Tygia tygia = objects.get(position); // Lấy đối tượng Tygia tại vị trí hiện tại

        // Đặt dữ liệu vào các View
        if (tygia.getBitmap() != null) {
            holder.imgHinh.setImageBitmap(tygia.getBitmap());
        } else {
            holder.imgHinh.setImageResource(R.drawable.ic_launcher_background); // Ảnh placeholder
        }
        holder.txtType.setText(tygia.getType());
        holder.txtMuaTM.setText(tygia.getMuatienmat());
        holder.txtMuaCK.setText(tygia.getMuack());
        holder.txtBanTM.setText(tygia.getBantuenmat());
        holder.txtBanCK.setText(tygia.getBanck());

        return item;
    }

    static class ViewHolder { // Lớp ViewHolder để tối ưu hiệu suất ListView
        ImageView imgHinh;
        TextView txtType;
        TextView txtMuaTM;
        TextView txtMuaCK;
        TextView txtBanTM;
        TextView txtBanCK;
    }
}
