package com.moodsmap.waterlogging.presentation.utils.extensions

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import java.io.Serializable

/**
 */
inline fun <reified T> Fragment.start() {
    this.startActivity(Intent(context, T::class.java))
}

fun Fragment.withArgument(key: String, value: Any) {
    val args = Bundle()
    when (value) {
        is Int -> args.putInt(key, value)
        is Long -> args.putLong(key, value)
        is String -> args.putString(key, value)
        is Parcelable -> args.putParcelable(key, value)
        is Serializable -> args.putSerializable(key, value)
        else -> throw UnsupportedOperationException("${value.javaClass.simpleName} type not supported yet!!!")
    }
    arguments = args
}

inline infix fun <reified T> Fragment.extraWithKey(key: String): T {
    val value: Any = arguments!![key]
    return value as T
}

fun Fragment.isPortrait() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

infix fun Fragment.takeColor(colorId: Int) = context?.apply { ContextCompat.getColor(this, colorId) }
