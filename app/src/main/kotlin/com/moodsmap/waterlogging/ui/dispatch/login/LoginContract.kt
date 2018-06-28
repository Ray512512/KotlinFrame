package com.moodsmap.waterlogging.ui.dispatch.login

import com.moodsmap.waterlogging.presentation.base_mvp.base.BaseContract


/**
 */
interface LoginContract {

    interface View : BaseContract.View {

        fun onLoginSucceed()
    }

    interface Presenter : BaseContract.Presenter<View>{
        fun requestLogin(account:String,code:String)
    }
}