package com.moodsmap.waterlogging.presentation.utils

import android.content.Context

/**
 * Created by gong on 2017/12/15.
 */
fun dp2px(ctx: Context, dp: Int): Int {
    val sDensity = ctx.resources.displayMetrics.density
    return (dp * sDensity + 0.5f).toInt()
}

fun px2dp(ctx: Context, px: Int): Int {
    val sDensity = ctx.resources.displayMetrics.density
    return (px / sDensity + 0.5f).toInt()
}