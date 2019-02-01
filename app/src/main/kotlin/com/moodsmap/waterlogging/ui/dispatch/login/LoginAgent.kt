package com.moodsmap.waterlogging.ui.dispatch.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.moodsmap.waterlogging.App
import com.moodsmap.waterlogging.data.domain.entity.User
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.krealmextensions.deleteAll
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.start
import com.moodsmap.waterlogging.presentation.rxutil.RxBus
import com.moodsmap.waterlogging.presentation.rxutil.RxEvent
import util.UpdateAppUtils

/**
 * Created by Ray on 2018/12/30.
 * 全局登录代理类
 */
object  LoginAgent {

    /**
     * 启动登录界面
     */
    fun goLogin(activity: Context?=null){
        if(activity==null){
            App.instance.currentActivity?.start<LoginActivity>()
        }else {
            if(activity is Activity) {
                activity.start<LoginActivity>()
            }else goLoginAndClear()
        }
    }


    /**
     * 启动登录界面并结束之前的界面
     */
    fun goLoginAndClear(activity: Activity?=null){
        val intent = Intent(activity ?: App.instance as Context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        App.instance.startActivity(intent)
        App.instance.sendBroadcast(Intent(UpdateAppUtils.EXITACTION))
    }


    /**
     * 退出登录
     * 清除用户数据
     * 清除购物车数据
     */
    fun logout(activity: Activity) {
        User().deleteAll()
        RxBus.sBus.post(RxEvent.LOGOUT,"")
        User.instance = User()
        User.save()
        goLoginAndClear(activity)
    }
}