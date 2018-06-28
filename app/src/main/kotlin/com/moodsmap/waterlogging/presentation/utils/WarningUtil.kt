package com.moodsmap.waterlogging.presentation.utils

import android.graphics.BitmapFactory
import com.amap.api.maps2d.model.BitmapDescriptor
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.BitmapDescriptorFactory.fromBitmap
import com.moodsmap.waterlogging.data.AppConst
import com.moodsmap.waterlogging.presentation.utils.extensions.log

/**
 * Created by gong on 2017/12/7.
 */

open class WarningUtil {
    private val warningLevel = arrayOf("红色预警", "橙色预警", "黄色预警", "蓝色预警")
    fun fromAsset(type: String, level: Int): BitmapDescriptor? {
        return try {
            log(getWarnIcon(type, level))
            val var1 = BitmapDescriptorFactory::class.java.getResourceAsStream("/assets/${getWarnIcon(type, level)}")
            val options = BitmapFactory.Options()
            options.inSampleSize = 2
            options.inJustDecodeBounds = false
            val var2 = BitmapFactory.decodeStream(var1, null, options)
            var1.close()
            fromBitmap(var2)
        } catch (var4: Throwable) {
            null
        }

    }

    fun getWarnIcon(type: String, level: Int): String? {
        if (level < 1 || level > 4) return null
        val index = AppConst.warn_type.indexOf(type)
        if (index < 0) {
            return "default_$level.png"
        }
        return "warning/${AppConst.warn_type_code[index]}_$level.png"
    }

    fun getWarningLevel(level: Int): String {
        if (level in 1..4) {
            return warningLevel[level - 1]
        }
        return "预警"
    }
}