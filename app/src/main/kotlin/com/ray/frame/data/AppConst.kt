package com.ray.frame.data

import com.ray.frame.App



/**
 * Created by Ray on 2017/11/22.
 */
object AppConst {

    val CACHE_DIR = App.instance.cacheDir.absolutePath

    /**
     * retrofit 上传参数
     */
    object MediaType {
        val TEXT_PLAIN = okhttp3.MediaType.parse("text/plain")
        val IMAGE = okhttp3.MediaType.parse("image/*")
        const val FILES_PARM = "files\"; filename=\""
    }

    /**
     * shareprences 键值
     */
    object SPKey {
        const val USER_CITY = "userCity"
        const val IS_FIRST_LAUNCH = "is_first_launch"//是否首次启动
        const val LAST_LOGIN_ACCOUNT = "lastLoginAccount"//上次登录用户名
        const val LAST_LOGIN_PSW = "lastLoginPsw"//上次登录密码
    }


    object TokenKey {
        const val TOKEN_KEY = "token_Key_Key"//本地生成token的键值
        const val TOKEN_MOUDEL = "token_moudel"
        const val PHONE_MOUDEL = "phone_moudel"
    }

    /**
     * 用户信息相关
     */
    object UserKey {
        const val NEED_LOGIN_TAG = "needLogin"
    }


    /**
     *intent传值相关key
     */
    object IntentKey {
        const val ID = "goodsId"
        const val D1 = "id1"
        const val D2 = "id2"
        const val DATA = "data"
        const val EXTRA_DATA = "extraData"
        const val STRING_DATA = "stringData"
        const val TYPE = "type"
        const val TAG = "tag"
        const val FROM = "from"
    }
}