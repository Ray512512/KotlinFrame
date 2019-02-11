package com.ray.frame.view.edittext

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.ray.frame.R
import com.ray.frame.presentation.kotlinx.extensions.*
import com.ray.frame.presentation.simple.SimpleTextWatcher
import com.ray.frame.presentation.utils.AnimationUtils
import com.ray.frame.presentation.utils.ValidatorUtil
import com.ray.frame.presentation.utils.ViewUtils
import com.ray.frame.view.edittext.floatinglabeledittext.FloatingLabelEditText
import java.util.*

/**
 * Created by Ray on 2018/7/18.
 */
class MyEditText (context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    init {
        init(attrs)
    }

    companion object {
        const val ID_CARD_INPUT="1234567890xX"
        const val INVITE_CODE_INPUT="qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789"
        const val PASSWORD_INPUT="qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789"
    }
    enum class EditType{
        phone,psw,text,sex,realname,idcard,
        indate//有效期
        ,invitecode
    }
    var type= EditType.phone
    var isVisibleImg=false//是否是明文密码

    lateinit var et : FloatingLabelEditText
    private lateinit var tv_failed :TextView
    lateinit var img_delete :ImageView
    lateinit var img_show :ImageView
    private lateinit var view_line :View

    var text=""
    var isErrorStatus=false//当前是否处于错误状态  （继续处于错误状态无法进行提交操作）
    var btn:Button? =null //需做可点击控制的按钮
    var other_Et:EditText?=null //需进行联合判断的Edittext
    var other_MyEt: MyEditText?=null //需进行联合判断的Edittext
    private lateinit var datePickerDialog:DatePickerDialog
    private var defaultTopHint=0
    @SuppressLint("CustomViewStyleable")
    private fun init(attrs: AttributeSet?) {
        val view =inflateTo(R.layout.my_edittext_layout)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MyEdit, 0, 0)
        try {
            val t = ta.getInt(R.styleable.MyEdit_e_type, EditType.phone.ordinal)
            val s = ta.getString(R.styleable.MyEdit_e_text)
            s?.let { text=it }
            for (position in EditType.values()) {
                if (t == position.ordinal) {
                    type = position
                    break
                }
            }
            text=""
            setUpView(view)
        } finally {
            ta.recycle()
        }
    }

    fun setMyText(s:String){
        text=s
        et.setText(s)
    }

     fun setHint(s:String){
        et.label=s
    }
    fun setHint(s:Int){
        et.label= context[s]
    }

    fun dealErrorTextToNormal(text:Int=defaultTopHint){
//        defaultTopHint=text
//        tv_failed.setText(text)
//        tv_failed.setTextColor(context takeColor R.color.text_3)
////        tv_failed.visibility=View.VISIBLE
//        tv_failed.show(!this.text.isEmpty())
    }

    private fun dealErrorTextToError(){
        tv_failed.setTextColor(context takeColor R.color.mainColor)
        tv_failed.visibility=View.VISIBLE
        et.showmErrorStatus(true)
    }

    @SuppressLint("SetTextI18n")
    fun initDate(mYear:Int=Calendar.getInstance().get(Calendar.YEAR), mMonth:Int=Calendar.getInstance().get(Calendar.MONTH), mDay:Int=Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
        if(type!= EditType.indate)return
        et.setText(mYear.toString() + "-" + (mMonth + 1) + "-" + mDay)
        datePickerDialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener {
            _, year, month, dayOfMonth ->
            et.setText(year.toString() + "-" + (month + 1) + "-" + dayOfMonth)
        }, mYear, mMonth, mDay)
//        datePickerDialog.datePicker.maxDate = Date().time
    }
    fun setImgVisib(b:Boolean){
        if(b){
            img_delete.visibility=View.VISIBLE
        }else{
            img_delete.visibility=View.GONE
        }
    }
    private fun setUpView(view:View){
        et=view.findViewById(R.id.my_et_et)
        tv_failed=view.findViewById(R.id.tv_failed)
        img_delete=view.findViewById(R.id.img_pws_delete)
        img_show=view.findViewById(R.id.img_pws_show)
        view_line=view.findViewById(R.id.pws_line)
        et.setText(text)

        img_delete.onClick {
            et.setText("")
        }
        when(type){
            EditType.phone ->{
                setHint(R.string.hint_input_account)
                et.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(11))
                et.inputType = InputType.TYPE_CLASS_NUMBER
            }
            EditType.psw ->{
                setHint(R.string.hint_input_psw)
                et.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(20))
                et.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                img_show.setImageResource(R.mipmap.ic_show)
//                et.keyListener = DigitsKeyListener.getInstance(PASSWORD_INPUT)
            }
            EditType.text ->{
                setHint(R.string.nick_hint)
                et.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(20))
                et.inputType = InputType.TYPE_CLASS_TEXT
            }
            EditType.sex ->{
                img_delete.onClick {}
                ViewUtils.EditloseFocus(context,et)
                img_delete.setImageResource(R.mipmap.ic_red_choice)
                et.onClick {
                    checkBtnEnable()
                    img_delete.visibility=View.VISIBLE
                    other_MyEt?.let { it.img_delete.visibility=View.GONE }
                }
                view_line.background=resources.getDrawable(R.color.text_c)
            }
            EditType.realname ->{
                et.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(20))
                et.inputType = InputType.TYPE_CLASS_TEXT
                dealErrorTextToNormal(R.string.realname)
                setHint(R.string.name_hint)
                view_line.background=resources.getDrawable(R.color.text_c)
            }
            EditType.idcard ->{
                et.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(18))
                et.inputType = InputType.TYPE_CLASS_TEXT
                et.keyListener = DigitsKeyListener.getInstance(ID_CARD_INPUT)
                dealErrorTextToNormal(R.string.idcard_num)
                setHint(R.string.idcard_hint)
                view_line.background=resources.getDrawable(R.color.text_c)
            }
            EditType.indate ->{
                view_line.background=ContextCompat.getDrawable(context,R.color.text_c)
                ViewUtils.EditloseFocus(context,et)
                dealErrorTextToNormal(R.string.card_indate)
                setHint(R.string.indate_hint)
                initDate()
                et.onClick {
                    datePickerDialog.show()
                }
            }
            EditType.invitecode ->{
                setHint(R.string.hint_invite)
                et.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(6))
                et.inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
                et.keyListener = DigitsKeyListener.getInstance(INVITE_CODE_INPUT)
            }
        }
        et.setOnFocusChangeListener { _, hasFocus ->
            if(!isVisibleImg)
            dealEditImg()
            if(hasFocus){
                if(type== EditType.realname ||type== EditType.idcard){
                    if(isErrorStatus){
                        dealErrorTextToNormal()
                    }
                }else {
                    errorViewHide()
                }
                view_line.background=resources.getDrawable(R.color.text_1)
            }else{
                view_line.background=resources.getDrawable(R.color.text_c)
            }
        }
        et.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                super.afterTextChanged(s)
                text=et.text.toString()
                if(type== EditType.realname ||type== EditType.idcard){
                    /*if(isErrorStatus) {
                        dealErrorTextToNormal()
                    }*/
                    dealErrorTextToNormal()
                }
                if(text.isNotEmpty()){
                    if(et.hasFocus())
                    img_delete.visibility=View.VISIBLE
                }else{
                    img_delete.visibility=View.GONE
                }
                checkBtnEnable()
                if(isErrorStatus)
                    errorViewHide()
                isErrorStatus=false
            }
        })


        img_show.onClick {
            if(et.inputType!= InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                img_show.setImageResource(R.mipmap.ic_show)
                et.inputType= InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }else{
                img_show.setImageResource(R.mipmap.ic_show_2)
                et.inputType= InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            et.setSelection(text.length)
        }
    }

    private fun errorViewHide(){
        tv_failed.visibility = View.GONE
        et.showmErrorStatus(false)
    }
    private fun dealEditImg(){
        if(et.hasFocus()){
            img_show.show(type=== EditType.psw)
            if(text.isNotEmpty()) {
                img_delete.visibility = View.VISIBLE
            }
        }else{
            img_delete.visibility=View.GONE
        }
    }


    private fun checkBtnEnable(){
        if(btn==null)return
        when(type){
            EditType.phone ->{
                if(other_Et==null)
                btn?.isEnabled= ValidatorUtil.isMobile(text)
                else{
                    btn?.isEnabled= ValidatorUtil.isMobile(text) && other_Et!!.text.length>=6
                }
            }
            EditType.psw ->{
                if(isVisibleImg||other_Et==null){
                    btn?.isEnabled= text.length>=6
                }else{
                btn?.isEnabled= ValidatorUtil.isMobile(other_Et?.text.toString()) &&text.length>=6
                }
            }
            EditType.text ->{
                if(other_Et==null)
                btn?.isEnabled= text.isNotEmpty()
                else
                    btn?.isEnabled=!text.isEmpty()&&!other_Et?.text.toString().isEmpty()
            }
            EditType.sex ->{
                btn?.isEnabled= true
            }

            EditType.realname ->{
                btn?.isEnabled=!text.isEmpty()&&!other_Et?.text.toString().isEmpty()
            }
            EditType.idcard ->{
                btn?.isEnabled=!text.isEmpty()&&!other_Et?.text.toString().isEmpty()
            }
            EditType.invitecode ->{
                btn?.isEnabled=!text.isEmpty()&&text.length>=6
            }
        }
    }

    fun showErrorView(msg:String=tv_failed.text.toString()){
        isErrorStatus=true
        tv_failed.text=msg
        dealErrorTextToError()
        AnimationUtils.startShakeByViewAnim(tv_failed,3.toFloat(),300)
    }


    fun isErrorAgain():Boolean{
        var r=false
        if(isErrorStatus){
            if(tv_failed.isShown){
                r=true
                showErrorView()
            }
        }
        return r
    }
}
