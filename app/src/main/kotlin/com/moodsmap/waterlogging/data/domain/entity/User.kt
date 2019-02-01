package com.moodsmap.waterlogging.data.domain.entity

import android.content.Context
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.krealmextensions.deleteAll
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.krealmextensions.queryFirst
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.krealmextensions.save
import com.moodsmap.waterlogging.ui.dispatch.login.LoginAgent
import io.realm.RealmObject

/**
 * Created by Ray on 2018/12/30.
 * 用户个人信息类
 */
open class User constructor(
        var token: String=""
): RealmObject(){
    companion object {
        var instance = User()
            get() {
                if(field.token.isEmpty()){
                    try {
                        User().queryFirst()?.let {
                            field=it
                        }
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
                return field
            }

        /**
         * 保存 先删除本地重新保存
         */
        fun save(){
            delete()
            instance.save()
        }

        /**
         * 保存token
         */
        fun save(token:String){
            delete()
            instance.token=token
            instance.save()
        }

        /**
         * 删除本地
         */
        fun delete(){
            instance.deleteAll()
        }

        /**
         * 是否能够登录
         */
        fun needLogin():Boolean{
            return  instance.token.isEmpty()
        }

        /**
         * 检查登录状态
         */
        fun checkLogin(activity: Context):Boolean{
            if(User.needLogin()){
                LoginAgent.goLogin(activity)
                return false
            }
            return true
        }


    }

}