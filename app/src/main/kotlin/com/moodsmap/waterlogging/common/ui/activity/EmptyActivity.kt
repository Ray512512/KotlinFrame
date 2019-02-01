package com.moodsmap.waterlogging.common.ui.activity

import android.os.Bundle
import com.bd.travel.common.di.EmptyPresenter
import com.moodsmap.waterlogging.common.di.EmptyContract
import com.moodsmap.waterlogging.presentation.base_mvp.base.BaseActivity
import javax.inject.Inject

/**
 * 无需实现任何网络加载数据逻辑界面
 */
abstract class EmptyActivity : BaseActivity<EmptyContract.View, EmptyContract.Presenter>(),EmptyContract.View {
   
    @Inject
    lateinit var emptyPresenter: EmptyPresenter
    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    override fun initPresenter()=emptyPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
