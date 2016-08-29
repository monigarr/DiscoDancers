/*
 * Copyright (c) 2016.
 */

package com.monigarr.MoniGarrChatDemo.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by monicapeters on 8/27/16.
 */

public class DemoFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "DemoFirebaseIIDService";
    private static final String FRIEND_ENGAGE_TOPIC = "friend_engage";

    /**
     * current Instance ID token is no longer valid. A new one must be requested.
     */
    @Override
    public void onTokenRefresh() {
        // handle generation of token, initially or after a refresh
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Demo Token: " + token);

        // Once a token is generated, subscribe to topic.
        FirebaseMessaging.getInstance().subscribeToTopic(FRIEND_ENGAGE_TOPIC);
    }
}