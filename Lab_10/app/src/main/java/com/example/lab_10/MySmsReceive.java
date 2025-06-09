package com.example.lab_10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MySmsReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        processReceive(context, intent);
    }

    public void processReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String message = "";
        String body = "";
        String address = "";

        if (extras != null) {
            Object[] smsExtra = (Object[]) extras.get("pdus");

            for (Object obj : smsExtra) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                body = sms.getMessageBody();
                address = sms.getOriginatingAddress();

                message = "Có 1 tin nhắn từ " + address + "\n" + body;

                // ⚠️ Chỉ lọc những tin nhắn đến từ số cụ thể (ví dụ: 0123456789)
                if (address.equals("+84123456789")) {
                    // Gửi tin nhắn này đến số khác (ví dụ: 0987654321)
                    sendSMS(context, "0987654321", body);
                }
            }

            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    // Gửi tin nhắn chuyển tiếp
    private void sendSMS(Context context, String phoneNumber, String message) {
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
