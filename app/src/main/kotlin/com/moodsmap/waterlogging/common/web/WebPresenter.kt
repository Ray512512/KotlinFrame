package com.moodsmap.waterlogging.common.web

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import com.moodsmap.waterlogging.di.scope.PerActivity
import com.moodsmap.waterlogging.presentation.base_mvp.api.ApiPresenter
import javax.inject.Inject
import com.moodsmap.waterlogging.data.repository.DataRepository


@PerActivity
class WebPresenter @Inject constructor(private val dataRepository: DataRepository) : ApiPresenter<WebContract.View>(), WebContract.Presenter {

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
        view.onLifecycleChanged(owner, event)
    }
}