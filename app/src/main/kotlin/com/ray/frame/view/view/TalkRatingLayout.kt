package com.ray.frame.view.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.ray.frame.R
import com.ray.frame.presentation.kotlinx.extensions.inflateTo
import com.varunest.sparkbutton.SparkButton
import com.varunest.sparkbutton.SparkEventListener
import java.util.*

/**
 * Created by Ray on 2018/9/3.
 * 星级评级特效布局
 */
class TalkRatingLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {


    private val parent: LinearLayout
    private val stars = ArrayList<SparkButton>()
    var star = 3//默认选中3星

    init {
        val view = inflateTo(R.layout.choice_raingt_layout)
        parent = view.findViewById(R.id.spark_parent)
        initView()
    }

    private fun initView() {
        val size = parent.childCount
        for (i in 0 until size) {
            val item = parent.getChildAt(i) as SparkButton
            stars.add(item)
            item.isChecked = i<star
            item.setEventListener(StarEventListener(i))
        }
    }


    private inner class StarEventListener constructor(pos: Int) : SparkEventListener {
        private var pos = 0

        init {
            this.pos = pos
        }

        /**
         * 选中几颗星
         */
        private fun dealSeletor() {
            for (s in stars) {
                s.isChecked = false
            }
            for (i in 0 until star) {
                stars[i].isChecked = true
            }
        }

        override fun onEvent(button: ImageView, buttonState: Boolean) {
            star = pos + 1
            dealSeletor()
        }

        override fun onEventAnimationEnd(button: ImageView, buttonState: Boolean) {

        }

        override fun onEventAnimationStart(button: ImageView, buttonState: Boolean) {

        }
    }
}