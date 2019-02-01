package com.moodsmap.waterlogging.data.domain.entity

import java.io.Serializable

/**
 * Created by Ray on 2019/1/8.
 */
data class UserCity constructor(var lng:Double=0.0,
                                var lat:Double=0.0,
                                var proviceName:String="",
                                var cityName:String="",
                                var district:String=""
):Serializable{

    companion object {
        /**
         * 默认定位城市
         */
         val DEFAULT_CITY=UserCity(30.551845,104.06555,"四川省","成都市","高新区")
    }
}