package com.example.networking_01;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    Button btnSend;
    EditText edtMessage;
    TextView txtChat;

    // Declare socket and out as member variables
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in; // Also declare BufferedReader as a member if needed for more complex scenarios,
    // though it's currently handled within the AsyncTask.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize UI elements AFTER setContentView
        btnSend = findViewById(R.id.btnSend);
        edtMessage = findViewById(R.id.edtMessage);
        txtChat = findViewById(R.id.txtChat);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            // Start the connection task when the activity is created
            new ConnectTask().execute();

            return insets;
        });
    }

    private class ConnectTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // Replace with the actual IP address of your server
                // If running on an emulator, "10.0.2.2" typically refers to your host machine's loopback interface
                // If running on a physical device, use the actual LAN IP of your server (e.g., "192.168.1.100")
                socket = new Socket("10.0.2.2", 5555); // Use 10.0.2.2 for emulator testing to connect to host machine
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Initialize member 'in'

                runOnUiThread(() -> txtChat.append("\nConnected to server!"));

                String message;
                while ((message = in.readLine()) != null) {
                    String finalMessage = message;
                    runOnUiThread(() -> txtChat.append("\nServer: " + finalMessage));
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> txtChat.append("\nError connecting to server: " + e.getMessage()));
            } finally {
                // Ensure resources are closed if connection loop exits unexpectedly
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    // This method is called when the "Gửi" button is clicked (due to android:onClick="sendMessage")
    public void sendMessage(View view) {
        String message = edtMessage.getText().toString();
        // Ensure message is not empty and the PrintWriter is initialized
        if (!message.isEmpty() && out != null) {
            // Network operations must be on a background thread
            new Thread(() -> {
                out.println(message); // Send message to server
                runOnUiThread(() -> {
                    txtChat.append("\nBạn: " + message); // Update UI with sent message
                    edtMessage.setText(""); // Clear the input field
                });
            }).start();
        } else if (out == null) {
            runOnUiThread(() -> txtChat.append("\nError: Not connected to server yet."));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close resources when the activity is destroyed
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}