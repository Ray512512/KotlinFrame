package com.moodsmap.waterlogging.presentation.utils.extensions

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.Spanned
import android.widget.Toast
import com.moodsmap.waterlogging.App
import com.moodsmap.waterlogging.data.api.exception.ApiException
import com.moodsmap.waterlogging.data.api.domain.entity.BaseRes
import com.moodsmap.waterlogging.presentation.utils.Experimental
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer


infix fun Context.takeColor(colorId: Int) = ContextCompat.getColor(this, colorId)

operator fun Context.get(resId: Int): String = getString(resId)

operator fun Fragment.get(resId: Int): String = getString(resId)

@Experimental
fun Int.text(): String = App.instance.getString(this) //What do you think about it?

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

inline fun delay(milliseconds: Long, crossinline action: () -> Unit) {
    Handler().postDelayed({
        action()
    }, milliseconds)
}

@Deprecated("Use emptyString instead", ReplaceWith("emptyString"), level = DeprecationLevel.WARNING)
fun emptyString() = ""

val emptyString = ""

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
inline fun LorAbove(body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        body()
    }
}

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
inline fun MorAbove(body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        body()
    }
}

@TargetApi(Build.VERSION_CODES.N)
inline fun NorAbove(body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        body()
    }
}

@SuppressLint("NewApi")
fun String.toHtml(): Spanned {
    NorAbove {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    }
    return Html.fromHtml(this)
}

fun Int.toPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this * density).toInt()
}

fun <T> unSafeLazy(initializer: () -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        initializer()
    }
}

fun Int.isZero(): Boolean = this == 0

inline fun <F, S> doubleWith(first: F, second: S, runWith: F.(S) -> Unit) {
    first.runWith(second)
}

val Any?.isNull: Boolean
    get() = this == null

//        upstream.flatMap(object :Function<in BaseRes<T>!,out Publisher<T>> {
//            fun apply(result: BaseRes<T>): Publisher<T> {
//                return if (result.code ==1) {
//                    createData(result.data)
//                } else {
//                    Flowable.error(Throwable(result.message))
//                }
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> handleResult(): FlowableTransformer<BaseRes<T>, T> {
    return FlowableTransformer { upstream ->
        upstream.flatMap { result ->
            if (result.status == 999) {
                createData(result.dataList)
            } else {
                Flowable.error(ApiException(result.status, result.message))
            }
        }
    }

}

private fun <T> createData(data: T): Flowable<T> {
    return Flowable.create({ emitter ->
        try {
            emitter.onNext(data)
            emitter.onComplete()
        } catch (e: Exception) {
            emitter.onError(e)
        }
    }, BackpressureStrategy.BUFFER)
}

fun getStatusBarHeight(): Int {
    var result = 0
    val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = Resources.getSystem().getDimensionPixelSize(resourceId)
    }
    return result
}

fun Long.formatTime(): String {
    var length = this
    var res = ""
    var day = 0
    var hour = 0
    var minute = 0
    val minuteC = 60
    val hourC = 60 * 60
    val dayC = 60 * 60 * 24
    if (length > dayC) {
        day = (length % dayC).toInt()
        length -= day * dayC
    }
    if (length > hourC) {
        hour = (length % hourC).toInt()
        length -= hour * hourC
    }
    if (length > minuteC) {
        minute = (length % minuteC).toInt()
        length -= minute * hourC
    }

    if (day > 0) {
        res += "${day}天"
    }
    if (hour > 0) {
        res += "${hour}时"
    }
    if (minute > 0) {
        res += "${minute}分"
    }
    return res
}
