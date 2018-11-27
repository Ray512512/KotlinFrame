package com.moodsmap.waterlogging.data.domain.entity.simple

import android.support.annotation.DrawableRes
import android.support.annotation.StringDef
import com.moodsmap.waterlogging.R
import java.io.Serializable

class LifeSuggestion : Serializable {

    companion object {
        const val 空气 = "空气"
        const val 舒适度 = "舒适度"
        const val 洗车 = "洗车"
        const val 穿衣 = "穿衣"
        const val 感冒 = "感冒"
        const val 运动 = "运动"
        const val 旅游 = "旅游"
        const val 紫外线 = "紫外线"
    }

    @StringDef(空气, 舒适度, 洗车, 穿衣, 感冒, 运动, 旅游, 紫外线)
    @Retention(AnnotationRetention.SOURCE)
    annotation class SuggestionType

    var title: String? = null
        set(@SuggestionType title) {
            field = title
            when (title) {
                空气 -> {
                    iconBackgroudColor = -0x806117
                }
                舒适度 -> {
                    iconBackgroudColor = -0x1661c4
                }
                洗车 -> {
                    iconBackgroudColor = -0x9d4e01
                }
                穿衣 -> {
                    iconBackgroudColor = -0x703aa1
                }
                感冒 -> {
                    iconBackgroudColor = -0x67e88
                }
                运动 -> {
                    iconBackgroudColor = -0x4c35a0
                }
                旅游 -> {
                    iconBackgroudColor = -0x293cb
                }
                紫外线 -> {
                    iconBackgroudColor = -0xf54d6
                }
                else -> {
                    iconBackgroudColor = -0x806117
                }
            }
        }
    var msg: String? = null
    @DrawableRes
    var icon: Int = 0
    var iconBackgroudColor: Int = 0
}