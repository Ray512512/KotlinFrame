package com.moodsmap.waterlogging.data.api.domain.cache

import android.util.LruCache
import com.moodsmap.waterlogging.data.api.domain.fetcher.result_listener.RequestType
import javax.inject.Inject
import javax.inject.Singleton

/**
 */
@Singleton
class MemoryCache @Inject constructor() : LruCache<RequestType, Any>(1024 * 1024 * 2)/* 2 MB */ {

    inline infix fun <reified V> getCacheForType(key: RequestType) = get(key) as V
}