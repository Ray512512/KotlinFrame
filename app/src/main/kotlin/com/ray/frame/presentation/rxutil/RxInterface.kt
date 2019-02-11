package com.ray.frame.presentation.rxutil

/**
 * Created by Ray on 2018/3/7.
 * RX回调接口
 */

class RxInterface {

    interface simple {
        fun action()
    }

    interface simpleR<T>{
        fun action(t:T)
    }
    interface simple2 {
        fun action1()
        fun action2()
    }
    //无条件轮训
    interface intervalInterface1 {
        fun action(time: Long)
    }

    //延时执行
    interface delayed {
        fun action()
    }

    //有条件轮训
    interface intervalInterface2 {
        val isStop: Boolean
        fun action(time: Long)
    }

    //联合判断
    interface combineLatest {
        val result: Boolean
        fun action(b: Boolean)
    }

    interface reTryWhen {
        fun isRetry(throwable: Throwable): Boolean
    }
}
