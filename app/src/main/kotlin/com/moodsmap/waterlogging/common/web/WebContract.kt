package com.moodsmap.waterlogging.common.web

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import com.moodsmap.waterlogging.presentation.base_mvp.api.ApiContract

/**
 * Created by Ray on 2018/7/19.
 */
interface WebContract{


    interface View : ApiContract.View{
        fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event)
    }


    interface Presenter : ApiContract.Presenter<View>

}