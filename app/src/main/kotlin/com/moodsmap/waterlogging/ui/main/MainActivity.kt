package com.moodsmap.waterlogging.ui.main

import android.os.Bundle
import com.moodsmap.waterlogging.R
import com.moodsmap.waterlogging.presentation.base_mvp.base.BaseActivity
import com.moodsmap.waterlogging.presentation.utils.extensions.getStatusBarHeight
import kotlinx.android.synthetic.main.activity_map.*
import javax.inject.Inject


/**
 * Created by Ray on 2017/11/6.
 */
class MainActivity : BaseActivity<MainContract.View, MainContract.Presenter>(), MainContract.View{

    @Inject
    protected lateinit var mainPresenter: MainPresenter

    override fun initPresenter() = mainPresenter

    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        transparentStatusBar()
        statusBar.layoutParams.height = getStatusBarHeight()

        initView()
    }


    private fun initView() {

    }

    override fun loadLegend(legend: Int) {

    }
}