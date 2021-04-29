package com.mashup.mobalmobal.custom.span

import android.text.TextPaint
import android.text.style.CharacterStyle

class ShadowSpan(
    private val color: Int,
    private val radius: Float,
    private val dx: Float,
    private val dy: Float
) : CharacterStyle() {
    override fun updateDrawState(p0: TextPaint?) {
        p0?.setShadowLayer(radius, dx, dy, color)
    }
}