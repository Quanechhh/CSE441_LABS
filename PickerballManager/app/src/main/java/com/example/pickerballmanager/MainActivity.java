package com.example.pickerballmanager;

import static com.example.pickerballmanager.R.id.btnAdd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference ref;
    List<Player> players;
    PlayerAdapter adapter;
    RecyclerView recyclerView;
    Button btnAdd;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ref = FirebaseDatabase.getInstance().getReference("players");
        players = new ArrayList<>();
        adapter = new PlayerAdapter(this, players);
        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                players.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Player p = snap.getValue(Player.class);
                    players.add(p);
                }
                adapter.notifyDataSetChanged();
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Lỗi đọc dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });

        btnAdd.setOnClickListener(v -> showAddDialog());
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm hội viên");

        // Tạo layout để nhập thông tin
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText usernameInput = new EditText(this);
        usernameInput.setHint("Tên hội viên");
        layout.addView(usernameInput);

        final EditText birthdayInput = new EditText(this);
        birthdayInput.setHint("Ngày sinh (dd/MM/yyyy)");
        layout.addView(birthdayInput);

        final EditText hometownInput = new EditText(this);
        hometownInput.setHint("Quê quán");
        layout.addView(hometownInput);

        final EditText residenceInput = new EditText(this);
        residenceInput.setHint("Nơi ở hiện tại");
        layout.addView(residenceInput);

        builder.setView(layout);

        builder.setPositiveButton("Lưu", null); // Set null trước để override sau

        builder.setNegativeButton("Hủy", null);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                String username = usernameInput.getText().toString().trim();
                String birthday = birthdayInput.getText().toString().trim();
                String hometown = hometownInput.getText().toString().trim();
                String residence = residenceInput.getText().toString().trim();

                if (!username.isEmpty() && !birthday.isEmpty() && !hometown.isEmpty() && !residence.isEmpty()) {
                    String id = ref.push().getKey(); // tạo mã hội viên tự sinh
                    String avatar = ""; // để trống hoặc sau này cập nhật ảnh
                    double ratingSingle = 1000.0;
                    double ratingDouble = 1000.0;

                    Player newPlayer = new Player(id, username, avatar, birthday, hometown, residence, ratingSingle, ratingDouble);
                    ref.child(id).setValue(newPlayer);

                    // Ẩn bàn phím
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(usernameInput.getWindowToken(), 0);
                    }

                    Toast.makeText(this, "Đã thêm hội viên", Toast.LENGTH_SHORT).show();
                    dialog.dismiss(); // chỉ dismiss khi thành công
                } else {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }


}
