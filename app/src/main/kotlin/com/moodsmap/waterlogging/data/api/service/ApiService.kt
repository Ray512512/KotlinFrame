package com.moodsmap.waterlogging.data.api.service

import com.moodsmap.waterlogging.data.api.domain.entity.BaseRes
import com.moodsmap.waterlogging.data.api.domain.entity.simple.Warning
import io.reactivex.Flowable
import retrofit2.http.GET

/**
 * Created by gong on 2017/10/18.
 */
interface ApiService {

    //预警列表
    @GET("?interfaceId=getWarninfo&starttime=2017-11-21")
    fun getWarning(): Flowable<BaseRes<List<Warning>>>

}