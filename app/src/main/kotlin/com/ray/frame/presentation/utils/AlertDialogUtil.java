package com.ray.frame.presentation.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

import com.ray.frame.R;


/**
 * Created by ray on 2016/3/31.
 */
public class AlertDialogUtil {
    /**
     * 自定义两个按钮，自定义提示内容 监听positive按钮
     * */
    public static  void  AlertDialog(Context context, String message, String PositiveButton, String NegativeButton, DialogInterface.OnClickListener listener){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.alertdialog_title));
        builder.setMessage(message);
        builder.setPositiveButton(PositiveButton, listener);
        builder.setNegativeButton(NegativeButton, (dialogInterface, i) -> builder.create().dismiss());
        builder.create().show();
    }

    /**
     * 自定义两个按钮，自定义提示内容 监听两个按钮
     * */
    public static  void  AlertDialog(Context context, String message, String PositiveButton, String NegativeButton, DialogInterface.OnClickListener listener, DialogInterface.OnClickListener listener2){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.alertdialog_title));
        builder.setMessage(message);
        builder.setPositiveButton(PositiveButton, listener);
        builder.setNegativeButton(NegativeButton, listener2);
        builder.create().show();
    }
    /**
     * 自定义两个按钮，自定义所有， 监听positive按钮
     * */
    public static  void  AlertDialog(Context context, String title, String message, String PositiveButton, String NegativeButton, DialogInterface.OnClickListener listener){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.alertdialog_title));
        builder.setMessage(message);
        builder.setPositiveButton(PositiveButton, listener);
        builder.setNegativeButton(NegativeButton, (dialogInterface, i) -> builder.create().dismiss());
        builder.create().show();
    }

    /**
     *
     *自定义一个按钮 无任何监听 点击取消
     **/
    public static  void  AlertDialog(Context context, String message, String NegativeButton){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setNegativeButton(NegativeButton, (dialogInterface, i) -> builder.create().dismiss());
        builder.create().show();
    }

    public static  void  AlertDialog(Context context, String PositiveButton, String message,DialogInterface.OnClickListener listener){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.alertdialog_title));
        builder.setMessage(message);
        builder.setPositiveButton(PositiveButton, listener);
        builder.create().show();
    }

    public static void AlertSystemDialog(Context context,String message){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.alertdialog_title));
        builder.setMessage(message);
//        builder.setPositiveButon(PositiveButton, listener);
        AlertDialog dialog=builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }
}
