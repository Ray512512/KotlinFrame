package com.ray.frame.presentation.kotlinx.coroutine

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import com.ray.frame.presentation.kotlinx.extensions.loge
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

internal class CoroutineLifecycleListener(private val deferred: Deferred<*>) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancelCoroutine() {
        if (!deferred.isCancelled) {
            deferred.cancel()
        }
    }
}

internal val Background = newFixedThreadPoolContext(Runtime.getRuntime().availableProcessors() * 2, "Loader")

/**
 * Creates a lazily started coroutine that runs <code>loader()</code>.
 * The coroutine is automatically cancelled using the CoroutineLifecycleListener.
 */
fun <T> LifecycleOwner.load(loader: suspend () -> T): Deferred<T> {
    val deferred = async(context = Background, start = CoroutineStart.LAZY) {
        loader()
    }
    lifecycle.addObserver(CoroutineLifecycleListener(deferred))
    return deferred
}
/*原始协程实现异步
job = async (Background) {
    //模拟长耗时
    delay(6000)
    var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
    launch(UI) {
        image_view.setImageBitmap(bitmap)
        dialog?.dismiss()
    }
}*/
/**
 * Extension function on <code>Deferred<T><code> that creates a launches a coroutine which
 * will call <code>await()</code> and pass the returned value to <code>block()</code>.
 */
infix fun <T> Deferred<T>.then(block: suspend (T) -> Unit): Job {
    return launch(UI) {
        try {
            block(this@then.await())
        } catch (e: Exception) {
            loge(e) { "Exception in then()!" }
            throw e
        }
    }
}
