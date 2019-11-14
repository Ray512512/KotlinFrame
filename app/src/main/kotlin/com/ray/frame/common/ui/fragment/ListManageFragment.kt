package com.ray.frame.common.ui.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bd.travel.common.ui.fragment.ListFragment
import com.freelib.multiitem.adapter.BaseItemAdapter
import com.freelib.multiitem.animation.ScaleInAnimation
import com.ray.frame.R
import com.ray.frame.common.ui.contact.ListContract
import com.ray.frame.common.ui.presenter.ListPresenter
import com.ray.frame.presentation.base_mvp.base.BaseFragment
import com.ray.frame.presentation.kotlinx.extensions.takeDrawable
import com.ray.frame.view.pulltorefreshview.PullToRefreshLayout
import kotlinx.android.synthetic.main.pull_rec.*
import javax.inject.Inject

/**
 * 父类实现加载数据等基础业务逻辑
 * 子类只需关心实际业务
 */
abstract class ListManageFragment<D>: BaseFragment<ListContract.View<D>, ListContract.Presenter<D>>(), PullToRefreshLayout.OnRefreshListener, ListContract.View<D> {
    override  val layoutResId = R.layout.activity_list

    @Inject
    lateinit var lisePresenter: ListPresenter<D>

    override fun initPresenter()=lisePresenter
    /**
     * 分页参数
     */
    var pageIndex=1
    var pageSize=10
     var adapter= BaseItemAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initHelperView(pullto_layout)
        pullto_layout.background=activity takeDrawable R.color.white
        pullto_layout.setOnRefreshListener(this)
        pullable_rec.adapter=adapter
        adapter.enableAnimation(ScaleInAnimation(), true)
        /**
         * 是否启用懒加载，默认直接加载数据
         */
        if(arguments==null||!arguments.containsKey(ListFragment.LAYZ)){
            onRefresh()
        }
    }

    override fun lazyFetchData() {
        super.lazyFetchData()
        if(arguments!=null&&arguments.containsKey(ListFragment.LAYZ)){
            onRefresh()
        }
    }

    override fun onRefresh() {
        pageIndex=1
        presenter.loadData(pageIndex,pageSize)
    }

    override fun onLoadMore() {
        pageIndex++
        presenter.loadData(pageIndex,pageSize)
    }

    /**
     * 加载数据成功子类回调
     */
   override fun loadDataSuccess(data:ArrayList<D>){
        if(pageIndex==1){
            stopLoadingDialog()
            pullto_layout.refreshFinish()
            adapter.clearFoot()
            if(data.isEmpty()) {
                val emptyV=View.inflate(activity,R.layout.empty_view_bg,null)
                emptyV.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
                adapter.addFootView(emptyV)
            }
            adapter.setDataItems(data)
        }else{
            if(data.size<pageSize){
                pullto_layout.loadmoreFinish(PullToRefreshLayout.NO_DATA)
            }else{
                pullto_layout.loadmoreFinish(PullToRefreshLayout.SUCCEED)
            }
            adapter.addDataItems(data)
        }
        pullable_rec.setCanPullUp(data.size>=pageSize)
    }


    override fun loadDataFailed(msg:String){
        if(pageIndex==1){
            pullto_layout.refreshFinish()
        }else{
            pullto_layout.loadmoreFinish(PullToRefreshLayout.FAIL)
        }
        showErrorView(msg)
    }
}
