package com.emi.systemconfiguration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DPC-Main";

    private DevicePolicyManager mDPM;
    private ComponentName mDeviceAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mDeviceAdmin = new ComponentName(this, DeviceAdmin.class);

        // Optional: detect first launch after provisioning
        if (getIntent() != null
                && "android.app.action.PROVISIONING_SUCCESSFUL".equals(getIntent().getAction())) {
            Log.i(TAG, "Launched after provisioning");
        }

        // ✅ SAFE Device Owner check
        if (isDeviceOwnerSafe()) {
            Log.i(TAG, "Device Owner confirmed");
            applyRestrictionsSafe();
        } else {
            Log.w(TAG, "Not device owner");
        }
    }

    /**
     * Safe wrapper for isDeviceOwnerApp()
     */
    private boolean isDeviceOwnerSafe() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return false;
        }
        return mDPM != null && mDPM.isDeviceOwnerApp(getPackageName());
    }

    /**
     * Apply restrictions ONLY when device owner is confirmed
     */
    private void applyRestrictionsSafe() {
        if (!isDeviceOwnerSafe()) {
            Log.w(TAG, "applyRestrictions called without device owner");
            return;
        }

        try {
            mDPM.addUserRestriction(mDeviceAdmin, UserManager.DISALLOW_USB_FILE_TRANSFER);
            mDPM.addUserRestriction(mDeviceAdmin, UserManager.DISALLOW_SAFE_BOOT);
            mDPM.setUninstallBlocked(mDeviceAdmin, getPackageName(), true);

            Log.i(TAG, "Restrictions applied successfully");
        } catch (SecurityException se) {
            Log.e(TAG, "SecurityException applying restrictions", se);
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error applying restrictions", e);
        }
    }

    /**
     * Optional deregistration (debug only)
     */
    public void deRegisterDevice(View view) {
        try {
            if (!isDeviceOwnerSafe()) {
                Toast.makeText(this, "Not Device Owner", Toast.LENGTH_SHORT).show();
                return;
            }

            mDPM.clearUserRestriction(mDeviceAdmin, UserManager.DISALLOW_USB_FILE_TRANSFER);
            mDPM.clearUserRestriction(mDeviceAdmin, UserManager.DISALLOW_SAFE_BOOT);
            mDPM.setUninstallBlocked(mDeviceAdmin, getPackageName(), false);

            Toast.makeText(this, "Restrictions cleared", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Error during deRegister", e);
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
