package com.moodsmap.waterlogging.presentation.utils

import android.content.Context
import android.text.ClipboardManager
import com.moodsmap.waterlogging.App
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.showToast
import com.moodsmap.waterlogging.presentation.utils.ContextUtils.getSystemService
import java.math.BigDecimal


/**
 * Created by Ray on 2018/7/24.
 */
object StringUtils{

    /**
     * 将list转化为逗号分隔的字符串
     */
    fun listToString(list:List<String>):String{
        var s=""
        if(list.isEmpty())return s
        for (i in list){
            s+= "$i,"
        }
        return cutEndTag(s,",")
    }

    fun getHintPhone(mobile:String):String{
        var r=""
        if(mobile.length>=11){
            r = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length)
        }
        return r
    }

    fun getHintIDCard(identity:String):String{
        var r=""
        if(ValidatorUtil.isIDCard(identity)){
            r = identity.substring(0, 3) + "***********" + identity.substring(14)
        }
        return r
    }

    /**
     *
     * 方法描述 隐藏银行卡号中间的字符串（使用*号），显示前四后四
     *
     * @param cardNo
     * @return
     *
     * @author yaomy
     * @date 2018年4月3日 上午10:37:00
     */
     fun  hideCardNo(cardNo:String):String {
        if(cardNo.isEmpty()||cardNo.length<8) {
            return cardNo
        }

        var length = cardNo.length
        val beforeLength = 4
        val maxLength = 15
        //替换字符串，当前使用“*”
        val replaceSymbol = "*"
        val sb =  StringBuffer()
        for(i in cardNo.indices) {
            if(i < beforeLength || i > maxLength) {
                sb.append(cardNo[i])
            } else {
                sb.append(replaceSymbol)
            }
            if((i+1)%4==0){
                sb.append(" ")
            }
        }
        return sb.toString()
    }


    fun getStringArray(array: ArrayList<String>): Array<String?> {
        val st = arrayOfNulls<String>(array.size)
        for (i in 0 until array.size) {
            st[i] = array[i]
        }
        return st
    }

    fun copyToClipBoard(msg:String){
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.text = msg
        App.instance.showToast("复制成功")
    }

    fun getChineseNum(index:Int):String{
        return when(index){
            0-> "零"
            1-> "一"
            2-> "二"
            3-> "三"
            4-> "四"
            5-> "五"
            6-> "六"
            7-> "七"
            8-> "八"
            9-> "九"
            else->{
                ""
            }
        }
    }

    fun addTagToStr(s:String,tag:String=" "):String{
        var str = ""
        for (i in 0 until s.length) {
            if (i == s.length - 1) {
                str += s.substring(i, i + 1)
                break
            } else {
                str += s.substring(i, i + 1)
                str += tag
            }
        }
        return str
    }

    /**
     * java转换数字以万为单位
     * @param num 要转化的数字
     * @param digit 保留的位数 可传null
     * @return
     */
    fun castMoney(money:String, digit:Int?=null):String {
        try {
           return castMoney(money.toDouble(),digit)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return "--"
    }
     fun castMoney(money:Double, digit:Int?=null):String {
        if(money < 10000){
            return "$money"
        }
        val unit = "万"
        val newNum = money / 10000.0
        if(digit != null){
            val numStr = String.format("%." +digit +"f", newNum)
            return numStr + unit
        }

        return "$newNum$unit".replace(".0","")
   }

    /**
     * 去除字符串末尾指定符号
     */
    fun cutEndTag(str:String,tag: String):String{
        if (str.endsWith(tag)) {
            return str.substring(0, str.length - 1)
        }
        return str
    }

    fun getBigDecInt(yzhbl:BigDecimal,isFixEnd:Boolean=true):String{
        val r=yzhbl.multiply(BigDecimal(100)).toInt()
        if(isFixEnd){
            return "$r%"
        }else{
            return "$r"
        }
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    fun div(v1: Double, v2: Double, scale: Int=3): Double {
        if (scale < 0) {
            throw IllegalArgumentException(
                    "The scale must be a positive integer or zero")
        }
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     * 获取周几数字1-7大写
     *
     * @return
     */
    fun getWeekBigNum(tag: Int): String {
        return when (tag) {
           1 -> "一"
           2 -> "二"
           3 -> "三"
           4 -> "四"
           5 -> "五"
           6 -> "六"
           0 -> "日"
            else -> ""
        }
    }

}