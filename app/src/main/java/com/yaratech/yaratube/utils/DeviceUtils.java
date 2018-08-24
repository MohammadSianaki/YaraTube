package com.yaratech.yaratube.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

public class DeviceUtils {

    private DeviceUtils() {
        //no instance
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceOS() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getDeviceModel() {
        return Build.MODEL;
    }
}
