package com.algo.transact.notificationservice;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * This Service class inovkes to generate the FCM Token. Whenever the FCM token will change
 * onTokenRefresh method will invoke by the service. So here we can get the updated FCM Token.
 */

public class NotificationTokenService extends FirebaseInstanceIdService {
    private static final String TAG = NotificationTokenService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
}
