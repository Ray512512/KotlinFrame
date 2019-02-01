package com.moodsmap.waterlogging.data.api.service

import com.moodsmap.waterlogging.data.domain.entity.base.BaseRes
import com.moodsmap.waterlogging.data.domain.entity.simple.Warning
import io.reactivex.Flowable
import retrofit2.http.GET

/**
 * Created by Ray on 2017/10/18.
 */
interface ApiService {

    //预警列表
    @GET("?interfaceId=getWarninfo&starttime=2017-11-21")
    fun getWarning(): Flowable<BaseRes<List<Warning>>>

}