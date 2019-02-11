package com.ray.frame.data.domain.entity.base

/**
 * Created by Ray on 2019/1/8.
 * 基础列表数据返回数据类
 */

data class BaseListDataRes<T>(
    val pageTotal: Int,
    val startRow: Int,
    val pageNo: Int,
    val pageSize: Int,
    val totalCount: Int,
    val list: ArrayList<T>
){
    constructor() : this(0,0,0,0,0,ArrayList())
}


