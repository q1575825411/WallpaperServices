package com.sunmi.wallpaperservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;

/**
 * @author sm2886
 */
public class WallpaperService extends Service {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "com.example.wallpaper.notification";
    private final String CONTENT_URL = "content://settings/secure/ui_night_mode";
    private final String key = "persist.sunmi.wallpaper.path";
    private ContentObserver contentObserver;


    public WallpaperService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Tian you", "服务启动：");
        startForegroundWithNotification();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 在需要的时候清除通知
        notificationManager.cancel(NOTIFICATION_ID);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        contentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                if (uri != null && uri.toString().equals(CONTENT_URL) && !selfChange) {
                    Log.e("tian you", "onChange: ");
                    String currentPath = "/system_ext/media/wallerpaper/dark/15system_silver_5.jpg";
                    Bitmap bitmap = BitmapFactory.decodeFile(currentPath);
                    try {
                        wallpaperManager.setBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        };
        getApplicationContext().getContentResolver().registerContentObserver(
                Settings.Secure.getUriFor("ui_night_mode"),
                true,
                contentObserver
        );

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(STOP_FOREGROUND_DETACH);
        Log.e("Tian you", "服务销毁：");
        if (contentObserver != null) {
            getApplicationContext().getContentResolver().unregisterContentObserver(contentObserver);
            contentObserver = null;
        }
        super.onDestroy();
    }

    private void startForegroundWithNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID,
                    NotificationManager.IMPORTANCE_MIN);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setShowWhen(true);
        Notification notification = builder.build();
        startForeground(NOTIFICATION_ID, notification);
    }
}
