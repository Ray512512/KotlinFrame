package com.moodsmap.waterlogging.presentation.kotlinx.extensions

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.support.annotation.Px
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.moodsmap.waterlogging.presentation.rxutil.RxFun
import com.moodsmap.waterlogging.presentation.rxutil.RxInterface
import com.moodsmap.waterlogging.presentation.utils.AnimaUtil
import com.moodsmap.waterlogging.presentation.utils.SizeUtils
import com.moodsmap.waterlogging.presentation.utils.StatusBarUtils


/**
 */

fun TextView.leftIcon(drawableId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, drawableId), null, null, null)
}


fun TextView.rightIcon(drawableId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, drawableId), null)
}


fun TextView.rightIcon(resId:Int,padding:Int){
    val drawableLeft = ContextCompat.getDrawable(context,resId)
    setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null)
    compoundDrawablePadding = SizeUtils.dp2px(context, padding.toFloat())
}

fun EditText.string() = this.text.toString()

@SuppressLint("NewApi")
fun TextView.iconTint(colorId: Int) {
    MorAbove {
        compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorId))
    }
}

var View.scale: Float
    get() = Math.min(scaleX, scaleY)
    set(value) {
        scaleY = value
        scaleX = value
    }

fun View.addTopMargin(@Px marginInPx: Int) {
    (layoutParams as ViewGroup.MarginLayoutParams).topMargin = marginInPx
}

fun View.addBottomMargin(@Px marginInPx: Int) {
    (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = marginInPx
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.showWithAnim() {
    visibility = View.VISIBLE
    AnimaUtil.TranslateShowViewSelfYup(this)
//    startAnim(R.anim.fragment_in)
}

fun View.hideWithAnim() {
    AnimaUtil.TranslateGoneViewSelfYdown(this)
}



fun View.show(v:Boolean){
    visibility = if(v){ View.VISIBLE }else{ View.GONE }
}

fun View.show2(v:Boolean){
    visibility = if(v){ View.VISIBLE }else{ View.INVISIBLE }
}

fun View.invisible() {
    visibility = View.INVISIBLE
}


fun View.onClick(time:Long=500,function: () -> Unit) {
        RxFun.clicks(this,time,object : RxInterface.simple{
            override fun action() {
                function()
            }
        })
}

infix fun ViewGroup.inflate(layoutResId: Int): View =
        LayoutInflater.from(context).inflate(layoutResId, this, false)

fun ViewGroup.inflateTo(layoutResId: Int): View =
        LayoutInflater.from(context).inflate(layoutResId, this)

fun ImageView.tint(colorId: Int) {
    setColorFilter(context.takeColor(colorId), PorterDuff.Mode.SRC_IN)
}

operator fun ViewGroup.get(index: Int): View = getChildAt(index)

fun View.asSatusBarView(){
    StatusBarUtils.setStatusBarView(context,this)
}

fun View.startAnim(res:Int){
    AnimationUtils.loadAnimation(context, res)
}

fun ImageView.setTintColor(colorId:Int,drawableId: Int){
    val d=resources.getDrawable(drawableId)
    DrawableCompat.setTint(d,context.takeColor(colorId))
    setImageDrawable(d)
}
