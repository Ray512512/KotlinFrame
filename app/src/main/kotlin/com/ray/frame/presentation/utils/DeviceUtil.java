package com.ray.frame.presentation.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 常规工具类
 * Created by 魏兴 on 2017/8/3.
 */

public class DeviceUtil {

    /**
     * 获取本地Apk版本号
     *
     * @param context 上下文
     * @return int
     */
    public static String getVersionCode(Context context) {
        String verCode = "-1";
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            Lg.e("Apk版本号：" + verCode);
            context = null;
        } catch (PackageManager.NameNotFoundException e) {
            Lg.e("AppApplicationMgr-->>getVerCode()", e.getMessage() + "获取本地Apk版本号失败！");
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取手机型号
     */
    public static String getPhoneModel() {
        Lg.e("手机型号：" + Build.MODEL);
        return Build.MODEL;
    }

    /**
     * 获得客户端操作系统名称
     *
     * @return
     */
    public static String getSysClientOs() {
        String OsName = Build.ID;
        Lg.e("操作系统名称" + OsName);
        return OsName;
    }

    /**
     * 获取操作系统的版本号
     *
     * @return String 系统版本号
     */
    public static String getSysRelease() {
        String release = Build.VERSION.RELEASE;
        Lg.e("操作系统的版本号" + release);
        return release;
    }

    /**
     * 读唯一的设备ID(唯一的设备ID【GSM手机的IMEI】和【CDMA手机的 MEID】,如果获取不到返回一个默认字符串)
     *
     * @param content 上下文
     * @return String 获取设备序列号
     */
    public static String getSysTelephoneSerialNum(Context content) {
        TelephonyManager telephonyManager = (TelephonyManager) content.getSystemService(Context.TELEPHONY_SERVICE);
        content = null;
        @SuppressLint("MissingPermission") String deviceId = telephonyManager.getDeviceId();
        Lg.e("设备ID" + deviceId);
        return deviceId;
    }

    /**
     * 获取运营商信息(三大运营商)
     *
     * @param content 上下文
     * @return String 获取运营商名称
     */
    public static String getSysCarrier(Context content) {
        String moblieType = "0";
        TelephonyManager telephonyManager = (TelephonyManager) content.getSystemService(Context.TELEPHONY_SERVICE);
        content = null;
        @SuppressLint("MissingPermission") String imsi = telephonyManager.getSubscriberId();
        if (imsi != null && imsi.length() > 0) {
            //因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                //中国移动
                moblieType = "China Mobile";
            } else if (imsi.startsWith("46001")) {
                //中国联通
                moblieType = "China Unicom";
            } else if (imsi.startsWith("46003")) {
                //中国电信
                moblieType = "China Telecom";
            }
        }

        Lg.e("获取运营商信息(三大运营商)" + moblieType);
        return moblieType;
    }

    /**
     * 获取屏幕尺寸
     *
     * @param context
     * @return
     */
    public static String getSesolution(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        String result = width + "*" + height;
        Lg.e("获取屏幕尺寸：" + result);
        return result;
    }


    /**
     * 判断手机是否开启全面屏
     *
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean isAllScreenDevice(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
    }

}
