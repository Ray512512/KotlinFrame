package com.ray.frame.data.api.exception

import android.content.Context
import com.ray.frame.presentation.base_mvp.base.BaseActivity
import com.ray.frame.presentation.kotlinx.extensions.showToast
import java.lang.RuntimeException

/**
 * Created by Ray on 2017/10/23.
 */
class ApiException(var code: String, var msg: String?) : RuntimeException(){
    companion object {
        const val ERROR_CODE_TOKEN_INVALID="100001"

        /**
         * 处理公共api异常
         */
        fun dealThrowable(context: Context, t:Throwable?):Boolean{
            t?.let {
                if(t is ApiException){
                    t.msg?.let {
                        context.showToast(it)
                        return true
                    }
                }
            }
            return false
        }

        /**
         * 处理公共api异常
         */
        fun dealThrowable(context: BaseActivity<*, *>, t:Throwable?):Boolean{
            t?.let {
                if(t is ApiException){
                    t.msg?.let {
                        context.showDialog(it)
                        return true
                    }
                }
            }
            return false
        }
    }
}