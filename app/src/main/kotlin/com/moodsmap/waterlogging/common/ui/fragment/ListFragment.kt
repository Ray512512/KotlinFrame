package com.bd.travel.common.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import com.bd.travel.common.ui.contact.PListContract
import com.moodsmap.waterlogging.common.ui.presenter.PListPresenter
import com.moodsmap.waterlogging.R
import com.moodsmap.waterlogging.common.adapter.RecyleAdapter
import com.moodsmap.waterlogging.data.domain.entity.base.BaseListDataRes
import com.moodsmap.waterlogging.data.domain.entity.base.ListP
import com.moodsmap.waterlogging.presentation.base_mvp.base.BaseFragment
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.hide
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.show
import com.moodsmap.waterlogging.view.pulltorefreshview.PullToRefreshLayout
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.pull_rec.*
import kotlinx.android.synthetic.main.view_empty.*
import javax.inject.Inject

/**
 * 父类实现加载数据等基础业务逻辑
 * 子类只需关心实际业务
 */
abstract class ListFragment<D>: BaseFragment<PListContract.View<D>, PListContract.Presenter<D>>(), PullToRefreshLayout.OnRefreshListener,PListContract.View<D> {

    override val layoutResId = R.layout.pull_rec_empty

    @Inject
    lateinit var lisePresenter: PListPresenter<D>

    override fun initPresenter()=lisePresenter

    /**
     * 分页参数
     */
    var pageIndex=1
    var pageSize=10
    abstract var adapter: RecyleAdapter<D>

    //数据源key
     var dataKey:String=""

    companion object {
        const val KEY="dataKey"
        //懒加载
        const val LAYZ="lazy"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments.containsKey(KEY)){
            dataKey=arguments.getString(KEY)
        }
        pullto_layout.setOnRefreshListener(this)
        pullable_rec.adapter=adapter
        /**
         * 是否启用懒加载，默认直接加载数据
         */
        if(!arguments.containsKey(LAYZ)){
            onRefresh()
        }
    }

    override fun lazyFetchData() {
        super.lazyFetchData()
        if(arguments.containsKey(LAYZ)){
            onRefresh()
        }
    }

    override fun onRefresh() {
        pageIndex=1
        presenter.loadData(pageIndex,pageSize,dataKey)
    }

    override fun onLoadMore() {
        pageIndex++
        presenter.loadData(pageIndex,pageSize,dataKey)
    }

    /**
     * 加载数据成功子类回调
     */
   override fun loadDataSuccess(data:ArrayList<D>){
        if(pageIndex==1){
            if(data.isEmpty()){
                empty_root.show()
            }else{
                empty_root.hide()
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

    override fun loadData(data: ListP<D>) {
        frameCallback?.getDataSuccess(pageIndex,data)
    }

    interface DataCallback{
        fun getDataSuccess(pageIndex:Int,data:Any)
    }

    /**
     * 数据回调
     */
    private  var frameCallback: DataCallback?=null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            if(context is DataCallback){
                frameCallback=context
            }
        }catch (e:ClassCastException){
            e.printStackTrace()
        }
    }

    override fun getDataRepositoryFun2(): Flowable<ListP<D>>? {
        return null
    }

    override fun getDataRepositoryFun(): Flowable<BaseListDataRes<D>>? {
        return null
    }
}
