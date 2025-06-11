package com.example.lab_19;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnparse;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnparse = findViewById(R.id.btnparse);
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);

        btnparse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasrexml();
            }
        });
    }

    private void pasrexml() {
        try {
            InputStream myInput = getAssets().open("employee.xml");
            XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
            XmlPullParser parser = fc.newPullParser();
            parser.setInput(myInput, null);

            int eventType = parser.getEventType();
            String nodeName;
            String datashow = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        nodeName = parser.getName();
                        if (nodeName.equals("employee")) {
                            datashow += parser.getAttributeValue(0) + "-";
                            datashow += parser.getAttributeValue(1) + "-";
                        } else if (nodeName.equals("name")) {
                            parser.next();
                            datashow += parser.getText() + "-";
                        } else if (nodeName.equals("phone")) {
                            parser.next();
                            datashow += parser.getText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        nodeName = parser.getName();
                        if (nodeName.equals("employee")) {
                            mylist.add(datashow);
                            datashow = "";
                        }
                        break;
                }
                eventType = parser.next();
            }
            myadapter.notifyDataSetChanged();

        } catch (Exception e) { // Đã sửa từ IOException và XmlPullParserException thành Exception chung để bắt tất cả lỗi
            e.printStackTrace();
        }
    }
}