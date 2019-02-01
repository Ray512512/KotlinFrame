package com.moodsmap.waterlogging.presentation.utils

import android.content.Context
import android.view.Surface
import android.view.WindowManager
import java.text.DecimalFormat


/**
 * 地理位置计算工具类
 */
object LocationUtil {

    /**
     * 计算指定坐标距离当前用户距离
     */
    /*fun getDistance(lng: LatLng): String {
        val distance = ""
        if (App.lanlng.latitude == 0.0) return distance
        val myL = App.lanlng
        val location = AMapUtils.calculateLineDistance(myL, lng)
        if (location.toDouble() == -1.0) {
            return distance
        }
        return if (location > 1000) {
            val a = DecimalFormat("##0.0").format(location / 1000)
            a + "km"
        } else {
            DecimalFormat("##0.0").format(location) + "m"
        }
    }*/

    /**
     * 距离转化
     */
    fun getDistance(dis: String?): String {
        if(dis.isNullOrEmpty())return ""
        var d=0
        try {
            d=dis!!.toInt()
        }catch (e:NumberFormatException){
            e.printStackTrace()
            return dis!!+"m"
        }
        return if (d> 1000) {
            val a = DecimalFormat("##0.0").format(d / 1000)
            a + "km"
        } else {
            DecimalFormat("##0.0").format(dis) + "m"
        }
    }

    /**
     * 获取当前屏幕旋转角度
     *
     * @param context
     * @return 0表示是竖屏; 90表示是左横屏; 180表示是反向竖屏; 270表示是右横屏
     */
    fun getScreenRotationOnPhone(context: Context): Int {
        val display = (context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay

        when (display.rotation) {
            Surface.ROTATION_0 -> return 0

            Surface.ROTATION_90 -> return 90

            Surface.ROTATION_180 -> return 180

            Surface.ROTATION_270 -> return -90
        }
        return 0
    }
}
