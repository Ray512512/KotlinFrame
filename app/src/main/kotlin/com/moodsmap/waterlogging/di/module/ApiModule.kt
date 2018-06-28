package com.moodsmap.waterlogging.di.module

import com.moodsmap.waterlogging.BuildConfig
import com.moodsmap.waterlogging.data.api.ApiConstants
import com.moodsmap.waterlogging.data.api.service.ApiService
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
 * Created by gong on 2017/10/18.
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
        }
        okHttpBuilder.readTimeout(15.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.connectTimeout(15.toLong(), TimeUnit.SECONDS)
        return okHttpBuilder
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



    @Singleton
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()
}