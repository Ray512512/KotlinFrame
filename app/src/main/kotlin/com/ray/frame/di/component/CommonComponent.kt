package com.ray.frame.di.component

import com.ray.frame.App
import com.ray.frame.di.module.CommonModule
import com.ray.frame.di.scope.PerActivity
import com.ray.frame.presentation.widget.work.NetWorkUtils
import dagger.Subcomponent

/**
 * Created by gong on 2017/10/18.
 */
@PerActivity
@Subcomponent(modules = [(CommonModule::class)])
interface CommonComponent {
    fun inject(app: App)
    fun inject(app: NetWorkUtils)
}
