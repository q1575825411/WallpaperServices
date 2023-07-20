package com.sunmi.wallpaperservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author sm2886
 */
public class StartServiceReceiver extends BroadcastReceiver {
    private static final String TAG = "StartServiceReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, WallpaperService.class);
        context.startService(serviceIntent);
    }
}