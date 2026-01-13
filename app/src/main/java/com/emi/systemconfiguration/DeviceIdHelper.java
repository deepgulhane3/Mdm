package com.emi.systemconfiguration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;

import java.util.UUID;

public class DeviceIdHelper {
    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
        // Primary: Android ID (unique per device + app signing key)
        String androidId = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );

        if (androidId != null && !androidId.equals("9774d56d682e549c")) {
            return androidId;
        }

        // Fallback: Generate/store UUID
        SharedPreferences prefs = context.getSharedPreferences("device_id_prefs", Context.MODE_PRIVATE);
        String uuid = prefs.getString("device_uuid", null);
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
            prefs.edit().putString("device_uuid", uuid).apply();
        }
        return uuid;
    }
}
