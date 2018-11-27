package com.moodsmap.waterlogging.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by gong on 2017/10/18.
 */
@Module
class CommonModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

}