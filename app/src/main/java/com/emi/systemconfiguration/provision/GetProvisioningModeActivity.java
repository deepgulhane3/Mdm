package com.emi.systemconfiguration.provision;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class GetProvisioningModeActivity extends Activity {

    // DO NOT use framework constants (API 29+)
    private static final String EXTRA_PROVISIONING_MODE =
            "android.app.extra.PROVISIONING_MODE";

    // Stable value defined by AOSP
    private static final int PROVISIONING_MODE_DEVICE_OWNER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("DPC", "GetProvisioningModeActivity called");

        Intent result = new Intent();
        result.putExtra(EXTRA_PROVISIONING_MODE,
                PROVISIONING_MODE_DEVICE_OWNER);

        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
