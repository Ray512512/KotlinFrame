package com.moodsmap.waterlogging.data.domain.entity.base

 interface ListP<T> {

     fun getListD(key:String):BaseListDataRes<T>

}