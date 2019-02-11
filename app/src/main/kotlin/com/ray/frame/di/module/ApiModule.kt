package com.ray.frame.di.module

import android.util.Log
import com.ray.frame.BuildConfig
import com.ray.frame.data.api.ApiConstants
import com.ray.frame.data.api.HttpHeadInterceptor
import com.ray.frame.data.api.service.ApiService
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Ray on 2017/10/18.
 */

@Module
class ApiModule {

    @Singleton
    @Provides
    @Named("apiEndpoint")
    fun apiEndpoint(): String = ApiConstants.API_ENDPOINT


    @Singleton
    @Provides
    fun provideOkHttpBuilder(): OkHttpClient.Builder {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            okHttpBuilder.addInterceptor(logging)
            okHttpBuilder.addInterceptor(getBodyLogInterceptor())
        }
        okHttpBuilder.addInterceptor(HttpHeadInterceptor())
        okHttpBuilder.readTimeout(30.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.connectTimeout(30.toLong(), TimeUnit.SECONDS)
        return okHttpBuilder
    }

    /**
     * 日志打印拦截
     */
    private fun getBodyLogInterceptor():HttpLoggingInterceptor{
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            //打印retrofit日志
            Log.i("RetrofitLog", "retrofitBack = $message")
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Singleton
    @Provides
    @Named("apiRetrofit")
    fun provideApiRetrofit(retrofitBuilder: Retrofit.Builder,
                        okHttpClientBuilder: OkHttpClient.Builder,
                        @Named("apiEndpoint") baseUrl: String): Retrofit {
        return retrofitBuilder
                .client(okHttpClientBuilder.build())
                .baseUrl(baseUrl)
                .build()
    }


    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Singleton
    @Provides
    fun provideApiService(@Named("apiRetrofit") retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)



//    @Singleton
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()
}