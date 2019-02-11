package com.ray.frame.presentation.utils;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ray.frame.App;
import com.ray.frame.R;

/**
 * 要实现所谓沉浸式即透明状态栏的页面单独引入使用，不影响全局
 *
 * Created by mazj<743ren@gmail.com> on 2018/3/27.
 */

public class PseudoImmersiveModeManager {

    private int statusBarHeight;
    private Activity mActivity;
    private Window window;
    private View decorView;
    private View rootView;
    private View firstChildView;
    private int statusBarColorAfter23 = -1; // 6.0 后的背景颜色
    private boolean isLightStatusBarAfter23 = true; // 6.0 后背景是浅色，要将状态栏图标文字变成黑的
    private int statusBarColorBefore23 = ContextCompat.getColor(App.instance,R.color.mainColor); // [4.4-6.0) 的背景颜色
    private int firstChildPaddingTop = -1;
    private int rootPaddingTop = -1;

    private static final String TAG_KITKAT = "kitkat";
    private static final int MODE_COLOR = 1;
    private static final int MODE_IMAGE = 2;
    private int lastMode = 0; // 上一次的模式，是图片还是颜色
    private boolean autoFindBg = false; // 默认不许自动寻找 View 背景，减少逻辑
    private Drawable drawable = ContextCompat.getDrawable(App.instance, R.drawable.shape_gradient);

    public PseudoImmersiveModeManager(Activity activity) {
        this.mActivity = activity;
        window = this.mActivity.getWindow();
        decorView = window.getDecorView();
        rootView = ((ViewGroup)decorView.findViewById(android.R.id.content)).getChildAt(0);
        statusBarHeight = getStatusBarHeight(activity);
        if (decorView == null || rootView == null) {
            throw new RuntimeException("布局未加载完毕");
        }
        if (rootPaddingTop == -1) {
            rootPaddingTop = rootView.getPaddingTop();
        }
    }

    public void makeStatusBarImmersive(View view,boolean isRootView) {
        autoFindBg=false;
        makeStatusBarImmersive();
        setStatusBarWithViewBg(view, isRootView);
    }

    public void makeStatusBarImmersive(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            StatusBarUtils.transparentStatusBar(mActivity);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight));
        }
    }
        /**
         * 设置沉浸效果
         */
    public void makeStatusBarImmersive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4 以上
            // 全屏页面不要做
            if ( (window.getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
                return;
            }
            if (statusBarColorBefore23 != -1) { // 调用者决定了要设置颜色
                setStatusBarBgByColor();
            } else if (autoFindBg) { // 调用者未设置颜色且允许自动寻找
                if (!setStatusBarWithViewBg(rootView, true)) {
                    if (rootView instanceof ViewGroup) {
                        // 只找两层，不再递归
                        firstChildView = ((ViewGroup) rootView).getChildAt(0);
                        if (firstChildView instanceof ImageView) {
                            setStatusBarBgByImage(firstChildView, false);
                        } else {
                            setStatusBarWithViewBg(firstChildView, false);
                        }
                    }
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) { // 6.0 以上，图片和颜色都要设置
                // SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 在 API23 后有效
                // SYSTEM_UI_FLAG_LAYOUT_STABLE 防止系统栏隐藏时内容区域大小发生变化，它的图标颜色是白色的
                int flag = isLightStatusBarAfter23 ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(flag);
            }
        }
    }

    /**
     * 同一个页面，如果多次改变状态栏样式，需要重置一些
     */
    private void reset(int newMode) {
        if (lastMode == MODE_IMAGE) {
            if (firstChildPaddingTop >= 0 && firstChildView != null) {
                setPaddingTop(firstChildView, firstChildPaddingTop);
            }
            if (rootPaddingTop >= 0) {
                setPaddingTop(rootView, rootPaddingTop);
            }
            if (newMode == MODE_COLOR) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        } else if ((lastMode == MODE_COLOR) && (newMode == MODE_IMAGE)) {
           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0 以上
                window.setStatusBarColor(Color.TRANSPARENT);
            } else*/ if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4
                View view = decorView.findViewWithTag(TAG_KITKAT);
                if (view != null) {
                    ((ViewGroup) decorView).removeView(view);
                }
                if (rootPaddingTop >=0) {
                    setPaddingTop(rootView, rootPaddingTop);
                }
            }
        }

        lastMode = newMode;
    }

    private void resetColor() {
        statusBarColorBefore23 = statusBarColorAfter23 = -1; // 重置
    }

    /**
     * @param view
     * @param isRootView
     * @return 是否成功找到 View 的背景并设置状态栏
     */
    private boolean setStatusBarWithViewBg(View view, boolean isRootView) {
        Drawable drawable = view.getBackground();
//        Drawable drawable = ContextCompat.getDrawable(App.instance, R.drawable.shape_gradient);
        if (drawable != null) { // 设置了背景
            if (drawable instanceof ColorDrawable) {
                statusBarColorBefore23 = statusBarColorAfter23 = ((ColorDrawable) drawable).getColor();
                setStatusBarBgByColor();
            } else { // 背景是一张图
                setStatusBarBgByImage(view, isRootView);
            }
            return true;
        } else {
            return false;
        }
    }



    /**
     * 用颜色设置状态栏背景
     */
    private void setStatusBarBgByColor() {

        reset(MODE_COLOR);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) { // 6.0
            window.setStatusBarColor(statusBarColorAfter23);
        } else*/ /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0
            invasionStatusBar(mActivity);
        } else */if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = decorView.findViewWithTag(TAG_KITKAT);
            if (view != null) {
                view.setBackgroundColor(statusBarColorBefore23);
            } else {
                view = new View(mActivity);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
//                view.setBackgroundColor(statusBarColorBefore23);
                view.setBackground(drawable);
                view.setLayoutParams(params);
                view.setTag(TAG_KITKAT);
                ((ViewGroup)decorView).addView(view);
            }

            setPaddingTop(rootView, rootPaddingTop + statusBarHeight);
        }

        resetColor();
    }

    private void setStatusBarBgByImage(View view, boolean isRootView) {

        reset(MODE_IMAGE);

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 主要是为了多次设置不同样子的状态栏，padding 不能累积增加，所以记录初始值
        if (firstChildView != null && firstChildPaddingTop == -1) {
            firstChildPaddingTop = firstChildView.getPaddingTop();
        }

        setPaddingTop(view, (isRootView ? rootPaddingTop : firstChildPaddingTop) + statusBarHeight);
    }

    public PseudoImmersiveModeManager autoFindBg() {
        this.autoFindBg = true;
        return this;
    }

    /**
     * @param isLightStatusBarAfter23 Android 6.0 之后的状态栏背景是否浅色系
     * @return
     */
    public PseudoImmersiveModeManager setIsLightStatusBarAfter23(boolean isLightStatusBarAfter23) {
        this.isLightStatusBarAfter23 = isLightStatusBarAfter23;
        return this;
    }

    public PseudoImmersiveModeManager setStatusBarColor(int statusBarColor) {
        return setStatusBarColor(statusBarColor, statusBarColor);
    }

    /**
     * @param statusBarColorBefore23 Android 6.0 之前的状态栏颜色
     * @param statusBarColorAfter23 Android 6.0 之后的状态栏颜色
     * @return
     */
    public PseudoImmersiveModeManager setStatusBarColor(int statusBarColorBefore23, int statusBarColorAfter23) {
        this.statusBarColorBefore23 = statusBarColorBefore23;
        this.statusBarColorAfter23 = statusBarColorAfter23;
        return this;
    }

    private void setPaddingTop(View view, int padding) {
        view.setPadding(view.getPaddingLeft(), padding, view.getPaddingRight(), view.getPaddingBottom());
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * DialogFragment 单独用的一个静态方法
     * @param fragment
     */
    public static void makeDialogFragmentImmersive(DialogFragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            makeDialogWindowImmersive(fragment.getActivity(), fragment.getDialog().getWindow());
        }
    }
    public static void makeDialogFragmentImmersive(android.support.v4.app.DialogFragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            makeDialogWindowImmersive(fragment.getActivity(), fragment.getDialog().getWindow());
        }
    }
    private static void makeDialogWindowImmersive(Context context, Window window) {
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = window.getDecorView();
            view.setPadding(view.getPaddingLeft(),
                    view.getPaddingTop() + getStatusBarHeight(context),
                    view.getPaddingRight(), view.getPaddingBottom());
        } catch (Exception e) {
            Log.e("immersive", "DialogFragment 沉浸失败");
        }
    }

}