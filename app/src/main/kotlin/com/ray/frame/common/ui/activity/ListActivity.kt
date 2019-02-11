package com.ray.frame.common.ui.activity

import android.os.Bundle
import com.ray.frame.common.ui.contact.ListContract
import com.ray.frame.common.ui.presenter.ListPresenter
import com.ray.frame.R
import com.ray.frame.common.adapter.RecyleAdapter
import com.ray.frame.presentation.base_mvp.base.BaseActivity
import com.ray.frame.view.pulltorefreshview.PullToRefreshLayout
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.pull_rec.*
import javax.inject.Inject

/**
 * 父类实现加载数据等基础业务逻辑
 * 子类只需关心实际业务
 */
abstract class ListActivity<D>: BaseActivity<ListContract.View<D>, ListContract.Presenter<D>>(), PullToRefreshLayout.OnRefreshListener, ListContract.View<D> {

    @Inject
    lateinit var lisePresenter: ListPresenter<D>

    override fun initPresenter()=lisePresenter

    /**
     * 设置标题
     */
    abstract var titleStr:String
    /**
     * 分页参数
     */
    var pageIndex=1
    var pageSize=10
    abstract var adapter: RecyleAdapter<D>
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_list)
        super.onCreate(savedInstanceState)

        initHelperView(pullto_layout)
        list_title.setTitleText(titleStr)
        pullto_layout.setOnRefreshListener(this)
        pullable_rec.adapter=adapter
        onRefresh()

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
            if(data.isEmpty()){
                showEmptyView()
            }else{
                showDataView()
            }
            pullto_layout.refreshFinish()
            adapter.setAll(data)
        }else{
            if(data.size<pageSize){
                pullto_layout.loadmoreFinish(PullToRefreshLayout.NO_DATA)
            }else{
                pullto_layout.loadmoreFinish(PullToRefreshLayout.SUCCEED)
            }
            adapter.addAll(data)
        }
        if(data.size<pageSize){
            pullable_rec.setCanPullUp(false)
        }
    }
}
