package com.ray.frame.ui.main

import com.ray.frame.data.repository.DataRepository
import com.ray.frame.di.scope.PerActivity
import com.ray.frame.presentation.base_mvp.api.ApiPresenter
import javax.inject.Inject


@PerActivity
open class MainPresenter @Inject constructor(private val dataRepository: DataRepository) : ApiPresenter<MainContract.View>(), MainContract.Presenter {
    private val TAG ="MainPresenter"

    override fun onPresenterCreate() {
        super.onPresenterCreate()
        /*fetch(dataRepository.getWarning(),success = {
            it.isEmpty()
        })*/
    }

}