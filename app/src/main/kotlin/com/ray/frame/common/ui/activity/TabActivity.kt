package com.ray.frame.common.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.bd.travel.common.di.EmptyPresenter
import com.ray.frame.R
import com.ray.frame.common.adapter.IndicatorAdapter
import com.ray.frame.common.adapter.PagerFragmentAdapter
import com.ray.frame.common.di.EmptyContract
import com.ray.frame.presentation.base_mvp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tab.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import java.util.*
import javax.inject.Inject

/**
 * 无需实现任何网络加载数据逻辑界面
 */
abstract class TabActivity : BaseActivity<EmptyContract.View, EmptyContract.Presenter>(),EmptyContract.View {
   
    @Inject
    lateinit var emptyPresenter: EmptyPresenter
    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    override fun initPresenter()=emptyPresenter

    //分类菜单
    abstract var mTitleDataList :Array<String>
    abstract var mTitle :String
    //分类容器
    protected abstract fun getFragments(): List<Fragment>
    //分类适配器
    lateinit var adapter: PagerFragmentAdapter
    //分类指示器类型
    open var indicatorType: IndicatorAdapter.IndicatorType=IndicatorAdapter.IndicatorType.MATCH_H_1
    protected lateinit var indicatorAdapter:IndicatorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_tab)
        super.onCreate(savedInstanceState)
        initView()
    }


    /**
     * 初始化界面
     */
    private fun initView(){
        tab_title.setTitleText(mTitle)

        initHelperView(view_pager)
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode=true
        indicatorAdapter= IndicatorAdapter(mTitleDataList, view_pager,indicatorType)
        indicatorAdapter.setTvColor(R.color.text_1,R.color.mainColor)
        commonNavigator.adapter = indicatorAdapter
        magic_indicator.navigator = commonNavigator
        ViewPagerHelper.bind(magic_indicator, view_pager)
        adapter = PagerFragmentAdapter(supportFragmentManager, getFragments() as ArrayList<Fragment>, mTitleDataList)
        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = getFragments().size
    }
}
