package com.ray.frame.ui.main

import com.ray.frame.presentation.base_mvp.api.ApiContract

/**
 * Created by Ray on 2017/10/18.
 */
interface MainContract {
    interface View : ApiContract.View {
    }

    interface Presenter : ApiContract.Presenter<View> {

    }
}