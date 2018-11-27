package com.moodsmap.waterlogging.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.WindowManager
import android.widget.*
import com.moodsmap.waterlogging.R
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.*
import kotlinx.android.synthetic.main.dialog_item.*


/**
 * Created by gong on 2017/10/18.
 */

class MaterialDialog(context: Context) : Dialog(context, R.style.MaterialDialogSheet) {

    private val titleText by unSafeLazy {
        findViewById<TextView>(R.id.title)
    }
    private val btn_liner by unSafeLazy {
        findViewById<LinearLayout>(R.id.dialog_btn_liner)
    }
    private val messageText by unSafeLazy {
        findViewById<TextView>(R.id.message)
    }
    private val positiveButton by unSafeLazy {
        findViewById<Button>(R.id.positiveButton)
    }
    private val negativeButton by unSafeLazy {
        findViewById<Button>(R.id.negativeButton)
    }
    private val img by unSafeLazy {
        findViewById<ImageView>(R.id.dialog_img)
    }

    private var cancled=true
    var isAutoDissmiss=true
    private var curretnType:DialogType=DialogType.ERROR

    fun setIsAutoDissmiss(b:Boolean):MaterialDialog{
        isAutoDissmiss=b
        return this
    }
    enum class DialogType{
        ERROR,RIGHT,TEXT
    }
    init {
        setContentView(R.layout.dialog_item)
        setTranslucentStatus()
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        dialogContainer.onClick { if(cancled)hide() }
    }

    infix fun cancelable(flag: Boolean):MaterialDialog {
        cancled=flag
        setCancelable(flag)
        setCanceledOnTouchOutside(flag)
        return this
    }

    override fun onDetachedFromWindow() {
        super.dismiss()
        super.onDetachedFromWindow()
    }

    infix fun title(title: String): MaterialDialog {
        titleText.text = title
        if(!title.isEmpty()){
            titleText.visibility=View.VISIBLE
            dialog_img.visibility=View.GONE
        }
        return this
    }

    infix fun type(type: DialogType): MaterialDialog {
        curretnType=type
        img.show()
        when(type){
            DialogType.ERROR->{
                img.setImageResource(R.mipmap.ic_error)
                img.onClick {
                    dismiss()
                }
            }
            DialogType.RIGHT->{
                img.setImageResource(R.mipmap.ic_right)
            }
            DialogType.TEXT->{
                img.hide()
            }
        }
        return this
    }

    infix fun message(message: String): MaterialDialog {
        messageText.text = message
        return this
    }

    fun addPositiveButton(text: String, action: MaterialDialog.() -> Unit): MaterialDialog {
        if(!text.isEmpty()){
            btn_liner.visibility=View.VISIBLE
            positiveButton.visibility=View.VISIBLE
            img.visibility=View.GONE
        }
        positiveButton.text = text.toUpperCase()
        positiveButton.onClick {
            action()
        }
        return this
    }


    fun addNegativeButton(text: String, action: MaterialDialog.() -> Unit): MaterialDialog {
        val view = findViewById<View>(R.id.container)
        val param=FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT)
        param.gravity=Gravity.CENTER
        view.layoutParams=param
        if(!text.isEmpty()){
            btn_liner.visibility=View.VISIBLE
            negativeButton.visibility=View.VISIBLE
        }
            negativeButton.visibility=View.VISIBLE
        negativeButton.text = text.toUpperCase()
        negativeButton.onClick {
            action()
        }
        return this
    }

    override fun show() {
        super.show()
        delay(1000){
            if(curretnType==DialogType.RIGHT&&isShowing&&isAutoDissmiss){
                dismiss()
            }
        }
    }

    override fun hide() {
        this.dismiss()
    }

    private fun ViewPropertyAnimator.scale(scale: Float): ViewPropertyAnimator {
        scaleX(scale)
        scaleY(scale)
        return this
    }

    private fun setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            val window = window
            window!!.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else {//4.4 全透明状态栏
            window!!.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

}