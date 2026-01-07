package com.emi.systemconfiguration;

import android.app.PendingIntent;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;



public class UninstallService extends Service {
    DevicePolicyManager dpm;


    @Override
    public void onCreate() {
        super.onCreate();
        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        checkUninstallStatus();

        return START_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void checkUninstallStatus(){
        try {
            String deviceId = MainActivity.getDeviceId(getApplicationContext());
            Log.d("deviceUid", deviceId);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void uninstallApk() {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
            devicePolicyManager.clearDeviceOwnerApp(this.getPackageName());
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:"+getPackageName()));
        startActivity(intent);

        Log.d("StatusUninsatall", "DIsable ADmin App" );
    }
}
