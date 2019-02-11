package com.ray.frame.common.ui.presenter

import com.ray.frame.common.ui.contact.ListContract
import com.ray.frame.data.repository.DataRepository
import com.ray.frame.presentation.base_mvp.api.ApiPresenter
import javax.inject.Inject

open class ListPresenter<T> @Inject constructor( val dataRepository: DataRepository) : ApiPresenter<ListContract.View<T>>(), ListContract.Presenter<T> {
    override fun getListDataRepository()=dataRepository

    override fun loadData(pageIndex: Int, pageSize: Int) {
        view?.showLoadingDialog()
        fetch(view.getDataRepositoryFun(),success = {
            view?.loadDataSuccess(it.list)
        })
    }

}