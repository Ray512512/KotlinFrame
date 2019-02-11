package com.ray.frame.presentation.rxutil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import com.luck.picture.lib.permissions.RxPermissions;
import com.ray.frame.R;
import com.ray.frame.presentation.utils.PermissionPageUtils;
import com.ray.frame.view.dialog.MaterialDialog;

/**
 * Created by Ray on 2018/8/15.
 */
public class RxUtil {

    @SuppressLint("CheckResult")
    public static void askPermission(Activity activity, int hintStr, RxInterface.simple simple, String ...permissionArray){
        new RxPermissions(activity).request(permissionArray)
                .subscribe(granted -> {
                    if (granted) {
                        simple.action();
                    } else {
                        showHintDialog(activity, hintStr,R.string.btn_go, () -> new PermissionPageUtils(activity).jumpPermissionPage());
                    }
                });
    }

    @SuppressLint("CheckResult")
    public static void askGpsBeforeMakeOrder(Activity activity, RxInterface.simple2 simple){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean p1=activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED;
            boolean p2=activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED;
            if(p1&&p2){
                simple.action1();
            }else {
                new RxPermissions(activity).requestEach(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(permission -> { // will emit 2 Permission objects
                            if (permission.granted) {
                                simple.action1();
                            } else {
                                simple.action2();
                            }
                        });
            }
        }else {
            simple.action1();
        }
    }

    private static void showHintDialog(Activity activity,int hintResId,int btnRes,RxInterface.simple simple){
        MaterialDialog s=new MaterialDialog(activity);
        s.title(activity.getString(R.string.system_dialog_title)).message(activity.getString(hintResId))
                .addPositiveButton(activity.getString(btnRes), materialDialog -> {
                    simple.action();
                    s.dismiss();
                    return null;
                }).show();
    }
}
