package com.algo.transact.notification_service;

import android.util.Log;

import com.algo.transact.AppConfig.AppConfig;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * If the Application is in fore ground, then will recieve the notification in onMessageRecieved.
 * Here we can check the type of notification by using appropriate methods.
 */

public class OnReceiveNotificationService extends FirebaseMessagingService {
    private static final String TAG = AppConfig.TAG;//OnReceiveNotificationService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From : " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload : " + remoteMessage.getData());

        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body : " + remoteMessage.getNotification().getBody());
        }
    }
}
