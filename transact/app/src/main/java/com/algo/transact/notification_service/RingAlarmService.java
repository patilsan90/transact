package com.algo.transact.notification_service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

import com.algo.transact.R;

public class RingAlarmService extends Service {
    static final String STOP_FOREGROUND_ACTION = "stopforegroundAction";
    static final String START_FOREGROUND_ACTION = "startforegroundAction";
    private String message;
    private MediaPlayer mMediaPlayer;
    private Vibrator vibrator;


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        message = intent.getStringExtra("TAG");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        message = intent.getStringExtra("TAG");
        if (intent.getAction().equals(STOP_FOREGROUND_ACTION)) {
            stopAlarmSound();
            stopForeground(true);
            stopSelf();
        } else if (intent.getAction().equals(START_FOREGROUND_ACTION)) {
            startForeGround();
            startVibrator();
            playAlarmSound();
        }
        return START_NOT_STICKY;
    }

    private void stopAlarmSound() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            vibrator.cancel();
        }
    }

    private void playAlarmSound() {
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer = MediaPlayer.create(this, R.raw.schoolalarm);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void startForeGround() {
        Intent intent = new Intent(this, RingAlarmService.class);
        intent.setAction(START_FOREGROUND_ACTION);
        Intent intent1 = new Intent(this, RingAlarmService.class);
        intent1.setAction(STOP_FOREGROUND_ACTION);
        PendingIntent stopAlarm = PendingIntent.getService(this, (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(stopAlarm)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_launcher_foreground, "Stop Alarm", stopAlarm)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(10, notification);
        startForeground(10, notification);
    }

    private void startVibrator() {
        int dot = 200;
        int dash = 500;
        int gap = 200;
        long[] pattern = {
                0,
                dot, gap, dash, gap, dot, gap, dot
        };
        vibrator = (Vibrator) getApplicationContext().getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, 0);
    }
}
