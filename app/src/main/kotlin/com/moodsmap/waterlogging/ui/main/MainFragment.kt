package com.moodsmap.waterlogging.ui.main

import android.os.Bundle
import android.view.View
import com.moodsmap.waterlogging.R
import com.moodsmap.waterlogging.presentation.base_mvp.base.BaseFragment
import javax.inject.Inject

/**
 * Created by Ray on 2018/11/27.
 */
class MainFragment : BaseFragment<MainContract.View, MainContract.Presenter>(),MainContract.View {

    override fun injectDependencies() {
        activityComponent.inject(this)
    }
    @Inject
    lateinit var mainPresenter: MainPresenter
    override fun initPresenter()=mainPresenter

    override val layoutResId = R.layout.test_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun lazyFetchData() {
    }

}