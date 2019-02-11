package com.ray.frame.common.web

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import com.ray.frame.di.scope.PerActivity
import com.ray.frame.presentation.base_mvp.api.ApiPresenter
import javax.inject.Inject
import com.ray.frame.data.repository.DataRepository


@PerActivity
class WebPresenter @Inject constructor(private val dataRepository: DataRepository) : ApiPresenter<WebContract.View>(), WebContract.Presenter {

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
        view.onLifecycleChanged(owner, event)
    }
}