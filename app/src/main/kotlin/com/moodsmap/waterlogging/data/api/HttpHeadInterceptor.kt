package com.moodsmap.waterlogging.data.api

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Ray on ${Data}.
 */
class HttpHeadInterceptor: Interceptor {
private val TAG = "HttpHeadInterceptor"
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
        /* val token = App.tokenManager.token
         val clientId = App.tokenManager.tokenMoudel.clientId
         val memId = App.tokenManager.tokenMoudel.memId
         Log.d(TAG,"token:$token\nclientId:$clientId\nmemId:$memId\nURL:${original.url()}")
         if (!TextUtils.isEmpty(token)) {
             builder.header("token", token)
             builder.header("appType", PhoneInfo.PLATFORM)
             builder.header("appVersion", App.tokenManager.appVersion)
             if (!TextUtils.isEmpty(clientId)) builder.header("clientId", clientId)
             if (!TextUtils.isEmpty(memId)) builder.header("memId", memId)
             else builder.header("memId", "0")
         }*/
        val request = builder.method(original.method(), original.body()).build()
        return chain.proceed(request)
    }

}