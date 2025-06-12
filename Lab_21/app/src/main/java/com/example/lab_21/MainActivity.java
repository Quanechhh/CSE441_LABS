package com.example.lab_21;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnparse;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnparse = findViewById(R.id.btnparse);
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);

        btnparse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJson(); // Đổi tên hàm từ parsejson() thành parseJson() để rõ ràng hơn
            }
        });
    }

    private void parseJson() {
        String json = null;
        try {
            InputStream is = getAssets().open("sanpham.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject reader = new JSONObject(json);

            // Lấy thông tin MaDM và TenDM
            String maDM = reader.getString("MaDM");
            String tenDM = reader.getString("TenDM");
            mylist.add(maDM);
            mylist.add(tenDM);

            // Lấy thông tin Sanphams
            JSONArray myarray = reader.getJSONArray("Sanphams");
            for (int i = 0; i < myarray.length(); i++) {
                JSONObject myobj = myarray.getJSONObject(i);
                // Định dạng hiển thị theo yêu cầu
                String sanPhamInfo = myobj.getString("MaSP") + " - " + myobj.getString("TenSP");
                mylist.add(sanPhamInfo);

                // Tính toán và hiển thị SoLuong * DonGia
                int soLuong = myobj.getInt("SoLuong"); // Use getInt for integer values
                long donGia = myobj.getLong("DonGia"); // Use getLong for potentially large monetary values
                long thanhTienCalculated = (long)soLuong * donGia;
                mylist.add(soLuong + " * " + donGia + " = " + thanhTienCalculated);

                mylist.add(myobj.getString("Hinh"));
            }
            myadapter.notifyDataSetChanged();

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }
}