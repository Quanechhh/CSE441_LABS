package com.example.lab_13_04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyGridAdapter extends BaseAdapter {
    private Context context;
    private String[] tenAnh;
    private int[] hinhAnh;

    public MyGridAdapter(Context context, String[] tenAnh, int[] hinhAnh) {
        this.context = context;
        this.tenAnh = tenAnh;
        this.hinhAnh = hinhAnh;
    }

    @Override
    public int getCount() {
        return tenAnh.length;
    }

    @Override
    public Object getItem(int i) {
        return tenAnh[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_gridview, parent, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);

        imageView.setImageResource(hinhAnh[i]);
        textView.setText(tenAnh[i]);

        return view;
    }
}

