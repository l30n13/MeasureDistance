package com.l30n13.measuredistance;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;

/**
 * Created by tonmoy on 4/19/17.
 */

public class AccessPermission {
    private Context context;
    private Activity activity;

    public AccessPermission(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(activity, permissions, 404);
    }

    public static void accessPermission(Activity activity) {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(activity, permissions, 404);
    }
}
