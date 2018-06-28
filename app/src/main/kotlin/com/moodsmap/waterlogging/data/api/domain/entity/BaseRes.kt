package com.moodsmap.waterlogging.data.api.domain.entity

/**
 * Created by Ray on 2017/11/6.
 */
data class BaseRes<T>(var status: Int, var message: String, var dataList: T)