package com.moodsmap.waterlogging

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.luseen.logger.LogType
import com.luseen.logger.Logger
import com.moodsmap.waterlogging.common.CrashHandler
import com.moodsmap.waterlogging.di.component.ApplicationComponent
import com.moodsmap.waterlogging.di.component.DaggerApplicationComponent
import com.moodsmap.waterlogging.di.module.ApplicationModule
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.get
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.unSafeLazy
import com.moodsmap.waterlogging.presentation.utils.Lg
import com.moodsmap.waterlogging.presentation.utils.Utils
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

    override  fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
    override fun onCreate() {
        super.onCreate()
        if (Utils.getCurrentProcessName(this) != packageName){
            Lg.v("Application",packageName+"不是主进程")
            return
        }
        instance = this
        CrashHandler.getInstance().init(this)
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
                .tag(get(R.string.app_tag_name))
                .setIsKotlin(true)
                .build()
    }
}