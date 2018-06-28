package com.moodsmap.waterlogging.di.component

import com.moodsmap.waterlogging.di.module.ActivityModule
import com.moodsmap.waterlogging.di.scope.PerActivity
import com.moodsmap.waterlogging.ui.dispatch.DispatchActivity
import com.moodsmap.waterlogging.ui.dispatch.login.LoginFragment
import com.moodsmap.waterlogging.ui.main.MainActivity
import dagger.Subcomponent

/**
 * Created by gong on 2017/10/18.
 */
@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(dispatchActivity: DispatchActivity)
    fun inject(mapActivity: MainActivity)
    fun inject(loginFragment: LoginFragment)


}
