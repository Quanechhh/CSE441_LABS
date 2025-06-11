package com.example.lab_13_02_02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    private final Context ctx;
    private final int[] data;
    private final LayoutInflater inflater;

    public ImageAdapter(Context c, int[] images) {
        ctx = c;
        data = images;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() { return data.length; }

    @Override
    public Object getItem(int position) { return data[position]; }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            row = inflater.inflate(R.layout.grid_item, parent, false);
            holder = new ViewHolder();
            holder.img = row.findViewById(R.id.imgThumb);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.img.setImageResource(data[position]);
        return row;
    }

    static class ViewHolder {
        ImageView img;
    }
}

