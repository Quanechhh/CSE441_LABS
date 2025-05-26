package com.example.mytodolist;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lấy toolbar từ layout và set làm ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // Nạp menu từ file XML
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_option_menu, menu);
        Log.d(TAG, "onCreateOptionsMenu đã được gọi và nạp menu.");
        return true;
    }

    // Xử lý sự kiện nhấn vào mục menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Log.d(TAG, "onOptionsItemSelected được gọi cho item: " + item.getTitle());

        if (itemId == R.id.action_settings) {
            Log.i(TAG, "Mục Cài đặt được chọn.");
            Toast.makeText(this, R.string.settings_selected, Toast.LENGTH_SHORT).show();
            return true;

        } else if (itemId == R.id.action_about) {
            Log.i(TAG, "Mục Giới thiệu được chọn.");
            Toast.makeText(this, R.string.about_selected, Toast.LENGTH_SHORT).show();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
