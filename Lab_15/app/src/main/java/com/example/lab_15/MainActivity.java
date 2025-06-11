package com.example.lab_15;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtMaLop, edtTenLop, edtSiSo;
    private Button btnInsert, btnUpdate, btnDelete, btnQuery;
    private ListView listView;

    private MyDatabaseHelper dbHelper;
    private List<ClassModel> classList;
    private ArrayAdapter<ClassModel> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        edtMaLop = findViewById(R.id.edtmalop);
        edtTenLop = findViewById(R.id.edttenlop);
        edtSiSo = findViewById(R.id.edtsiso);
        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnQuery = findViewById(R.id.btnQuery);
        listView = findViewById(R.id.lv);

        // Initialize database helper
        dbHelper = new MyDatabaseHelper(this);

        // Initialize ListView and Adapter
        classList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, classList);
        listView.setAdapter(adapter);

        // --- Set OnClickListeners for Buttons ---
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryData();
            }
        });

        // --- Set OnItemClickListener for ListView to populate EditTexts ---
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassModel selectedClass = (ClassModel) parent.getItemAtPosition(position);
                if (selectedClass != null) {
                    edtMaLop.setText(selectedClass.getMaLop());
                    edtTenLop.setText(selectedClass.getTenLop());
                    edtSiSo.setText(String.valueOf(selectedClass.getSiSo()));
                    // Disable MaLop for update/delete to prevent accidental changes to PK
                    edtMaLop.setEnabled(false);
                }
            }
        });

        // Initial query to load existing data
        queryData();
    }

    private void insertData() {
        String maLop = edtMaLop.getText().toString().trim();
        String tenLop = edtTenLop.getText().toString().trim();
        String siSoStr = edtSiSo.getText().toString().trim();

        if (maLop.isEmpty() || tenLop.isEmpty() || siSoStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int siSo = Integer.parseInt(siSoStr);
            long result = dbHelper.insertClass(maLop, tenLop, siSo);
            if (result != -1) {
                Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                clearFields();
                queryData(); // Refresh list
            } else {
                Toast.makeText(this, "Thêm thất bại. Mã lớp có thể đã tồn tại.", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Sĩ số phải là số.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData() {
        String maLop = edtMaLop.getText().toString().trim(); // MaLop is disabled, so it's from selection
        String tenLop = edtTenLop.getText().toString().trim();
        String siSoStr = edtSiSo.getText().toString().trim();

        if (maLop.isEmpty() || tenLop.isEmpty() || siSoStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn lớp cần cập nhật và nhập đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int siSo = Integer.parseInt(siSoStr);
            int rowsAffected = dbHelper.updateClass(maLop, tenLop, siSo);
            if (rowsAffected > 0) {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                clearFields();
                queryData(); // Refresh list
            } else {
                Toast.makeText(this, "Cập nhật thất bại. Mã lớp không tồn tại hoặc không có thay đổi.", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Sĩ số phải là số.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData() {
        String maLop = edtMaLop.getText().toString().trim();

        if (maLop.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn lớp cần xóa.", Toast.LENGTH_SHORT).show();
            return;
        }

        int rowsAffected = dbHelper.deleteClass(maLop);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            clearFields();
            queryData(); // Refresh list
        } else {
            Toast.makeText(this, "Xóa thất bại. Mã lớp không tồn tại.", Toast.LENGTH_SHORT).show();
        }
    }

    private void queryData() {
        classList.clear();
        classList.addAll(dbHelper.getAllClasses());
        adapter.notifyDataSetChanged();
        if (classList.isEmpty()) {
            Toast.makeText(this, "Không có dữ liệu lớp học.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        edtMaLop.setText("");
        edtTenLop.setText("");
        edtSiSo.setText("");
        edtMaLop.setEnabled(true); // Re-enable MaLop for new inserts
    }
}