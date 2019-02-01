package com.moodsmap.waterlogging.common.ui.presenter

import com.bd.travel.common.ui.contact.PListContract
import com.moodsmap.waterlogging.data.repository.DataRepository
import com.moodsmap.waterlogging.presentation.base_mvp.api.ApiPresenter
import javax.inject.Inject

open class PListPresenter<T> @Inject constructor(val dataRepository: DataRepository) : ApiPresenter<PListContract.View<T>>(), PListContract.Presenter<T> {


    override fun getListDataRepository()=dataRepository

    override fun loadData(pageIndex: Int, pageSize: Int, key: String) {
        view?.showLoadingDialog()
        view.getDataRepositoryFun2()?.let {
            fetch(it,success = {
                view?.loadData(it)
                view?.loadDataSuccess(it.getListD(key).list)
            })
        }
        view?.getDataRepositoryFun()?.let {
            fetch(it,success = {
                view?.loadDataSuccess(it.list)
            })
        }
    }

}