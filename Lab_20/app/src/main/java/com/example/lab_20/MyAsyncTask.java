package com.example.lab_20;

import android.app.Activity; //
import android.os.AsyncTask; //
import android.os.SystemClock; //
import android.widget.ProgressBar; //
import android.widget.TextView; //
import android.widget.Toast; //

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> { //
    Activity contextCha; //
    ProgressBar paCha; //
    TextView txtmsg; //

    // constructor này được truyền vào là MainActivity
    public MyAsyncTask(Activity ctx) { //
        contextCha = ctx; //
        // Ánh xạ ProgressBar và TextView từ layout của MainActivity
        paCha = contextCha.findViewById(R.id.progressBar1); //
        txtmsg = contextCha.findViewById(R.id.textView1); //
    }

    //hàm này sẽ được thực hiện đầu tiên
    @Override //
    protected void onPreExecute() { //
        super.onPreExecute(); //
        Toast.makeText(contextCha, "onPreExecute!", Toast.LENGTH_LONG).show(); //
        paCha.setProgress(0); // Đặt progress ban đầu về 0
        txtmsg.setText("0%"); // Đặt text ban đầu về "0%"
    }

    //tuyệt đối không được cập nhật giao diện trong hàm này
    @Override //
    protected Void doInBackground(Void... arg0) { //
        for (int i = 0; i <= 100; i++) { //
            //nghỉ 100 millisecond thì tiến hành update UI
            SystemClock.sleep(100); //
            //khi gọi hàm này thì onProgressUpdate sẽ thực thi
            publishProgress(i); //
        }
        return null; //
    }

    // ta cập nhập giao diện trong hàm này
    @Override //
    protected void onProgressUpdate(Integer... values) { //
        super.onProgressUpdate(values); //
        //thông qua contextCha để lấy được control trong MainActivity

        // vì publishProgress chỉ truyền 1 đối số
        int giatri = values[0]; //
        //cập nhập lại cho Progressbar
        paCha.setProgress(giatri); //
        //Đồng thời hiển thị giá trị lên TextView
        txtmsg.setText(giatri + "%"); //
    }

    // sau khi tiến trình thực hiện xong thì hàm này sảy ra
    @Override //
    protected void onPostExecute(Void result) { //
        super.onPostExecute(result); //
        Toast.makeText(contextCha, "Update xong rồi đó!", Toast.LENGTH_LONG).show(); //
    }
}