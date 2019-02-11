package com.ray.frame.presentation.widget.loc

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.model.LatLng
import com.luck.picture.lib.dialog.PictureDialog
import com.ray.frame.App
import com.ray.frame.data.AppConst
import com.ray.frame.data.domain.entity.UserCity
import com.ray.frame.presentation.kotlinx.extensions.delay
import com.ray.frame.presentation.kotlinx.extensions.unSafeLazy
import com.ray.frame.presentation.rxutil.RxInterface
import com.ray.frame.presentation.utils.SPUtils

/**
 * Created by Ray on 2018/10/16.
 */
class LocManager constructor(var context:Context,var autoDestory:Boolean=false): AMapLocationListener  {

    private var isPost = false
    private var isDestory = false
    private var city: UserCity?=null
    private  var mlocationClient: AMapLocationClient = AMapLocationClient(context)
    private  var mLocationOption: AMapLocationClientOption = AMapLocationClientOption()
    private var locCallback: RxInterface.simpleR<UserCity?>?=null
    private val dialog: PictureDialog by unSafeLazy {
        PictureDialog(context)
    }
    init {
        mlocationClient.setLocationListener(this)
        mLocationOption.isOnceLocation = true
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
        mLocationOption.interval = 2000
        mlocationClient.setLocationOption(mLocationOption)
    }

    fun start(simple: RxInterface.simpleR<UserCity?>){
        this.locCallback=simple
        dialog.show()
        isPost=false
        if(city!=null){
            isPost=true
            sendResult(city)
            return
        }
        mlocationClient.startLocation()
        delay(10000){
            if(isDestory)return@delay
            if(!isPost){
                isPost=true
                sendResult(null)
            }
        }
    }

    private fun sendResult(city:UserCity?){
        dialog.dismiss()
        if(city==null){
            val loc=SPUtils.get(AppConst.SPKey.USER_CITY,null)
            if(loc!=null){
                locCallback?.action(loc as UserCity)
            }else{
                locCallback?.action(null)
            }
        }else{
            locCallback?.action(city)
        }

        if(autoDestory){
            destory()
        }
    }

    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                App.lanlng= LatLng(amapLocation.latitude,amapLocation.longitude)
                if(city==null)city= UserCity()
                city!!.proviceName =amapLocation.province
                city!!.cityName =amapLocation.city
                city!!.district =amapLocation.district
                city!!.lat =amapLocation.latitude
                city!!.lng =amapLocation.longitude
                if (!isPost) {
                    if (!TextUtils.isEmpty(city!!.cityName)) {
                        SPUtils.put(AppConst.SPKey.USER_CITY, city)
                        sendResult(city)
                        isPost = true
                    }
                }
            } else {
                sendResult(null)
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.errorCode + ", errInfo:"
                        + amapLocation.errorInfo)
            }
        }
    }

    fun destory(){
        isDestory=true
        mlocationClient.onDestroy()
    }
}
