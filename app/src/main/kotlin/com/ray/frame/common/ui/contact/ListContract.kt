package com.ray.frame.common.ui.contact

import com.ray.frame.data.domain.entity.base.BaseListDataRes
import com.ray.frame.data.repository.DataRepository
import com.ray.frame.presentation.base_mvp.api.ApiContract
import io.reactivex.Flowable

/**
 * Created by Ray on 2019/1/22.
 * 列表界面共用连接器
 */
interface ListContract{
    interface View<T> : ApiContract.View {
        /**
         * 查询数据成功
         */
        fun loadDataSuccess(data:ArrayList<T>)
        /**
         * 设置调用方法
         */
        fun getDataRepositoryFun(): Flowable<BaseListDataRes<T>>
    }

    interface Presenter<T> : ApiContract.Presenter<View<T>> {
        /**
         * 查询数据
         */
        fun loadData(pageIndex:Int,pageSize:Int)

        /**
         * 获取api仓库
         */
        fun getListDataRepository(): DataRepository
    }
}