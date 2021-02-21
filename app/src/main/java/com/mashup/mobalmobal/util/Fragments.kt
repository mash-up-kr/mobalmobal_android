package com.mashup.mobalmobal.util

import android.widget.Toast
import androidx.fragment.app.Fragment


fun Fragment.showToastMessage(message: String) {
    Toast.makeText(
        requireContext(),
        message, Toast.LENGTH_SHORT
    ).show()
}