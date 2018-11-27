package com.moodsmap.waterlogging.di.component

import com.moodsmap.waterlogging.App
import com.moodsmap.waterlogging.di.module.CommonModule
import com.moodsmap.waterlogging.di.scope.PerActivity
import dagger.Subcomponent

/**
 * Created by gong on 2017/10/18.
 */
@PerActivity
@Subcomponent(modules = [(CommonModule::class)])
interface CommonComponent {
    fun inject(app: App)
}
