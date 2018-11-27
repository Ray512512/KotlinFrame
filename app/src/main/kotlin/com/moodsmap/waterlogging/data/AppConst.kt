package com.moodsmap.waterlogging.data

import com.moodsmap.waterlogging.App



/**
 * Created by Ray on 2017/11/22.
 */
object AppConst {

    val CACHE_DIR = App.instance.cacheDir.absolutePath

    object MediaType {
        val TEXT_PLAIN = okhttp3.MediaType.parse("text/plain")
        val IMAGE = okhttp3.MediaType.parse("image/*")
        const val FILES_PARM = "files\"; filename=\""
    }

    object SPKey {
        const val USER_CITY = "userCity"
        const val IS_FIRST_LAUNCH = "is_first_launch"//是否首次启动
    }


    object TokenKey {
        const val TOKEN_KEY = "token_Key_Key"//本地生成token的键值
        const val TOKEN_MOUDEL = "token_moudel"
        const val PHONE_MOUDEL = "phone_moudel"
    }
}