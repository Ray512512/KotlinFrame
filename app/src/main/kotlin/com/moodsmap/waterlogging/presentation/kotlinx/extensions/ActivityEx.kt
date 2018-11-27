package com.moodsmap.waterlogging.presentation.kotlinx.extensions

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import java.io.Serializable

/**
 */
inline fun <reified T> Activity.start() {
    this.startActivity(Intent(this, T::class.java))
}

inline fun <reified T> Activity.start(startCode:Int,arg:Map<String,Any>) {
    this.startActivityForResult(getIntentEx<T>(arg),startCode)
}


inline fun <reified T> Activity.start(arg:Map<String,Any>) {
    this.startActivity(getIntentEx<T>(arg))
}

inline fun <reified T> Activity.getIntentEx(arg:Map<String,Any>):Intent{
    val intent=Intent(this, T::class.java)
    val bundle=Bundle()
    arg.forEach{
        bundle.putValue(it.key,it.value)
    }
    intent.putExtras(bundle)
    return intent
}

 fun Bundle.putValue(key:String,value:Any):Bundle{
    when (value) {
        is Int -> putInt(key, value)
        is Long -> putLong(key, value)
        is String -> putString(key, value)
        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        else -> throw UnsupportedOperationException("${value.javaClass.simpleName} type not supported yet!!!")
    }
     return this
}



inline fun <reified T> Activity.start2() {
    this.startActivity(Intent(this, T::class.java))
    this.finish()
}

fun Activity.isPortrait() = this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

infix fun Activity.takeColor(colorId: Int) = ContextCompat.getColor(this, colorId)

fun Activity.setResult(code:Int,arg:Map<String,Any>){
    val intent=Intent()
    val bundle=Bundle()
    arg.forEach{
        bundle.putValue(it.key,it.value)
    }
    intent.putExtras(bundle)
    setResult(code,intent)
}