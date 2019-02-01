package com.moodsmap.waterlogging.ui.main

import com.moodsmap.waterlogging.data.repository.DataRepository
import com.moodsmap.waterlogging.di.scope.PerActivity
import com.moodsmap.waterlogging.presentation.base_mvp.api.ApiPresenter
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