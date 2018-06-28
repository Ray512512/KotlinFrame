package com.moodsmap.waterlogging.presentation.base_mvp.base

import android.content.Context
import io.armcha.arch.BaseMVPContract


/**
 */
interface BaseContract {

    interface View : BaseMVPContract.View {
        fun showLoadingDialog()
        fun showLoadingDialog(message: String)
        fun stopLoadingDialog()
        fun getContext():Context?
    }

    interface Presenter<V : BaseMVPContract.View> : BaseMVPContract.Presenter<V>
}