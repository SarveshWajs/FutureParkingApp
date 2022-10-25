package com.example.testing.notifications;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FCMSender {
    /*
     * URL where we request to send notification and the key to send notification using admin sdk
     * */
    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send", KEY_STRING = "key=AAAA_Zez6Vk:APA91bEFU6bpDd1jWUkgNVO5icOoXzeJ2c-599RubQ5KMvh6TpJsBf9N84uRtSkmwHb5P0I98gOGuQ3ybhDKBXJcfXGvfm6kpvWS3JcGKJGE09Cul5hxrvCQ4VDScVnsQvMFOkDicsCV";

    OkHttpClient client = new OkHttpClient();

    /*
     * Method to send notification to the application
     * */
    public void send(String message, Callback callback) {
        RequestBody reqBody = RequestBody.create(message
                , MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(FCM_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", KEY_STRING)
                .post(reqBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}

