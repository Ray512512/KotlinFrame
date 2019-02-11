package com.ray.frame.ui.dispatch.login

import com.ray.frame.di.scope.PerActivity
import com.ray.frame.presentation.base_mvp.api.ApiPresenter
import javax.inject.Inject
import com.ray.frame.data.repository.DataRepository
import com.ray.frame.ui.dispatch.login.LoginContract

/**
 */
@PerActivity
class LoginPresenter @Inject constructor(private val dataRepository: DataRepository)
    : ApiPresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun requestLoginByPsw(account: String, psw: String,isShow:Boolean) {

    }

}