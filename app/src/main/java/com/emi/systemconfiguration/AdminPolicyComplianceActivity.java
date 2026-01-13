package com.emi.systemconfiguration;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AdminPolicyComplianceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Validate intent exists
        if (getIntent() == null) {
            setResult(Activity.RESULT_CANCELED);
            finish();
            return;
        }

        // Signal compliance
        setResult(Activity.RESULT_OK);
        finish();
    }
}
