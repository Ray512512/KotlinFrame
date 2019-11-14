package com.ray.frame.presentation.widget.work

import com.ray.frame.App
import com.ray.frame.R
import com.ray.frame.data.api.exception.ApiException
import com.ray.frame.data.repository.DataRepository
import com.ray.frame.di.module.CommonModule
import com.ray.frame.presentation.base_mvp.base.BaseActivity
import com.ray.frame.presentation.kotlinx.extensions.showToast
import com.ray.frame.presentation.kotlinx.extensions.unSafeLazy
import com.ray.frame.presentation.rxutil.RxInterface
import com.ray.frame.presentation.simple.SimpleSubscriber
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Ray on 2019/3/13.
 */
class NetWorkUtils {

    @Inject
    lateinit var api: DataRepository

    init {
        App.instance.applicationComponent.plus(CommonModule(App.instance)).inject(this)
    }

    companion object {
        //服务器获取的常量map
        val configMap:HashMap<Int,Int> by unSafeLazy {
            HashMap<Int,Int>()
        }
    }
    var isShowLoading = true
    fun <T> action(activity: BaseActivity<*, *>, f: Flowable<T>, simpleCallback: RxInterface.simpleR<Throwable?>? = null, func: (T) -> Unit) {
        if (isShowLoading)
            activity.showLoadingDialog()
        f.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(object : SimpleSubscriber<T>() {
            override fun onNext(t: T) {
                activity.stopLoadingDialog()
                func(t)
            }

            override fun onError(e: Throwable?) {
                super.onError(e)
                simpleCallback?.let {
                    it.action(e)
                    return
                }
                activity.stopLoadingDialog()
                if (!ApiException.dealThrowable(activity, e)) {
                    activity.showToast(R.string.ask_failed)
                }
            }
        })
    }

}