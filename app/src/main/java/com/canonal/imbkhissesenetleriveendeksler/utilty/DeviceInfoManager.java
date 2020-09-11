package com.canonal.imbkhissesenetleriveendeksler.utilty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.canonal.imbkhissesenetleriveendeksler.R;
import com.canonal.imbkhissesenetleriveendeksler.model.handshake.DeviceInfo;

public class DeviceInfoManager {

    @SuppressLint("HardwareIds")
    private static String getDeviceId(Context context) {
        TelephonyManager telephonyManager;

        telephonyManager = (TelephonyManager) context.getSystemService(Context.
                TELEPHONY_SERVICE);

        return telephonyManager.getDeviceId();
    }

    private static String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    private static String getDeviceManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    private static String getDeviceModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static DeviceInfo getDeviceInfo(DeviceInfo deviceInfo, Context context) {
        deviceInfo = new DeviceInfo();

        deviceInfo.setDeviceId(DeviceInfoManager.getDeviceId(context));
        deviceInfo.setSystemVersion(DeviceInfoManager.getAndroidVersion());
        deviceInfo.setPlatformName(context.getString(R.string.platform_name));
        deviceInfo.setDeviceModel(DeviceInfoManager.getDeviceModel());
        deviceInfo.setManifacturer(DeviceInfoManager.getDeviceManufacturer());

        Log.d("DEVICE ID", deviceInfo.getDeviceId());
        Log.d("DEVICE SYSTEM VERSION", deviceInfo.getSystemVersion());
        Log.d("DEVICE PLATFORM NAME", deviceInfo.getPlatformName());
        Log.d("DEVICE MODEL", deviceInfo.getDeviceModel());
        Log.d("DEVICE MANIFACTURER", deviceInfo.getManifacturer());
        return deviceInfo;
    }
}
