package com.example.lab_23;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<List> {
    private Context context;
    private int layoutID;
    private ArrayList<List> arr;

    public MyArrayAdapter(@NonNull Context context, int layoutID, @NonNull ArrayList<List> arr) {
        super(context, layoutID, arr);
        this.context = context;
        this.layoutID = layoutID;
        this.arr = arr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Sử dụng ViewHolder pattern để tái sử dụng Views
        ViewHolder holder;

        if (convertView == null) {
            // Nếu convertView là null, inflate layout mới và tạo ViewHolder
            LayoutInflater inflater = LayoutInflater.from(context); // Cách lấy LayoutInflater tốt hơn
            convertView = inflater.inflate(layoutID, parent, false); // Sử dụng parent và false để AttachToRoot

            holder = new ViewHolder();
            holder.imgItem = convertView.findViewById(R.id.imgView);
            holder.txtTitle = convertView.findViewById(R.id.txtTitle);
            holder.txtInfo = convertView.findViewById(R.id.txtInfo);

            convertView.setTag(holder); // Lưu trữ ViewHolder vào Tag của convertView
        } else {
            // Nếu convertView không null, lấy ViewHolder từ Tag để tái sử dụng các View đã có
            holder = (ViewHolder) convertView.getTag();
        }

        final List item = arr.get(position);

        // Đặt dữ liệu vào các View thông qua ViewHolder
        if (item.getImg() != null) {
            holder.imgItem.setImageBitmap(item.getImg());
        } else {
            // Đặt một ảnh placeholder nếu không có ảnh hoặc ảnh tải lỗi
            // Đảm bảo bạn có một drawable tên là ic_launcher_background trong res/drawable
            holder.imgItem.setImageResource(R.drawable.ic_launcher_background); // Thay thế bằng placeholder của bạn
        }

        holder.txtTitle.setText(item.getTitle());
        holder.txtInfo.setText(item.getInfo());

        // Đặt OnClickListener cho toàn bộ item
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(item.getLink()));
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    // Lớp ViewHolder để giữ các tham chiếu đến Views
    static class ViewHolder {
        ImageView imgItem;
        TextView txtTitle;
        TextView txtInfo;
    }
}