package com.moodsmap.waterlogging.presentation.location

import android.content.Context
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationListener
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe

/**
 * Created by Ray on 2018/1/9.
 */
class LocationOnSubscribe(private var context: Context) : FlowableOnSubscribe<AMapLocation> {

    override fun subscribe(e: FlowableEmitter<AMapLocation>) {
        val locationListener = AMapLocationListener { location ->
            if (location != null) {
                e.onNext(location)
                e.onComplete()
            } else {
                e.onError(Throwable("定位失败咯"))
            }
        }
        LocationClient(context).locate(locationListener)
    }

//    override fun subscribe(subscribe: Subscriber<in AMapLocation>?) {
//        subscribe?.let {
//            val locationListener = AMapLocationListener { location ->
//                if (location != null) {
//                    it.onNext(location)
//                    it.onComplete()
//                } else {
//                    it.onError(Throwable("定位失败咯"))
//                }
//            }
//            LocationClient(context).locate(locationListener)
//        }
//    }
}