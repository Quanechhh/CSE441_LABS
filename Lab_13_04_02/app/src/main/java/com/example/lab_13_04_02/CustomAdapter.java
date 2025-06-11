package com.example.lab_13_04_02;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    Context context;
    String[] ten;
    String[] sdt;

    public CustomAdapter(Context context, String[] ten, String[] sdt) {
        this.context = context;
        this.ten = ten;
        this.sdt = sdt;
    }

    @Override
    public int getCount() {
        return ten.length;
    }

    @Override
    public Object getItem(int position) {
        return ten[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView txtTen, txtSdt;
        ImageButton btnCall, btnSms, btnSave;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_item, parent, false);

            holder.txtTen = convertView.findViewById(R.id.txtTen);
            holder.txtSdt = convertView.findViewById(R.id.txtSdt);
            holder.btnCall = convertView.findViewById(R.id.btnCall);
            holder.btnSms = convertView.findViewById(R.id.btnSms);
            holder.btnSave = convertView.findViewById(R.id.btnSave);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTen.setText(ten[position]);
        holder.txtSdt.setText(sdt[position]);

        String phone = sdt[position];

        holder.btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            context.startActivity(intent);
        });

        holder.btnSms.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
            context.startActivity(intent);
        });

        holder.btnSave.setOnClickListener(v -> {
            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            intent.putExtra(ContactsContract.Intents.Insert.NAME, ten[position]);
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
            context.startActivity(intent);
        });

        return convertView;
    }
}
