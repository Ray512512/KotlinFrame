package com.moodsmap.waterlogging.presentation.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.moodsmap.waterlogging.R;
import com.moodsmap.waterlogging.ui.dispatch.DispatchActivity;


/**
 * Created by Ray on 2018/1/9.
 */

public class StatusBarUtils {

    /**
     * 需要过滤不公共处理的类
     */
    private static final String [] filters=new String[]{
            DispatchActivity.class.getCanonicalName(),
    };

    private static boolean isFiter(String tag){
        boolean b=false;
        for (String s:filters){
            if(s.equals(tag)){
                b=true;
                break;
            }
        }
        return b;
    }

    public static void setStatusBarBg(Activity context){
        if(isFiter(context.getClass().getCanonicalName()))return;
        new PseudoImmersiveModeManager(context).makeStatusBarImmersive();
    }

    public static void setStatusBarView(Context context,View top_view){
        new PseudoImmersiveModeManager((Activity) context).makeStatusBarImmersive(top_view);
    }

    public static int statusBarH=0;
    public static int getStatusBarHeight(Context activity){
        if(statusBarH!=0)return statusBarH;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusBarH = activity.getResources().getDimensionPixelSize(resourceId);
        return statusBarH;
    }

    /**
     * 获取导航栏高度
     * @param context
     * @return
     */
    public static int naviBarH=0;
    public static int getNavBarHeight(Context context) {
        if(naviBarH!=0)return naviBarH;
        int result = 0;
        int resourceId=0;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid!=0){
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            naviBarH=context.getResources().getDimensionPixelSize(resourceId);
            return naviBarH;
        }else
            return 0;
    }

    public static void transparentStatusBar(Activity activity) {
        Window window=activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); // 新增滑动返回，舍弃过渡动效
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.BLACK);
        }
    }

    public static void setStatusColor(Activity activity,int color){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window=activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(ContextCompat.getColor(activity,color));
            window.setNavigationBarColor(Color.BLACK);
            View decor = window.getDecorView();
            int ui = decor.getSystemUiVisibility();
            ui |=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decor.setSystemUiVisibility(ui);
        }
    }
}
