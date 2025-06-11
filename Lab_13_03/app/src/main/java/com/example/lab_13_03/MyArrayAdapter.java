package com.example.lab_13_03;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<Phone> {
    Activity context;
    int layoutId;
    ArrayList<Phone> phones;

    public MyArrayAdapter(Activity context, int layoutId, ArrayList<Phone> phones) {
        super(context, layoutId, phones);
        this.context = context;
        this.layoutId = layoutId;
        this.phones = phones;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(layoutId, null);

        ImageView img = row.findViewById(R.id.imgphone);
        TextView txt = row.findViewById(R.id.txtnamephone);

        Phone p = phones.get(position);
        img.setImageResource(p.image);
        txt.setText(p.name);

        return row;
    }
}

