package com.ray.frame.presentation.kotlinx.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import com.ray.frame.presentation.utils.IntentUtils

/**
 * 系统发送邮件
 */
fun Context.sendEmail(subject: String,
                      senderMail: String,
                      sendText: String) {
    val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto", senderMail, null))
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(senderMail))
    startActivity(Intent.createChooser(emailIntent, sendText))
}

inline fun Context.actionView(url: () -> String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url()))
    startActivity(intent)
}

/**
 * 找到真实context
 */
fun Context.scanForActivity(): Context? {
    if (this is Activity)
        return this
    else if (this is ContextWrapper)
        return this
    return null
}

/**
 * 拨号界面
 */
fun Context.call(phone:String){
    startActivity(IntentUtils.getDialIntent(phone))
}

/**
 * 系统分享
 */
fun Context.share(content:String){
    startActivity(IntentUtils.getShareTextIntent(content))
}

/**
 * 回到桌面
 */
fun Context.goHome(){
    startActivity(IntentUtils.getHomeIntent())
}

/**
 * 选择文件
 */
fun Activity.pickFile(code:Int,exFile:String?=null){
    if(exFile==null){
        startActivityForResult(IntentUtils.getPickFileIntent(),code)
    }else{
        startActivityForResult(IntentUtils.getPickFileIntent(exFile),code)
    }
}