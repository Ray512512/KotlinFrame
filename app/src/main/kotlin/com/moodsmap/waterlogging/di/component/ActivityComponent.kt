package com.moodsmap.waterlogging.di.component

import com.moodsmap.waterlogging.common.web.WebActivity
import com.moodsmap.waterlogging.di.module.ActivityModule
import com.moodsmap.waterlogging.di.scope.PerActivity
import com.moodsmap.waterlogging.ui.dispatch.DispatchActivity
import com.moodsmap.waterlogging.ui.dispatch.login.LoginFragment
import com.moodsmap.waterlogging.ui.main.MainActivity
import com.moodsmap.waterlogging.ui.main.MainFragment
import dagger.Subcomponent

/**
 * Created by Ray on 2017/10/18.
 */
@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(dispatchActivity: DispatchActivity)
    fun inject(mapActivity: MainActivity)
    fun inject(loginFragment: LoginFragment)
    fun inject(loginFragment: WebActivity)
    fun inject(loginFragment: MainFragment)


}
