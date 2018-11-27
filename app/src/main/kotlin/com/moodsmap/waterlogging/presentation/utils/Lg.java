package com.moodsmap.waterlogging.presentation.utils;

import android.util.Log;

import com.moodsmap.waterlogging.BuildConfig;

/**
 * Created by caism on 2017/4/13.
 */

public class Lg {
    private Lg(){
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    public static boolean isDebug = BuildConfig.IS_DEBUG;//是否需要打印bug，可以在applation的oncreate里初始化
    private static final String TAG = "";
    public static void i(String msg){
        if (isDebug){
            Log.i(TAG,msg);
        }
    }
    public static void d(String msg){
        if (isDebug){
            Log.d(TAG,msg);
        }
    }
    public static void e(String msg){
        if (isDebug){
            Log.e(TAG,msg);
        }
    }
    public static void v(String msg){
        if (isDebug){
            Log.v(TAG,msg);
        }
    }
    public static void i(String tag,String msg){
        if (isDebug){
            Log.i(tag,msg);
        }
    }
    public static void d(String tag,String msg){
        if (isDebug){
            Log.d(tag,msg);
        }
    }
    public static void d(String tag, String msg, Throwable tr) {
        if (isDebug){
            Log.d( tag, msg, tr);
        }
    }

    public static void e(String tag,String msg){
        if (isDebug){
            Log.e(tag,msg);
        }
    }
    public static void w(String tag,String msg){
        if (isDebug){
            Log.w(tag,msg);
        }
    }
    public static void v(String tag,String msg){
        if (isDebug){
            Log.v(tag,msg);
        }
    }

    public static void f(String name,String msg){
        if (isDebug){
//            FileUtils.writeTxtToFile(msg,FileUtils.path,nickName);
        }
    }
}
