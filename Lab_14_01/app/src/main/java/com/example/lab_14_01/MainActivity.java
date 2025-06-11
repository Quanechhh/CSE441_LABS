package com.example.lab_14_01;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends TabActivity {

    // Tab 1 elements
    private EditText editTextA;
    private EditText editTextB;
    private Button buttonAdd;
    private TextView textViewResult;

    // Tab 2 elements
    private ListView historyListView;
    private TextView noHistoryTextView;
    private ArrayList<String> historyList;
    private ArrayAdapter<String> historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        // Tab 1: Phep Cong
        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab_PhepCong");
        spec1.setIndicator("Phép cộng");
        spec1.setContent(R.id.tab1);
        tabHost.addTab(spec1);

        // Tab 2: Lich Su
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab_LichSu");
        spec2.setIndicator("Lịch sử");
        spec2.setContent(R.id.tab2);
        tabHost.addTab(spec2);

        tabHost.setCurrentTab(0);

        // --- Initialize Tab 1 UI elements ---
        // We need to find views within the included layout.
        // The TabHost provides access to the content view for each tab.
        // However, a simpler way when using <include> is to find them from the main activity's content view.
        // If findViewById(R.id.editTextA) doesn't work directly, you might need to
        // inflate the layouts and get references from the inflated views.
        // For <include>, as long as IDs are unique, findViewById generally works.

        editTextA = findViewById(R.id.editTextA);
        editTextB = findViewById(R.id.editTextB);
        buttonAdd = findViewById(R.id.buttonAdd);
        textViewResult = findViewById(R.id.textViewResult);

        // --- Initialize Tab 2 UI elements ---
        historyListView = findViewById(R.id.historyListView);
        noHistoryTextView = findViewById(R.id.noHistoryTextView);
        historyList = new ArrayList<>();
        historyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        historyListView.setAdapter(historyAdapter);

        // --- Button Click Listener for Addition ---
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAddition();
            }
        });

        // --- Tab Change Listener to update history (optional, but good for real-time update) ---
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if ("Tab_LichSu".equals(tabId)) {
                    updateHistoryDisplay();
                }
            }
        });
        updateHistoryDisplay(); // Initial update
    }

    private void performAddition() {
        String inputA = editTextA.getText().toString().trim();
        String inputB = editTextB.getText().toString().trim();

        if (inputA.isEmpty() || inputB.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ cả hai số.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double numA = Double.parseDouble(inputA);
            double numB = Double.parseDouble(inputB);
            double sum = numA + numB;

            String resultText = String.format("%.0f + %.0f = %.0f", numA, numB, sum); // Format as integers
            textViewResult.setText(resultText);
            textViewResult.setVisibility(View.VISIBLE);

            // Add to history
            historyList.add(resultText);
            historyAdapter.notifyDataSetChanged(); // Notify adapter that data has changed

            // Clear input fields
            editTextA.setText("");
            editTextB.setText("");

            Toast.makeText(this, "Tính toán thành công!", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập số hợp lệ.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateHistoryDisplay() {
        if (historyList.isEmpty()) {
            noHistoryTextView.setVisibility(View.VISIBLE);
            historyListView.setVisibility(View.GONE);
        } else {
            noHistoryTextView.setVisibility(View.GONE);
            historyListView.setVisibility(View.VISIBLE);
        }
        historyAdapter.notifyDataSetChanged(); // Ensure history is up-to-date
    }
}