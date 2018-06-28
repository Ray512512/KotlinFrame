package com.moodsmap.waterlogging.presentation.location

import android.content.Context
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener


/**
 * Created by gong on 2018/1/9.
 */
class LocationClient(context: Context) {
    private var realClient: AMapLocationClient = AMapLocationClient(context)

    @Volatile private var proxyClient: LocationClient? = null

    fun get(context: Context): LocationClient {
        if (proxyClient == null) {
            synchronized(AMapLocationClient::class.java) {
                if (proxyClient == null) {
                    proxyClient = LocationClient(context.applicationContext)
                }
            }
        }
        return proxyClient!!
    }

    init {
        val option = AMapLocationClientOption()
        option.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
//        option.isOnceLocation = true
        option.isNeedAddress = true
        option.httpTimeOut = 10000
        realClient.setLocationOption(option)
    }

    fun locate(aMapLocationListener: AMapLocationListener) {
        val realListener: AMapLocationListener = object : AMapLocationListener {
            override fun onLocationChanged(location: AMapLocation?) {
                aMapLocationListener.onLocationChanged(location)
                realClient.unRegisterLocationListener(this)
                stop()
            }
        }
        realClient.setLocationListener(realListener)
        if (!realClient.isStarted) {
            realClient.startLocation()
        }
    }

    fun getLastKnownLocation(): AMapLocation? {
        return realClient.lastKnownLocation
    }

    fun stop() {
        realClient.stopLocation()
    }

}