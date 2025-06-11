package com.example.lab_16;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtA, edtB;
    private TextView txtKq, txtlichsu;
    private Button btnTong, btnClear;

    // Constants for Shared Preferences
    private static final String PREFS_NAME = "CalculationHistoryPrefs";
    private static final String HISTORY_KEY = "history";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        txtKq = findViewById(R.id.txtKq);
        txtlichsu = findViewById(R.id.txtlichsu);
        btnTong = findViewById(R.id.btnTong);
        btnClear = findViewById(R.id.btnClear);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Load history when activity is created
        loadHistory();

        // Set OnClickListener for "TỔNG" button
        btnTong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSum();
            }
        });

        // Set OnClickListener for "CLEAR" button
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistory();
            }
        });
    }

    private void performSum() {
        String inputA = edtA.getText().toString().trim();
        String inputB = edtB.getText().toString().trim();

        if (inputA.isEmpty() || inputB.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ cả hai số.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double numA = Double.parseDouble(inputA);
            double numB = Double.parseDouble(inputB);
            double sum = numA + numB;

            // Display result
            txtKq.setText(String.valueOf(sum));

            // Format history entry (e.g., "7 + 8 = 15")
            String historyEntry = String.format("%s + %s = %.0f", inputA, inputB, sum); // %.0f for integer-like display

            // Save to history
            saveHistory(historyEntry);

            // Clear input fields
            edtA.setText("");
            edtB.setText("");

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập số hợp lệ.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveHistory(String newEntry) {
        // Get current history
        String currentHistory = sharedPreferences.getString(HISTORY_KEY, "");

        // Append new entry (add newline for separation)
        String updatedHistory;
        if (currentHistory.isEmpty()) {
            updatedHistory = newEntry;
        } else {
            updatedHistory = currentHistory + "\n" + newEntry;
        }

        // Save updated history to Shared Preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(HISTORY_KEY, updatedHistory);
        editor.apply(); // Use apply() for asynchronous save

        // Update TextView
        txtlichsu.setText(updatedHistory);
    }

    private void loadHistory() {
        String savedHistory = sharedPreferences.getString(HISTORY_KEY, "");
        txtlichsu.setText(savedHistory);
    }

    private void clearHistory() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(HISTORY_KEY); // Remove the history key
        editor.apply();

        txtlichsu.setText(""); // Clear TextView
        Toast.makeText(this, "Lịch sử đã được xóa.", Toast.LENGTH_SHORT).show();
    }
}