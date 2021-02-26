package com.mashup.base.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.mashup.base.R

fun Context.clipData(text: String?): Boolean {
    text ?: return false
    val plainText = ClipData.newPlainText(getString(R.string.mobal_clip_label), text)
    (getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)
        ?.setPrimaryClip(ClipData(plainText.description, plainText.getItemAt(0)))
    return true
}