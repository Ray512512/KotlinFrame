package com.moodsmap.waterlogging.ui.dispatch.login

import com.moodsmap.waterlogging.di.scope.PerActivity
import com.moodsmap.waterlogging.presentation.base_mvp.api.ApiPresenter
import javax.inject.Inject
import com.moodsmap.waterlogging.data.repository.DataRepository
import com.moodsmap.waterlogging.ui.dispatch.login.LoginContract

/**
 */
@PerActivity
class LoginPresenter @Inject constructor(private val dataRepository: DataRepository)
    : ApiPresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun requestLoginByPsw(account: String, psw: String,isShow:Boolean) {

    }

}