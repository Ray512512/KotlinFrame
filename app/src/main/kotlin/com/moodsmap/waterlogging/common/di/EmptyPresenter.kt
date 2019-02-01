package com.bd.travel.common.di

import javax.inject.Inject
import com.moodsmap.waterlogging.common.di.EmptyContract
import com.moodsmap.waterlogging.data.repository.DataRepository
import com.moodsmap.waterlogging.di.scope.PerActivity
import com.moodsmap.waterlogging.presentation.base_mvp.api.ApiPresenter


/**
 * 无需实现任何网络加载数据逻辑P层
 */
@PerActivity
 class EmptyPresenter @Inject constructor(private val dataRepository: DataRepository) : ApiPresenter<EmptyContract.View>(), EmptyContract.Presenter {}