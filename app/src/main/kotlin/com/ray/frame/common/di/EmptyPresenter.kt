package com.bd.travel.common.di

import javax.inject.Inject
import com.ray.frame.common.di.EmptyContract
import com.ray.frame.data.repository.DataRepository
import com.ray.frame.di.scope.PerActivity
import com.ray.frame.presentation.base_mvp.api.ApiPresenter


/**
 * 无需实现任何网络加载数据逻辑P层
 */
@PerActivity
 class EmptyPresenter @Inject constructor(private val dataRepository: DataRepository) : ApiPresenter<EmptyContract.View>(), EmptyContract.Presenter {}