package com.example.lab_17;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Đường dẫn đến thư mục chứa database trong ứng dụng
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;
    // Tên của file database trong thư mục assets
    String DATABASE_NAME = "sach.db";

    // Khai báo ListView và Adapter
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gọi hàm Copy CSDL từ assets vào thư mục Databases
        processCopy();

        // Mở CSDL đã có
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        // Tạo ListView
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);

        // Truy vấn CSDL và cập nhật hiển thị lên ListView
        Cursor c = database.query("tbsach", null, null, null, null, null, null);
        c.moveToFirst();
        String data = "";
        while (!c.isAfterLast()) {
            // Lấy dữ liệu từ cột 0 và cột 1 (ví dụ)
            data = c.getString(0) + " - " + c.getString(1);
            mylist.add(data);
            c.moveToNext();
        }
        c.close();
        myadapter.notifyDataSetChanged();
    }

    // Phương thức kiểm tra và sao chép database
    private void processCopy() {
        // Kiểm tra xem file database đã tồn tại trong thư mục ứng dụng chưa
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                // Nếu chưa tồn tại, thực hiện sao chép
                CopyDatabaseFromAsset();
                Toast.makeText(this, "Copying success from Assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                // Xử lý lỗi nếu có
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    // Phương thức lấy đường dẫn đầy đủ đến database trong ứng dụng
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    // Phương thức sao chép database từ thư mục assets vào ứng dụng
    public void CopyDatabaseFromAsset() throws IOException {
        InputStream myInput = getAssets().open(DATABASE_NAME);
        // Đường dẫn đến thư mục sẽ lưu trữ database trong ứng dụng
        String outFile = getDatabasePath();

        // Tạo thư mục nếu nó chưa tồn tại
        File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists()) {
            f.mkdir();
        }

        // Mở file output rỗng để ghi dữ liệu
        OutputStream myOutput = new FileOutputStream(outFile);

        // Truyền bytes từ input sang output
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Đóng các luồng
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}