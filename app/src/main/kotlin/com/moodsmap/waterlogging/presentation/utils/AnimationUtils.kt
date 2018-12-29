package com.moodsmap.waterlogging.presentation.utils

import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.View
import android.view.animation.*

/**
 */

object AnimationUtils {

    val LINEAR_INTERPOLATOR: Interpolator = LinearInterpolator()
    val FAST_OUT_SLOW_IN_INTERPOLATOR: Interpolator = FastOutSlowInInterpolator()
    val FAST_OUT_LINEAR_IN_INTERPOLATOR: Interpolator = FastOutLinearInInterpolator()

    fun lerp(startValue: Int, endValue: Int, fraction: Float): Int {
        return startValue + Math.round(fraction * (endValue - startValue))
    }

    fun startShakeByViewAnim(view: View, shakeDegrees:Float, duration:Long) {
        //从左向右
        val rotateAnim =  RotateAnimation(-shakeDegrees, shakeDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.duration = duration / 10
        rotateAnim.repeatMode = Animation.REVERSE
        rotateAnim.repeatCount = 5
        val smallAnimationSet =  AnimationSet(false)
        smallAnimationSet.addAnimation(rotateAnim)
        view.startAnimation(smallAnimationSet)
    }
}