package com.example.lab_13;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView selection;
    AutoCompleteTextView SingleComplete;
    MultiAutoCompleteTextView multiComplete;
    String arr[] = {"Hà Nội", "Huế", "Sài Gòn", "Hà Giang", "Hội An", "Kiên Giang", "Lâm Đồng", "Long Khánh"};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            selection = findViewById(R.id.selection);
            SingleComplete = (AutoCompleteTextView) findViewById(R.id.editauto);
            ArrayAdapter myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
            SingleComplete.setAdapter(myAdapter);

            multiComplete = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
            multiComplete.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr));
            multiComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            SingleComplete.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    selection.setText(SingleComplete.getText());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            return insets;
        });
    }
}