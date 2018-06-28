package com.moodsmap.waterlogging.ui.dispatch

import com.moodsmap.waterlogging.presentation.base_mvp.base.BaseContract


/**
 */
interface DispatchContract {

    interface View : BaseContract.View {

        fun initPermission()

        fun requestPermission()

        fun openHomeActivity()

        fun openLoginActivity()
    }

    interface Presenter : BaseContract.Presenter<View>
}