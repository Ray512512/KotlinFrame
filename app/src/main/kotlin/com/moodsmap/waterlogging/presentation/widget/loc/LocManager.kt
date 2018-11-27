package com.moodsmap.waterlogging.presentation.widget.loc

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.moodsmap.waterlogging.presentation.rxutil.RxBus
import com.moodsmap.waterlogging.presentation.rxutil.RxEvent
import com.moodsmap.waterlogging.data.AppConst
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.delay
import com.moodsmap.waterlogging.presentation.utils.SPUtils

/**
 * Created by Ray on 2018/10/16.
 */
class LocManager constructor(var context:Context): AMapLocationListener  {

    private var isPost = false
    private var city=""
    private  var mlocationClient: AMapLocationClient = AMapLocationClient(context)
    private  var mLocationOption: AMapLocationClientOption = AMapLocationClientOption()

    init {
        mlocationClient.setLocationListener(this)
        mLocationOption.isOnceLocation = true
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
        mLocationOption.interval = 2000
        mlocationClient.setLocationOption(mLocationOption)
    }

    fun start(){
        isPost=false
        if(!city.isEmpty()){
            isPost=true
            RxBus.sBus.post(RxEvent.GET_CITY, city)
            return
        }
        mlocationClient.startLocation()
        delay(10000){
            if(!isPost){
                isPost=true
                RxBus.sBus.post(RxEvent.GET_CITY, "")
            }
        }
    }

    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                 city =amapLocation.province+","+amapLocation.city
                if (!isPost) {
                    if (!TextUtils.isEmpty(city)) {
                        SPUtils.put(AppConst.SPKey.USER_CITY, city)
                        RxBus.sBus.post(RxEvent.GET_CITY, city)
                        isPost = true
                    }
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.errorCode + ", errInfo:"
                        + amapLocation.errorInfo)
            }
        }
    }

    fun destory(){
        mlocationClient.onDestroy()
    }
}
