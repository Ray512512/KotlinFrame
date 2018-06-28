package com.moodsmap.waterlogging.presentation.utils.delegates

import android.support.v4.app.Fragment
import com.moodsmap.waterlogging.presentation.utils.extensions.isNull
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 */


inline fun <reified VALUE> Fragment.args() = object : ReadOnlyProperty<Fragment, VALUE> {

    private var value: VALUE? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VALUE {
        if (value.isNull) {
            value = arguments!![property.name] as VALUE
        }
        return value!!
    }
}


