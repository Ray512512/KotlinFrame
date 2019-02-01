package com.moodsmap.waterlogging.ui.dispatch.login

import com.moodsmap.waterlogging.presentation.base_mvp.base.BaseContract


/**
 */
interface LoginContract {

    interface View : BaseContract.View {
        fun onLoginSucceed(info: Any)
        fun onLoginFailed(msg:String)
    }

    interface Presenter : BaseContract.Presenter<View>{
        fun requestLoginByPsw(account:String, psw:String,isShow:Boolean=true)
    }
}