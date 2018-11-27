package com.moodsmap.waterlogging.presentation.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.moodsmap.waterlogging.App;

/**
 * Created by hc on 2017/4/21.
 */

public class ToastUtils {

    private static long beforTime;
    private static String beforText;

    /**
     * to avoid show same message  in 60 second
     * @param text show message
     * if(System.currentTimeMillis()-beforTime<50000){
       return;
       }
       if(text.equals(beforText)){
        return;
      }
     */

    public static  void  show (String text) {
        Toast toast= Toast.makeText(App.instance,text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
       // beforText=text;
        //beforTime = System.currentTimeMillis();
    }

    public static  void  showSingle (String text) {
        if(System.currentTimeMillis()-beforTime<50000){
            return;
        }
        if(text.equals(beforText)){
            return;
        }
        Toast toast= Toast.makeText(App.instance,text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        beforText=text;
        beforTime = System.currentTimeMillis();
    }
}
