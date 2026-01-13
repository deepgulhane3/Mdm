package com.emi.systemconfiguration;

import android.app.admin.DeviceAdminReceiver;

import android.content.ComponentName;
import android.content.Context;

import android.content.Intent;
import android.util.Log;

public class DeviceAdmin extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.i("DeviceAdmin", "Administrator enabled");
        // DO NOT apply restrictions here
    }


    @Override
    public void onProfileProvisioningComplete(Context context, Intent intent) {
        Log.i("DeviceAdmin", "Profile provisioning complete");
        // DO NOTHING ELSE HERE
    }


    public static ComponentName getComponentName(Context context) {
        return new ComponentName(context, DeviceAdmin.class);
    }

}
