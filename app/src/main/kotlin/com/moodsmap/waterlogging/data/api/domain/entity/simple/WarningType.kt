package com.moodsmap.waterlogging.data.api.domain.entity.simple

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by gong on 2017/12/14.
 */
open class WarningType() : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""

    constructor(id: Int, name: String) : this() {
        this.id = id
        this.name = name
    }


    fun getDataList(): List<WarningType> {
        val data = ArrayList<WarningType>()
        data.add(WarningType(1, "大风"))
        data.add(WarningType(2, "大雾"))
        data.add(WarningType(3, "台风"))
        data.add(WarningType(4, "寒潮"))
        data.add(WarningType(5, "雷电"))
        data.add(WarningType(6, "冰雹"))
        data.add(WarningType(7, "暴雨"))
        data.add(WarningType(8, "高温"))
        data.add(WarningType(9, "干旱"))
        data.add(WarningType(10, "低温"))
        data.add(WarningType(11, "冷冻"))
        data.add(WarningType(12, "结冰"))
        data.add(WarningType(13, "沙尘"))
        data.add(WarningType(14, "雾霾"))
        data.add(WarningType(15, "火险"))
        data.add(WarningType(16, "污染"))
        data.add(WarningType(17, "雪灾"))
        data.add(WarningType(18, "大雪"))
        return data
    }
}