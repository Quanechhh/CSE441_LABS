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
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Hằng số cho việc quản lý database
    private static final String DB_PATH_SUFFIX = "/databases/";
    private static final String DATABASE_NAME = "qlsach.db"; // Tên file database của bạn

    private SQLiteDatabase database = null;
    private ListView lv;
    private ArrayList<String> mylist;
    private ArrayAdapter<String> myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Đặt layout cho Activity

        // Gọi phương thức để copy database từ assets sang hệ thống nếu cần
        processCopy();

        // Mở database
        // MODE_PRIVATE đảm bảo chỉ ứng dụng này có thể truy cập
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        lv = findViewById(R.id.lv); // Ánh xạ ListView từ layout
        mylist = new ArrayList<>(); // Khởi tạo ArrayList để lưu dữ liệu
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist); // Tạo ArrayAdapter
        lv.setAdapter(myadapter); // Gán adapter cho ListView

        // Truy vấn dữ liệu từ bảng tbsach và hiển thị lên ListView
        // Đảm bảo tên bảng và tên cột trùng khớp với database của bạn
        Cursor c = database.rawQuery("SELECT * FROM tbsach", null);
        c.moveToFirst(); // Di chuyển con trỏ đến hàng đầu tiên
        while (!c.isAfterLast()) { // Lặp qua tất cả các hàng
            // Lấy dữ liệu từ các cột và định dạng thành chuỗi để hiển thị
            String maSach = c.getString(c.getColumnIndexOrThrow("MaSach"));
            String tenSach = c.getString(c.getColumnIndexOrThrow("TenSach"));
            int soTrang = c.getInt(c.getColumnIndexOrThrow("SoTrang"));
            double gia = c.getDouble(c.getColumnIndexOrThrow("Gia")); // Sử dụng getDouble cho kiểu REAL

            String data = maSach + " - " + tenSach + " - " + soTrang + " trang - " + gia + " VNĐ";
            mylist.add(data); // Thêm chuỗi dữ liệu vào ArrayList
            c.moveToNext(); // Di chuyển con trỏ đến hàng tiếp theo
        }
        c.close(); // Đóng Cursor
        database.close(); // Đóng kết nối database sau khi hoàn thành
        myadapter.notifyDataSetChanged(); // Cập nhật ListView để hiển thị dữ liệu mới
    }

    private void processCopy() {
        // Lấy đường dẫn tới file database trên thiết bị
        File dbFile = getDatabasePath(DATABASE_NAME);
        // Kiểm tra xem database đã tồn tại trong thư mục dữ liệu của ứng dụng chưa
        if (!dbFile.exists()) {
            try {
                CopyDatabaseFromAsset(); // Nếu chưa, thực hiện copy
                Toast.makeText(this, "Copying success from Assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                // Xử lý lỗi nếu việc copy thất bại
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDatabasePath() {
        // Trả về đường dẫn đầy đủ tới file database trên thiết bị
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    public void CopyDatabaseFromAsset() throws IOException {
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            // Mở file database cục bộ từ thư mục assets làm luồng đầu vào
            myInput = getAssets().open(DATABASE_NAME);

            // Đường dẫn đến file database rỗng vừa tạo trên thiết bị
            String outFile = getDatabasePath();

            // Nếu đường dẫn chưa tồn tại, tạo nó trước
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) {
                f.mkdir(); // Tạo thư mục
            }

            // Mở file database rỗng làm luồng đầu ra
            myOutput = new FileOutputStream(outFile);

            // Truyền byte từ luồng đầu vào sang luồng đầu ra
            byte[] buffer = new byte[1024]; // Sử dụng bộ đệm để tăng hiệu suất
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush(); // Đảm bảo tất cả dữ liệu được ghi
        } finally {
            // Đóng các luồng để giải phóng tài nguyên
            if (myOutput != null) {
                myOutput.close();
            }
            if (myInput != null) {
                myInput.close();
            }
        }
    }
}