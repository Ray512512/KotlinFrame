package com.moodsmap.waterlogging

import android.app.Application
import android.support.multidex.MultiDex
import com.luseen.logger.LogType
import com.luseen.logger.Logger
import com.moodsmap.waterlogging.di.component.ApplicationComponent
import com.moodsmap.waterlogging.di.component.DaggerApplicationComponent
import com.moodsmap.waterlogging.di.module.ApplicationModule
import com.moodsmap.waterlogging.presentation.utils.extensions.unSafeLazy
import io.realm.Realm
import io.realm.RealmConfiguration


/**
 * Created by Ray on 2017/10/18.
 */
class App : Application() {
    val applicationComponent: ApplicationComponent by unSafeLazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
//        if (LeakCanary.isInAnalyzerProcess(this)) return
//        LeakCanary.install(this)
        instance = this
        initLogger()
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build())
    }

    private fun initLogger() {
        Logger.Builder()
                .isLoggable(BuildConfig.DEBUG)
                .logType(LogType.ERROR)
                .tag("waterlogging")
                .setIsKotlin(true)
                .build()
    }
}