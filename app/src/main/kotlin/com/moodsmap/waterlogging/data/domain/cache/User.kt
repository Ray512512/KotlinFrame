package com.moodsmap.waterlogging.data.domain.cache

import android.text.TextUtils
import com.moodsmap.waterlogging.data.AppConst
import com.moodsmap.waterlogging.presentation.utils.Utils
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectStreamException
import java.io.Serializable


/**
 * Created by Ray on 2018/3/21.
 */
class User : Serializable, Cloneable {

    companion object {
        private const val TAG = "User"
        var instance: User

        init {
            var obj = Utils.restoreObject(AppConst.CACHE_DIR + TAG)
            if (obj == null) {
                obj = User()
                Utils.saveObject(AppConst.CACHE_DIR + TAG, obj)
            }
            instance = obj as User
        }
    }



    private var code: String? = null
    private var phone: String? = null
    private var name: String? = null
    private var token: String? = null


    fun save(user: User) {
        code = user.code
        phone = user.phone
        name = user.name
        token = user.token
        Utils.saveObject(AppConst.CACHE_DIR + TAG, this)
    }

    @Throws(ObjectStreamException::class, CloneNotSupportedException::class)
    fun readResolve(): User? {
        instance = this.clone() as User
        return instance
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(ois: ObjectInputStream) {
        ois.defaultReadObject()
    }

    @Throws(CloneNotSupportedException::class)
    fun Clone(): Any {
        return super.clone()
    }

    fun isLogin(): Boolean {
        return !TextUtils.isEmpty(token)
    }

}