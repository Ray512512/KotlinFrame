package com.moodsmap.waterlogging.presentation.location

import android.content.Context
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationListener
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber

/**
 * Created by gong on 2018/1/9.
 */
class LocationLastKnownOnSubscribe(private var context: Context) : Publisher<AMapLocation> {

    override fun subscribe(subscriber: Subscriber<in AMapLocation>?) {
        subscriber?.let {
            val lateKnownLocation = LocationClient(context).getLastKnownLocation()
            if (lateKnownLocation != null) {
                it.onNext(lateKnownLocation)
                it.onComplete()
            } else {
                val bdLocationListener = AMapLocationListener { location ->
                    if (location != null) {
                        it.onNext(location)
                        it.onComplete()
                    } else {
                        it.onError(Throwable("定位失败咯"))
                    }
                }
                LocationClient(context).locate(bdLocationListener)
            }
        }
    }
}