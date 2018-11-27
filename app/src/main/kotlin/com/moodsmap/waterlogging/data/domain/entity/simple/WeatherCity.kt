package com.moodsmap.waterlogging.data.domain.entity.simple

import io.realm.RealmObject

open class WeatherCity() : RealmObject() {

    constructor(cityName: String?, cityId: String?) : this() {
        this.cityName = cityName
        this.cityId = cityId
    }
    var cityName: String? = null
    var cityId: String? = null
    var cityIndex: Int = 0
    var weatherCode: String? = null
    var weatherText: String? = null
    var weatherTemp: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeatherCity

        if (cityName != other.cityName) return false

        return true
    }

    override fun hashCode(): Int {
        return cityName?.hashCode() ?: 0
    }


}
