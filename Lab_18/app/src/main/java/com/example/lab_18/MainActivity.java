package com.example.lab_18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log; // Import Log
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    String DATABASE_NAME = "arirang.sqlite";

    EditText edttim;
    ListView lv1, lv2, lv3;
    ArrayList<Item> list1, list2, list3;
    myarrayAdapter myarray1, myarray2, myarray3;
    TabHost tabhost;
    ImageButton btnxoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processCopy();
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        addControls();
        addEvents();
    }

    private void addControls() {
        tabhost = findViewById(android.R.id.tabhost);
        tabhost.setup();

        TabHost.TabSpec tab1 = tabhost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Tìm kiếm", getResources().getDrawable(R.drawable.search));
        tabhost.addTab(tab1);

        TabHost.TabSpec tab2 = tabhost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Danh sách", getResources().getDrawable(R.drawable.list));
        tabhost.addTab(tab2);

        TabHost.TabSpec tab3 = tabhost.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("Yêu thích", getResources().getDrawable(R.drawable.favourite));
        tabhost.addTab(tab3);

        edttim = findViewById(R.id.edttim);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        lv3 = findViewById(R.id.lv3);
        btnxoa = findViewById(R.id.btnxoa);

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        myarray1 = new myarrayAdapter(MainActivity.this, R.layout.listitem, list1);
        lv1.setAdapter(myarray1);

        myarray2 = new myarrayAdapter(MainActivity.this, R.layout.listitem, list2);
        lv2.setAdapter(myarray2);

        myarray3 = new myarrayAdapter(MainActivity.this, R.layout.listitem, list3);
        lv3.setAdapter(myarray3);
    }

    private void addEvents() {
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equalsIgnoreCase("t2")) {
                    addDanhsach();
                } else if (tabId.equalsIgnoreCase("t3")) {
                    addYeuthich();
                }
            }
        });

        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edttim.setText("");
            }
        });

        edttim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getData();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void addYeuthich() {
        myarray3.clear();
        Cursor c = database.rawQuery("SELECT * FROM ArirangSonglist WHERE YEUTHICH = 1", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            // Sử dụng getColumnIndex() để an toàn hơn
            int maBhColIndex = c.getColumnIndex("MABH");
            int tenBhColIndex = c.getColumnIndex("TENBH");
            int yeuThichColIndex = c.getColumnIndex("YEUTHICH"); // Tên cột yêu thích

            if (maBhColIndex != -1 && tenBhColIndex != -1 && yeuThichColIndex != -1) {
                list3.add(new Item(c.getString(maBhColIndex), c.getString(tenBhColIndex), c.getInt(yeuThichColIndex)));
            } else {
                Log.e("MainActivity", "addYeuthich: Column(s) not found in Cursor for YEUTHICH table.");
                // Hoặc bạn có thể thêm một Toast thông báo lỗi cho người dùng
                // Toast.makeText(this, "Lỗi dữ liệu: Cột Yêu thích không tìm thấy.", Toast.LENGTH_SHORT).show();
            }
            c.moveToNext();
        }
        c.close();
        myarray3.notifyDataSetChanged();
    }

    private void addDanhsach() {
        myarray2.clear();
        Cursor c = database.rawQuery("SELECT * FROM ArirangSonglist", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            // Sử dụng getColumnIndex() để an toàn hơn
            int maBhColIndex = c.getColumnIndex("MABH");
            int tenBhColIndex = c.getColumnIndex("TENBH");
            int yeuThichColIndex = c.getColumnIndex("YEUTHICH"); // Tên cột yêu thích

            // Kiểm tra xem các cột có tồn tại không trước khi truy cập
            if (maBhColIndex != -1 && tenBhColIndex != -1 && yeuThichColIndex != -1) {
                list2.add(new Item(c.getString(maBhColIndex), c.getString(tenBhColIndex), c.getInt(yeuThichColIndex)));
            } else {
                Log.e("MainActivity", "addDanhsach: Column(s) not found in Cursor for full list.");
                // Hoặc bạn có thể thêm một Toast thông báo lỗi cho người dùng
                // Toast.makeText(this, "Lỗi dữ liệu: Cột không tìm thấy.", Toast.LENGTH_SHORT).show();
                // Thoát vòng lặp nếu có lỗi nghiêm trọng để tránh crash
                break;
            }
            c.moveToNext();
        }
        c.close();
        myarray2.notifyDataSetChanged();
    }

    private void getData() {
        myarray1.clear();
        String dulieunhap = edttim.getText().toString().trim();

        if (dulieunhap.equals("")) {
            myarray1.notifyDataSetChanged();
            return;
        }

        Cursor c = database.rawQuery("SELECT * FROM ArirangSonglist WHERE MABH LIKE '%" + dulieunhap + "%' OR TENBH LIKE '%" + dulieunhap + "%'", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            // Sử dụng getColumnIndex() để an toàn hơn
            int maBhColIndex = c.getColumnIndex("MABH");
            int tenBhColIndex = c.getColumnIndex("TENBH");
            int yeuThichColIndex = c.getColumnIndex("YEUTHICH"); // Tên cột yêu thích

            if (maBhColIndex != -1 && tenBhColIndex != -1 && yeuThichColIndex != -1) {
                list1.add(new Item(c.getString(maBhColIndex), c.getString(tenBhColIndex), c.getInt(yeuThichColIndex)));
            } else {
                Log.e("MainActivity", "getData: Column(s) not found in Cursor for search results.");
                // Hoặc bạn có thể thêm một Toast thông báo lỗi cho người dùng
                // Toast.makeText(this, "Lỗi dữ liệu: Cột tìm kiếm không tìm thấy.", Toast.LENGTH_SHORT).show();
            }
            c.moveToNext();
        }
        c.close();
        myarray1.notifyDataSetChanged();
    }

    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDatabaseFromAsset();
                Toast.makeText(this, "Copying success from Assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void CopyDatabaseFromAsset() throws IOException {
        InputStream myInput = getAssets().open(DATABASE_NAME);
        String outFile = getDatabasePath(DATABASE_NAME).getAbsolutePath();

        File directory = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        OutputStream myOutput = new FileOutputStream(outFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    // Phương thức để cập nhật trạng thái yêu thích của bài hát trong SQLite
    public static void CapNhatYeuThich(String maBH, int yeuThich) {
        ContentValues values = new ContentValues();
        values.put("YEUTHICH", yeuThich);
        // Kiểm tra xem database có mở không trước khi thao tác
        if (database != null && database.isOpen()) {
            database.update("ArirangSonglist", values, "MABH=?", new String[]{maBH});
        } else {
            Log.e("MainActivity", "Database is not open for updating YEUTHICH.");
        }
    }
}