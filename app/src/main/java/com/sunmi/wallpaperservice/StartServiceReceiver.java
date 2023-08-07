package com.sunmi.wallpaperservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author sm2886
 */
public class StartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, WallpaperService.class);
        context.startService(serviceIntent);
        Log.e("tian you", "onReceive: tian you ");
    }
}