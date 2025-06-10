package com.example.lab_12_03;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String arr1[] = {"Hàng Điện tử", "Hàng Hóa Chất", "Hàng Gia dụng", "Hàng xây dựng"};
    TextView txt1;
    Spinner spin1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            // Liên kết các View từ XML với các biến trong Java
            txt1 = findViewById(R.id.txt1);
            spin1 = findViewById(R.id.spinner1);
            // Tạo ArrayAdapter để đưa dữ liệu vào Spinner
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item, // Layout cho mục hiển thị trong Spinner
                    arr1
            );

            // Đặt layout cho danh sách thả xuống của Spinner
            adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

            // Gán adapter cho Spinner
            spin1.setAdapter(adapter1);

            // Xử lý sự kiện khi một mục trong Spinner được chọn
            spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Cập nhật TextView với giá trị của mục được chọn
                    txt1.setText(arr1[position]);

                    // Hiển thị một thông báo ngắn (Toast)
                    Toast.makeText(MainActivity.this, "Bạn đã chọn: " + arr1[position], Toast.LENGTH_SHORT).show();

                    if (position == 0) { // Nếu chọn "Hàng Điện tử"
                        Toast.makeText(MainActivity.this, "Đang xử lý cho: Hàng Điện tử", Toast.LENGTH_SHORT).show();
                        // Ví dụ: Bạn có thể chuyển sang một màn hình khác (Activity mới)
                        // Intent intent = new Intent(MainActivity.this, ElectronicsActivity.class);
                        // startActivity(intent);
                    } else if (position == 1) { // Nếu chọn "Hàng Hóa Chất"
                        Toast.makeText(MainActivity.this, "Đang xử lý cho: Hàng Hóa Chất", Toast.LENGTH_SHORT).show();
                        // Ví dụ: Hiển thị một hộp thoại thông báo
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Thông báo")
                                .setMessage("Bạn đã chọn Hàng Hóa Chất. Vui lòng kiểm tra thông tin an toàn.")
                                .setPositiveButton("OK", null)
                                .show();
                    } else if (position == 2) { // Nếu chọn "Hàng Gia dụng"
                        Toast.makeText(MainActivity.this, "Đang xử lý cho: Hàng Gia dụng", Toast.LENGTH_SHORT).show();
                        // Ví dụ: Thay đổi màu nền của TextView
                        txt1.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                    } else if (position == 3) { // Nếu chọn "Hàng xây dựng"
                        Toast.makeText(MainActivity.this, "Đang xử lý cho: Hàng xây dựng", Toast.LENGTH_SHORT).show();
                        // Ví dụ: Thay đổi nội dung của TextView thành một thông báo khác
                        txt1.setText("Đã chọn Vật liệu Xây dựng!");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Phương thức này được gọi khi không có mục nào được chọn, thường không cần xử lý nhiều cho Spinner
                }
            });
            return insets;
        });
}}