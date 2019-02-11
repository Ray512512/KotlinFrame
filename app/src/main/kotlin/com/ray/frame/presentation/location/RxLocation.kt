package com.ray.frame.presentation.location

import android.content.Context
import com.amap.api.location.AMapLocation
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

/**
 * Created by Ray on 2018/1/9.
 */

object RxLocation {

    fun locate(context: Context): Flowable<AMapLocation> {
        return Flowable.create(LocationOnSubscribe(context),BackpressureStrategy.BUFFER)
    }


}
