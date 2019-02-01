package com.moodsmap.waterlogging.data.domain.entity.base

/**
 * Created by gong on 2017/11/6.
 */
data class BaseRes<T>(var code: String, var msg: String, var data: T){
    fun success(): Boolean {
        return code == "000"
    }
}