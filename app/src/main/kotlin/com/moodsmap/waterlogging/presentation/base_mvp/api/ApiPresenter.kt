package com.moodsmap.waterlogging.presentation.base_mvp.api

import android.support.annotation.CallSuper
import com.moodsmap.waterlogging.data.domain.fetcher.Fetcher
import com.moodsmap.waterlogging.data.domain.fetcher.Status
import com.moodsmap.waterlogging.data.domain.fetcher.result_listener.RequestType
import com.moodsmap.waterlogging.data.domain.fetcher.result_listener.ResultListener
import com.moodsmap.waterlogging.presentation.base_mvp.base.BaseContract
import com.moodsmap.waterlogging.presentation.base_mvp.base.BasePresenter
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 */

abstract class ApiPresenter<VIEW : BaseContract.View> : BasePresenter<VIEW>(), ResultListener {

    @Inject
    protected lateinit var fetcher: Fetcher

    private val TYPE_NONE = RequestType.TYPE_NONE
    protected val SUCCESS = Status.SUCCESS
    protected val LOADING = Status.LOADING
    protected val ERROR = Status.ERROR
    protected val EMPTY_SUCCESS = Status.EMPTY_SUCCESS

    protected infix fun RequestType.statusIs(status: Status) = fetcher.getRequestStatus(this) == status

    protected val RequestType.status
        get() = fetcher.getRequestStatus(this)

    fun <TYPE> fetch(flowable: Flowable<TYPE>,
                     requestType: RequestType = TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(flowable, requestType, this, success)
    }

    fun <TYPE> fetch(observable: Observable<TYPE>,
                     requestType: RequestType = TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(observable, requestType, this, success)
    }

    fun <TYPE> fetch(single: Single<TYPE>,
                     requestType: RequestType = TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(single, requestType, this, success)
    }

    fun complete(completable: Completable,
                 requestType: RequestType = TYPE_NONE, success: () -> Unit = {}) {
        fetcher.complete(completable, requestType, this, success)
    }

    @CallSuper
    override fun onPresenterDestroy() {
        super.onPresenterDestroy()
        fetcher.clear()
    }

    @CallSuper
    override fun onRequestStart(requestType: RequestType) {
        onRequestStart()
    }

    @CallSuper
    override fun onRequestError(requestType: RequestType, errorMessage: String?) {
        onRequestError(errorMessage)
    }

    override fun onRequestError(requestType: RequestType, error: Throwable) {
        super.onRequestError(requestType,error)
    }
}