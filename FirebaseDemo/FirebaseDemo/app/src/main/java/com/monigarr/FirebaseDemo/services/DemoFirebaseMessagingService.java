/*
 * Copyright (c) 2016.
 */

package com.monigarr.FirebaseDemo.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by monicapeters on 8/27/16.
 */

public class DemoFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "DemoService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "Demo Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "Demo Notification Message: " + remoteMessage.getNotification());
        Log.d(TAG, "Demo Data Message: " + remoteMessage.getData());
    }
}
