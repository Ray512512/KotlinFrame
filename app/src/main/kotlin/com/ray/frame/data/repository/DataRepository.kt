package com.ray.frame.data.repository

import com.ray.frame.data.domain.cache.MemoryCache
import com.ray.frame.data.domain.entity.simple.Warning
import com.ray.frame.data.domain.fetcher.result_listener.RequestType
import com.ray.frame.data.api.service.ApiService
import com.ray.frame.di.scope.PerActivity
import com.ray.frame.presentation.kotlinx.extensions.handleResult
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