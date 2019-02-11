package com.ray.frame.presentation.base_mvp.base

import android.content.Context
import com.ray.frame.data.domain.fetcher.DealErrorType
import io.armcha.arch.BaseMVPContract


/**
 */
interface BaseContract {

    interface View : BaseMVPContract.View {
        fun showLoadingDialog(canCancle:Boolean=true)
        fun showLoadingDialog(message: String,canCancle:Boolean=true)
        fun showLoadingDialog(message: Int,canCancle:Boolean=true)
        fun stopLoadingDialog()
        fun getContext():Context?

        fun showNetWorkErrorView(dealErrorType: DealErrorType)
        fun showErrorView(dealErrorType: DealErrorType)
        fun showErrorView(str: String)
        fun showDataView()
        fun showEmptyView()
    }

    interface Presenter<V : BaseMVPContract.View> : BaseMVPContract.Presenter<V>
}