package com.moodsmap.waterlogging.presentation.rxutil

import android.util.Log
import android.view.View
import android.widget.EditText
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 封装一些RxJava常用操作
 * Created by Ray on 2018/3/7.
 */

object RxFun {
    private val TAG = "RxFun"

    private fun getScheduler(vararg schedulers: Scheduler): Array<Scheduler?> {
        val s = arrayOfNulls<Scheduler>(2)
        when (schedulers.size) {
            0 -> {
                s[0] = Schedulers.io()
                s[1] = AndroidSchedulers.mainThread()
            }
            1 -> {
                s[0] = Schedulers.io()
                s[1] = schedulers[0]
            }
            2 -> {
                s[0] = schedulers[0]
                s[1] = schedulers[1]
            }
            else -> {
                s[0] = Schedulers.io()
                s[1] = AndroidSchedulers.mainThread()
            }
        }
        return s
    }

    /**
     * 无条件轮询 无限循环
     * @param period 间隔时间
     * @param callback 回调action
     * @param schedulers 依次传递订阅线程 执行线程
     */
    fun interval(period: Int, callback: RxInterface.intervalInterface1, vararg schedulers: Scheduler): Disposable {
        val s = getScheduler(*schedulers)
        return Observable.interval(period.toLong(), TimeUnit.SECONDS).subscribeOn(s[0]).observeOn(s[1]).subscribe { aLong ->
            Log.v(TAG, "interval 1\t" + aLong!!)
            callback.action(aLong)
        }
    }

    /**
     * 有条件轮询 满足条件终止
     * @param period 间隔时间
     * @param callback 停止条件  回调action
     * @param schedulers 依次传递订阅线程 执行线程
     */
    fun interval(period: Long, callback: RxInterface.intervalInterface2, vararg schedulers: Scheduler): Disposable {
        val s = getScheduler(*schedulers)
        return Observable.interval(period, TimeUnit.SECONDS).takeUntil { callback.isStop }.subscribeOn(s[0]).observeOn(s[1]).subscribe {
            Log.v(TAG, "interval 2\t$it")
            callback.action(it)
        }
    }

    /**
     * 延时任务
     * @param delayed 延迟时间
     * @param callback  回调action
     * @param schedulers 依次传递订阅线程 执行线程
     */
    fun delay(delayed: Long, callback: RxInterface.delayed, vararg schedulers: Scheduler): Disposable {
        val s = getScheduler(*schedulers)
        return Observable.timer(delayed, TimeUnit.SECONDS).subscribeOn(s[0]).observeOn(s[1]).subscribe { callback.action() }
    }

    /**
     * 联合判断，当满足所有条件时决定行为
     * @param callback
     * @param observables
     */
    @SafeVarargs
    fun <T> combineLatest(callback: RxInterface.combineLatest, vararg observables: Observable<T>): Disposable {
        return Observable.combineLatest(observables) { callback.result }.subscribe { callback.action(it) }
    }

    /**
     * 防抖功能
     * 单位为毫秒
     * @param view
     * @param time 防抖间隔
     * @param simple
     */
    fun clicks(view: View, time: Int, simple: RxInterface.simple): Disposable {
        return RxView.clicks(view).throttleFirst(time.toLong(), TimeUnit.MILLISECONDS).subscribe { simple.action() }
    }

    /**
     * 智能搜索优化
     * 防止短时间内连续搜索
     * 单位为毫秒
     * @param view
     * @param time
     * @param simple
     */
    fun autoSearch(view: EditText, time: Int, simple: RxInterface.simple): Disposable {
        return RxTextView.textChanges(view).debounce(time.toLong(), TimeUnit.MILLISECONDS).skip(1)//跳过初始值空字符
                .observeOn(AndroidSchedulers.mainThread()).subscribe { simple.action() }
    }

}
