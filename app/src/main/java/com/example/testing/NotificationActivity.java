package com.example.testing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

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
    NotificationActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=NotificationActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
        setUpButtons();



    }

    private void setUpButtons() {

        binding.sendNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.notifMessage.getText().toString().isEmpty()&&(!binding.notifNumber.getText().toString().isEmpty())){
                    new FCMSender().send(String.format(NotificationMessage.message,"messaging", binding.notifMessage.getText().toString(), binding.notifNumber.getText().toString()), new Callback() {
                        @Override
                        public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                            NotificationActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(response.code()==200){
                                        Toast.makeText(NotificationActivity.this, "Notification sent", Toast.LENGTH_SHORT).show();
                                        hideKeyboard(NotificationActivity.this);
                                    }
                                }
                            });
                        }

                        @Override
                        public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {

                        }

                    });
                }else{
                    if (binding.notifNumber.getText().toString().isEmpty()){
                        binding.notifNumber.setError("Please enter the mobile number");
                    }if (binding.notifMessage.getText().toString().isEmpty()){
                        binding.notifMessage.setError("Please enter the message you want to send");
                    }
                }
            }
        });

    }
    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        String packageName = "com.android.chrome";
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName);

            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabsIntent.launchUrl(activity, uri);
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}







