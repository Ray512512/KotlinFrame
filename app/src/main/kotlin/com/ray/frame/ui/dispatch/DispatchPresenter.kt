package com.ray.frame.ui.dispatch

import com.ray.frame.di.scope.PerActivity
import com.ray.frame.presentation.base_mvp.base.BasePresenter
import javax.inject.Inject

/**
 */
@PerActivity
class DispatchPresenter @Inject constructor() : BasePresenter<DispatchContract.View>(), DispatchContract.Presenter {

}