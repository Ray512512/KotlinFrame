package com.moodsmap.waterlogging.presentation.utils.extensions

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.support.v4.content.ContextCompat

/**
 */
inline fun <reified T> Activity.start() {
    this.startActivity(Intent(this, T::class.java))
}

fun Activity.isPortrait() = this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

infix fun Activity.takeColor(colorId: Int) = ContextCompat.getColor(this, colorId)
