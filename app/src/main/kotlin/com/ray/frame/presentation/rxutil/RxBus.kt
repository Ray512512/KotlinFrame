package com.ray.frame.presentation.rxutil

import com.hwangjr.rxbus.Bus
import com.hwangjr.rxbus.thread.ThreadEnforcer
import javax.inject.Singleton

@Singleton
class RxBus  constructor(){
    companion object {
        var sBus: Bus = Bus()  //主线程post
        var ioBus: Bus = Bus(ThreadEnforcer.ANY) //子线程post

        infix fun register(o: Any) {
            sBus.register(o)
            ioBus.register(o)
        }

        infix fun unregister(o: Any) {
            sBus.unregister(o)
            ioBus.unregister(o)
        }


    }

}