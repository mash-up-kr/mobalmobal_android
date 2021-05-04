package com.mashup.base.extensions

import com.google.android.material.bottomsheet.BottomSheetDialog

fun BottomSheetDialog.show(block: BottomSheetDialog.() -> Unit) {
    block(this)
    show()
}