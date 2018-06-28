package com.moodsmap.waterlogging.di.component

import dagger.Component
import com.moodsmap.waterlogging.di.module.ActivityModule
import com.moodsmap.waterlogging.di.module.ApiModule
import com.moodsmap.waterlogging.di.module.ApplicationModule
import javax.inject.Singleton

/**
 * Created by gong on 2017/10/18.
 */

@Singleton
@Component(modules = [(ApplicationModule::class), (ApiModule::class)])
interface ApplicationComponent {
    fun plus(activityModule: ActivityModule): ActivityComponent
}