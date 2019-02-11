package com.ray.frame.ui.dispatch

import com.ray.frame.presentation.base_mvp.base.BaseContract


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