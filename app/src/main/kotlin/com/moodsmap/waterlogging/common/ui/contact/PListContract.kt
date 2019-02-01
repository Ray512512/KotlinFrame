package com.bd.travel.common.ui.contact

import com.moodsmap.waterlogging.data.domain.entity.base.BaseListDataRes
import com.moodsmap.waterlogging.data.domain.entity.base.ListP
import com.moodsmap.waterlogging.data.repository.DataRepository
import com.moodsmap.waterlogging.presentation.base_mvp.api.ApiContract
import io.reactivex.Flowable

/**
 * Created by Ray on 2019/1/22.
 * 列表界面共用连接器
 */
interface PListContract{
    interface View<T> : ApiContract.View {
        /**
         * 查询列表数据成功
         */
        fun loadDataSuccess(data:ArrayList<T>)

        /**
         * 查询完整数据
         */
        fun loadData(data: ListP<T>)
        /**
         * 设置调用方法
         */
        fun getDataRepositoryFun2(): Flowable<ListP<T>>?

        /**
         * 设置调用方法
         */
        fun getDataRepositoryFun(): Flowable<BaseListDataRes<T>>?
    }

    interface Presenter<T> : ApiContract.Presenter<View<T>> {
        /**
         * 查询数据
         */
        fun loadData(pageIndex:Int,pageSize:Int,key:String)

        /**
         * 获取api仓库
         */
        fun getListDataRepository(): DataRepository
    }
}