package com.ray.frame.view.dialog

import android.app.Activity
import android.graphics.Color
import android.view.View
import com.ray.frame.R
import com.ray.frame.presentation.utils.SizeUtils
import com.zyyoona7.lib.EasyPopup
import com.zyyoona7.lib.HorizontalGravity
import com.zyyoona7.lib.VerticalGravity

/**
 * Created by Ray on 2019/1/7.
 * 选择离我最近弹窗
 */
class DemoPopDialog(private val context: Activity) {

    //弹窗实体
    private lateinit var easyPopup: EasyPopup
    init {
        init()
    }

    /**
     * 初始化布局
     */
    private fun init() {
        easyPopup = EasyPopup(context)
                .setContentView<EasyPopup>(R.layout.a_line_layout)
                .setFocusAndOutsideEnable<EasyPopup>(true)
                .setBackgroundDimEnable<EasyPopup>(true)
                .setDimValue<EasyPopup>(0.4f)
                .setDimColor<EasyPopup>(Color.BLACK)
                .setWidth<EasyPopup>(SizeUtils.getScreenWidth(context))
                .createPopup()
    }

    /**
     * show弹窗
     */
    fun show(view:View) {
        easyPopup.showAtAnchorView(view, VerticalGravity.BELOW, HorizontalGravity.CENTER)
    }
}
