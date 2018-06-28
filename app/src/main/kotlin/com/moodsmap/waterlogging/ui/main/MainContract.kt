package com.moodsmap.waterlogging.ui.main

import com.moodsmap.waterlogging.presentation.base_mvp.api.ApiContract

/**
 * Created by Ray on 2017/10/18.
 */
interface MainContract {
    interface View : ApiContract.View {
        fun loadLegend(legend: Int)
    }

    interface Presenter : ApiContract.Presenter<View> {

    }
}