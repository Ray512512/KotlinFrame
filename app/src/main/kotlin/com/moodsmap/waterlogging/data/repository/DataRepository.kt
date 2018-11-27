package com.moodsmap.waterlogging.data.repository

import com.moodsmap.waterlogging.data.domain.cache.MemoryCache
import com.moodsmap.waterlogging.data.domain.entity.simple.Warning
import com.moodsmap.waterlogging.data.domain.fetcher.result_listener.RequestType
import com.moodsmap.waterlogging.data.api.service.ApiService
import com.moodsmap.waterlogging.di.scope.PerActivity
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.handleResult
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Ray on 2017/10/18.
 */
@PerActivity
class DataRepository @Inject constructor(private var apiService: ApiService,
                                         private val memoryCache: MemoryCache) {

    fun getWarning(): Flowable<List<Warning>> = apiService.getWarning().compose(handleResult())
    fun getWarningFromMemory(): List<Warning> = memoryCache getCacheForType (RequestType.WARNING)

}