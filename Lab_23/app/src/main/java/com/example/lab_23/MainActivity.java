package com.example.lab_23;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log; // Import Log
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvl; // Bỏ static
    private ArrayList<List> mylist; // Bỏ static
    private MyArrayAdapter myadapter; // Bỏ static
    private String urlStr = "https://vnexpress.net/rss/tin-moi-nhat.rss"; // Bỏ static

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvl = findViewById(R.id.lvl);
        mylist = new ArrayList<>();
        myadapter = new MyArrayAdapter(this, R.layout.layout_listview, mylist);
        lvl.setAdapter(myadapter);

        // Bắt đầu tải dữ liệu khi ứng dụng khởi động
        new LoadExampleTask().execute();
    }

    // Class LoadExampleTask không cần là static nếu nó là inner class của MainActivity
    // nhưng nếu là nested static class thì cần truyền context cho constructor
    // Ở đây, nó là inner non-static class nên có thể truy cập các biến của MainActivity
    class LoadExampleTask extends AsyncTask<Void, Void, ArrayList<List>> {
        @Override
        protected ArrayList<List> doInBackground(Void... voids) {
            ArrayList<List> resultList = new ArrayList<>();
            try {
                XMLParser parser = new XMLParser();
                String xml = parser.getXmlFromUrl(urlStr);
                if (xml != null && !xml.isEmpty()) {
                    resultList = parser.parseXml(xml);
                } else {
                    Log.e("MainActivity", "XML is null or empty. Cannot parse.");
                }
            } catch (Exception e) {
                Log.e("MainActivity", "Error in doInBackground: " + e.getMessage(), e);
            }
            return resultList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Xóa dữ liệu hiện có trong adapter trước khi tải cái mới
            myadapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<List> result) {
            super.onPostExecute(result);
            if (result != null && !result.isEmpty()) {
                // Thêm tất cả các item đã parse vào adapter
                myadapter.addAll(result);
            } else {
                Log.w("MainActivity", "No items parsed or result is null/empty.");
                // Có thể hiển thị thông báo "Không có dữ liệu" trên UI nếu muốn
            }
            // Thông báo cho adapter biết dữ liệu đã thay đổi để cập nhật ListView
            myadapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            // Có thể dùng để cập nhật ProgressBar nếu cần
        }
    }
}