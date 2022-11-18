package com.example.testing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.view.View;

import com.example.testing.databinding.NotificationActivityBinding;
import com.example.testing.notifications.FCMSender;
import com.example.testing.notifications.NotificationMessage;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import okhttp3.Callback;
import okhttp3.Response;
public class NotificationActivity extends AppCompatActivity {

    EditText notifMessage, notifNumber;
    Button send_notif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        notifMessage = findViewById(R.id.notifMessage);
        notifNumber = findViewById(R.id.notifNumber);
        send_notif = findViewById(R.id.send_notif);

        send_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(NotificationActivity.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED) {
                    sendMessage();
                }else{
                    ActivityCompat.requestPermissions(NotificationActivity.this,new String[]{Manifest.permission.SEND_SMS},100);
                }
            }
        });
}

    private void sendMessage() {
        String sPhone = notifNumber.getText().toString().trim();
        String sMessage = notifMessage.getText().toString().trim();

        if(!sPhone.equals("") && !sMessage.equals("")) {

            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(sPhone, null, sMessage, null, null);

            Toast.makeText(getApplicationContext()
                    , "SMS sent successfully!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext()
                    , "Enter value first.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode ==100 && grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            sendMessage();
        }else{
            Toast.makeText(getApplicationContext()
                    , "Permission Denied!", Toast.LENGTH_SHORT).show();
        }
    }
}







