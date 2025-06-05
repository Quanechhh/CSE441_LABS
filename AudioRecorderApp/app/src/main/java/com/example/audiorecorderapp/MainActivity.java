package com.example.audiorecorderapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String outputFile;
    private TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Áp dụng padding hệ thống
        txtStatus = findViewById(R.id.txtStatus);
        View rootView = findViewById(R.id.main);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (view, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        } else {
            txtStatus.setText("Không tìm thấy View root layout (ID: main)");
        }


        // Ánh xạ View

        Button btnStart = findViewById(R.id.btnStart);
        Button btnStop = findViewById(R.id.btnStop);
        Button btnPlay = findViewById(R.id.btnPlay);

        // Yêu cầu quyền
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        // Đường dẫn lưu file
        outputFile = getExternalFilesDir(Environment.DIRECTORY_MUSIC) + "/recording.mp4";

        // Bắt sự kiện
        btnStart.setOnClickListener(v -> startRecording());
        btnStop.setOnClickListener(v -> stopRecording());
        btnPlay.setOnClickListener(v -> playRecording());
    }

    private void startRecording() {
        releaseMediaPlayer();  // Đảm bảo không phát khi ghi

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setOutputFile(outputFile);
            mediaRecorder.prepare();
            mediaRecorder.start();
            txtStatus.setText("Đang ghi âm...");
        } catch (IOException e) {
            e.printStackTrace();
            txtStatus.setText("Lỗi ghi âm");
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
            } catch (RuntimeException e) {
                e.printStackTrace();
                txtStatus.setText("Ghi âm bị lỗi hoặc quá ngắn");
            }
            mediaRecorder.release();
            mediaRecorder = null;
            txtStatus.setText("Đã dừng ghi âm");
        }
    }

    private void playRecording() {
        releaseMediaPlayer();  // Tránh bị đè player

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(outputFile);
            mediaPlayer.prepare();
            mediaPlayer.start();
            txtStatus.setText("Đang phát lại...");

            mediaPlayer.setOnCompletionListener(mp -> {
                txtStatus.setText("Đã phát xong");
                releaseMediaPlayer();
            });

        } catch (IOException e) {
            e.printStackTrace();
            txtStatus.setText("Lỗi phát lại");
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
        releaseMediaPlayer();
        super.onDestroy();
    }
}
