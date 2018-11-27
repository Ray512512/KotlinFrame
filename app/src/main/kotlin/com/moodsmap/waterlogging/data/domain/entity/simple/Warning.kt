package com.moodsmap.waterlogging.data.domain.entity.simple

import io.realm.RealmObject

/**
 * Created by Ray on 2017/11/21.
 */
open class Warning : RealmObject() {
//        val ID: Int, //1
//        val Issuetime: Long, //1511250300000
//        val Lon: Double, //110.88033
//        val Lat: Double, //34.51687
//        val Warntype: String, //大风
//        val Warnlevel: Int, //蓝黄橙红  对应 IV-III-II-I
//        val Warncontent: String, //灵宝市气象局发布大风蓝色预警
//        val Ifrelieve: Int //0

    var ID: Int = 0 //1
    var Issuetime: Long = 0L //1511250300000
    var Lon: Double = 0.0 //110.88033
    var Lat: Double = 0.0 //34.51687
    var Warntype: String = "" //大风
    var Warnlevel: Int = 0 //蓝黄橙红  对应 IV-III-II-I
    var Warncontent: String = "" //灵宝市气象局发布大风蓝色预警
    var Ifrelieve: Int = 0 //0

    override fun toString(): String {
        return "Warning(ID=$ID, Issuetime=$Issuetime, Lon=$Lon, Lat=$Lat, Warntype='$Warntype', Warnlevel=$Warnlevel, Warncontent='$Warncontent', Ifrelieve=$Ifrelieve)"
    }

}
