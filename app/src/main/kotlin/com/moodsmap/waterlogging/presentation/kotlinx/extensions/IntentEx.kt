package com.moodsmap.waterlogging.presentation.kotlinx.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri

/**
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

fun Context.scanForActivity(): Context? {
    if (this is Activity)
        return this
    else if (this is ContextWrapper)
        return this
    return null
}
