package com.example.lab_22;

import android.os.AsyncTask; //
import android.os.Bundle; //
import android.util.Log; //
import android.view.View; //
import android.widget.ArrayAdapter; //
import android.widget.Button; //
import android.widget.ListView; //

import androidx.appcompat.app.AppCompatActivity; //

import org.xmlpull.v1.XmlPullParser; //
import org.xmlpull.v1.XmlPullParserFactory; //

import java.io.IOException; //
import java.io.StringReader; //
import java.util.ArrayList; //

public class MainActivity extends AppCompatActivity { //
    Button btnparse; //
    ListView lv1; //
    ArrayAdapter<String> myadapter; //
    ArrayList<String> mylist; //
    String URL = "https://api.androidhive.info/pizza/?format=xml"; //

    @Override //
    protected void onCreate(Bundle savedInstanceState) { //
        super.onCreate(savedInstanceState); //
        setContentView(R.layout.activity_main); //

        btnparse = findViewById(R.id.btnparse); //
        lv1 = findViewById(R.id.lv1); //
        mylist = new ArrayList<>(); //
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist); //
        lv1.setAdapter(myadapter); //

        btnparse.setOnClickListener(new View.OnClickListener() { //
            @Override //
            public void onClick(View v) { //
                LoadExampleTask task = new LoadExampleTask(); //
                task.execute(); //
            }
        });
    }

    // Lớp LoadExampleTask extends AsyncTask
    class LoadExampleTask extends AsyncTask<Void, Void, ArrayList<String>> { //

        @Override //
        protected void onPreExecute() { //
            super.onPreExecute(); //
            myadapter.clear(); // Xóa dữ liệu cũ trước khi parse mới
        }

        @Override //
        protected ArrayList<String> doInBackground(Void... voids) { //
            ArrayList<String> list = new ArrayList<>(); //
            XMLParser myparser = new XMLParser(); //
            String xml = myparser.getXmlFromUrl(URL); //
            Log.d("XML_DATA", xml); // Log dữ liệu XML nhận được để kiểm tra

            try { //
                XmlPullParserFactory fc = XmlPullParserFactory.newInstance(); //
                XmlPullParser parser = fc.newPullParser(); //
                parser.setInput(new StringReader(xml)); //
                int eventType = parser.getEventType(); //
                String nodeName; //
                String dataShow = ""; // Khởi tạo để tránh lỗi null

                while (eventType != XmlPullParser.END_DOCUMENT) { //
                    switch (eventType) { //
                        case XmlPullParser.START_DOCUMENT: //
                            break; //
                        case XmlPullParser.START_TAG: //
                            nodeName = parser.getName(); //
                            if (nodeName.equals("id")) { //
                                parser.next(); // Di chuyển đến nội dung của thẻ
                                dataShow += parser.getText() + "-"; //
                            } else if (nodeName.equals("name")) { //
                                parser.next(); //
                                dataShow += parser.getText(); //
                            }
                            break; //
                        case XmlPullParser.END_TAG: //
                            nodeName = parser.getName(); //
                            if (nodeName.equals("item")) { //
                                list.add(dataShow); //
                                dataShow = ""; // Reset sau khi thêm vào list
                            }
                            break; //
                    }
                    eventType = parser.next(); //
                }
            } catch (Exception e) { // Sử dụng Exception chung để bắt cả XmlPullParserException và IOException
                e.printStackTrace(); //
                Log.e("XML_PARSE_ERROR", "Error parsing XML", e);
            }
            return list; //
        }

        @Override //
        protected void onPostExecute(ArrayList<String> result) { //
            super.onPostExecute(result); //
            myadapter.clear(); // Xóa dữ liệu cũ một lần nữa (đảm bảo không trùng lặp nếu onPreExecute không chạy kịp)
            myadapter.addAll(result); // Thêm tất cả kết quả vào adapter
            myadapter.notifyDataSetChanged(); // Cập nhật ListView
        }

        @Override //
        protected void onProgressUpdate(Void... values) { //
            super.onProgressUpdate(values); //
            // Không sử dụng trong bài này vì không có cập nhật tiến độ liên tục
        }
    }
}