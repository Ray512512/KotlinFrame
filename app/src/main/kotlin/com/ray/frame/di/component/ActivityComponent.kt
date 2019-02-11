package com.ray.frame.di.component

import com.ray.frame.common.ui.activity.TabActivity
import com.ray.frame.common.ui.activity.EmptyActivity
import com.ray.frame.common.web.WebActivity
import com.ray.frame.di.module.ActivityModule
import com.ray.frame.di.scope.PerActivity
import com.ray.frame.ui.dispatch.DispatchActivity
import com.ray.frame.ui.dispatch.login.LoginActivity
import com.ray.frame.ui.main.MainActivity
import com.ray.frame.ui.main.MainFragment
import dagger.Subcomponent

/**
 * Created by Ray on 2017/10/18.
 */
@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(dispatchActivity: DispatchActivity)
    fun inject(mapActivity: MainActivity)
    fun inject(loginFragment: LoginActivity)
    fun inject(loginFragment: WebActivity)
    fun inject(loginFragment: MainFragment)
    fun inject(loginFragment: EmptyActivity)
    fun inject(loginFragment: TabActivity)


}
