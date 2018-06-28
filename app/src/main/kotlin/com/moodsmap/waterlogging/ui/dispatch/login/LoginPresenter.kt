package com.moodsmap.waterlogging.ui.dispatch.login

import com.moodsmap.waterlogging.di.scope.PerActivity
import com.moodsmap.waterlogging.data.repository.DataRepository
import com.moodsmap.waterlogging.presentation.base_mvp.base.BasePresenter
import javax.inject.Inject

/**
 */
@PerActivity
class LoginPresenter @Inject constructor(private val repository: DataRepository)
    : BasePresenter<LoginContract.View>(), LoginContract.Presenter {
    override fun requestLogin(account: String, code: String) {
        view.onLoginSucceed()
    }

}