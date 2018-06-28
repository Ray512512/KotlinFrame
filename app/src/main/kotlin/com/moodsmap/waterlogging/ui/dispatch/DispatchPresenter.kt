package com.moodsmap.waterlogging.ui.dispatch

import com.moodsmap.waterlogging.di.scope.PerActivity
import com.moodsmap.waterlogging.presentation.base_mvp.base.BasePresenter
import javax.inject.Inject

/**
 */
@PerActivity
class DispatchPresenter @Inject constructor() : BasePresenter<DispatchContract.View>(), DispatchContract.Presenter {

    override fun onPresenterCreate() {
        super.onPresenterCreate()
        view?.initPermission()
    }
}