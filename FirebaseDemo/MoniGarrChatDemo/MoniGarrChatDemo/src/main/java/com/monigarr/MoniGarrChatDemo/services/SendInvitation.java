/*
 * Copyright (c) 2016.
 */

package com.monigarr.MoniGarrChatDemo.services;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.monigarr.MoniGarrChatDemo.R;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by monicapeters on 8/31/16.
 */

public class SendInvitation  extends Activity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private static final int REQUEST_INVITE = 1;
    Activity activity;

    SendInvitation(Activity activity) {
        this.activity = activity;
    }

    public void inviteFriend() {
        Intent intent = new AppInviteInvitation.IntentBuilder(Resources.getSystem().getString(R.string.invitation_title))
                .setMessage(Resources.getSystem().getString(R.string.invitation_message))
                .setCallToActionText(Resources.getSystem().getString(R.string.invitation_cta))
                .build();
        activity.startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Use Firebase Measurement to log invitation was sent.
                Bundle payload = new Bundle();
                payload.putString(FirebaseAnalytics.Param.VALUE, "inv_sent");

                // Check how many invitations were sent and log.
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                Log.d(TAG, "Invitations sent: " + ids.length);
            } else {
                // Use Firebase Measurement to log invitation was not sent
                Bundle payload = new Bundle();
                payload.putString(FirebaseAnalytics.Param.VALUE, "inv_not_sent");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, payload);

                // Sending failed or it was canceled, show failure message to the user
                Log.d(TAG, "Failed to send invitation.");
            }
        }
    }

}
