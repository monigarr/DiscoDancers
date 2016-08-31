/*
 * Copyright (c) 2016.
 */

package com.monigarr.MoniGarrChatDemo.services;

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by monicapeters on 8/31/16.
 */

public class CauseCrash {
    //Create Crash on Purpose so we can review in the
    //Firebase Logs and Analytics

    //Crash on Purpose
    public void crashTestMe() {
        FirebaseCrash.logcat(Log.ERROR, TAG, "crash caused");
        throw new NullPointerException("Fake null pointer exception");
    }

}
